
/*
 *
 *University of Alberta CMPUT 301 Group: CMPUT301F15T11
 *Copyright {2015} {Dingkai Liang, Zhaorui Chen, Jiaxuan Yue, Xi Zhang, Qingdai Du, Wei Song}
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
*/
package com.example.zhaorui.dvdcollector.Controller;

import android.util.Pair;

import com.example.zhaorui.dvdcollector.Model.Cache;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.GalleryList;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.User;

import java.util.Observer;

/**
 * <p>
 * The <code>FriendsController</code> is a controller of <code>Friends</code>, which controls friends information.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 01/11/15
 * @see java.util.ArrayList
 * @see com.google.gson.Gson
 * @see java.util.Observer
 */
public class FriendsController {
    private Friends friends;
    private Cache cache;
    private Boolean resultSearch;
    private Boolean resultGet;

    /**
     * Get friend list
     */
    public FriendsController(){
        friends = User.instance().getFriends();
        cache = Cache.getInstance();
    }

    /**
     * add a new friend to the friends list
     * @param name , a string variable, the friend to be add.
     */
    public void add(String name){
        friends.add(name);
        ObserverManager.getInstance().notifying("Friends");
    }
    /**
     * To get a friend from the friends list by index
     * @param index , an int variable.
     * @return the target friend's name.
     */

    public Friend get(int index){
        String name = friends.get(index);
        Thread getThread = new GetThread(name);
        getThread.start();
        while (resultGet==null){}//do nothing but wait
        return cache.get(name).first;
    }


    public String getNameByIndex(int index){
        return friends.get(index);
    }

    public void putFriendInCache(Friend friend, GalleryList galleryList){
        String name = friend.getProfile().getName();
        friend.getInventory().fresh();
        cache.put(name, new Pair<>(friend,galleryList));
    }

    public Friend getByName(String name){
        if (!cache.containsKey(name)) {
            Thread getThread = new GetThread(name);
            getThread.start();

            while (resultGet==null){//do nothing but wait
            }
        }
        return cache.get(name).first;
    }

    /**
     * To remove a friend from the friends list by index
     * @param index , an int variable.
     */
    public void remove(int index){
        String name = friends.get(index);
        friends.remove(name);
        if (cache.containsKey(name)) {
            cache.remove(name);
        }
        ObserverManager.getInstance().notifying("Friends");
    }
    /**
     * get all friends
     * @return a Friends variable
     */
    public Friends getFriends() {
        return friends;
    }

    /**
     * To add observer to make sure the firends list update on time in user's interface.
     * @param o , an observer.
     */
    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver("Friends",o);
    }

    // if there exist this name in webservice database, return true
    public boolean nameExist(String name){
        SearchThread thread = new SearchThread(name);
        thread.start();

        while (resultSearch==null){//do nothing but wait
        }
        return resultSearch;
    }

    class SearchThread extends Thread {
        private String search;

        public SearchThread(String search) {
            this.search = search;
        }

        @Override
        public void run() {
            UserHttpClient userHttpClient1 = new UserHttpClient();
            resultSearch = userHttpClient1.searchFriend(search, null);
        }
    }


    class GetThread extends Thread {
        private String userName;

        public GetThread(String userName) {
            this.userName = userName;
        }

        @Override
        public void run() {
            MyHttpClient httpClient = new MyHttpClient(userName);
            Friend friendToShow = httpClient.runPullFriend();
            GalleryList galleryList;
            if (User.instance().isDownloadImage()) {
                galleryList = httpClient.runPullGalleryList();
            } else {
                galleryList = new GalleryList();
            }
            putFriendInCache(friendToShow,galleryList);
            resultGet = true;
        }
    }
}
