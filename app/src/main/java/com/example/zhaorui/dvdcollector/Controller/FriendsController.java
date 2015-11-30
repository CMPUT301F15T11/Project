
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
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Model.Cache;
import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.GalleryList;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observer;

/**
 * <p>
 * The <code>FriendsController</code> is a controller of <code>Friends</code>, which controls friends information.
 * <p>
 *
 * @author  Dingkai Liang; Jiaxaun Yue
 * @version 01/11/15
 * @see java.util.ArrayList
 * @see com.google.gson.Gson
 * @see java.util.Observer
 */
public class FriendsController {
    /**
     * Initialize a new friends
     */
    private Friends friends;
    /**
     * Initialize a new cache
     */
    private Cache cache;
    /**
     * Initialize a boolean variable to store search result
     */
    private Boolean resultSearch;
    /**
     * Initialize a boolean variable to store get result
     */
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
        if (ContextUtil.getInstance().isConnected()){
            Thread getThread = new GetThread(name);
            getThread.start();
            while (resultGet==null){}//do nothing but wait
            return cache.get(name).first;
        } else if (cache.containsKey(name)){
            return cache.get(name).first;
        } else {
            Toast.makeText(ContextUtil.getInstance(), "Not Connect to Internet!", Toast.LENGTH_LONG).show();
            return new Friend();
        }
    }

    /**
     * Initialize an int variable to store target friend's name
     * @param index int variable
     * @return friend's name in string
     */
    public String getNameByIndex(int index){
        return friends.get(index);
    }
    /**
     * This function is to put friends to cache
     * @param friend a string variable and is user's friend
     * @param galleryList a string variable and is galleryList
     */
    private void putFriendInCache(Friend friend, GalleryList galleryList){
        String name = friend.getProfile().getName();
        friend.getInventory().fresh();
        cache.put(name, new Pair<>(friend,galleryList));
    }
    /**
     * Get friend's name by name
     * @param name string variable
     * @return friend's name in cache
     */
    public Friend getByName(String name){
        if (ContextUtil.getInstance().isConnected()){
            Thread getThread = new GetThread(name);
            getThread.start();
            while (resultGet==null){}//do nothing but wait
            return cache.get(name).first;
        } else if (cache.containsKey(name)){
            return cache.get(name).first;
        } else {
            Toast.makeText(ContextUtil.getInstance(), "Not Connect to Internet!", Toast.LENGTH_LONG).show();
            return new Friend();
        }
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

    /**
     * To search friend
     */
    class SearchThread extends Thread {
        /**
         * Initialize a string to store a search
         */
        private String search;
        /**
         * Initialize a search thread of search
         * @param search string variable
         */
        public SearchThread(String search) {
            this.search = search;
            resultSearch = null;
        }
        /**
         * To execute search
         */
        @Override
        public void run() {
            UserHttpClient userHttpClient1 = new UserHttpClient();
            resultSearch = userHttpClient1.searchFriend(search, null);
        }
    }

    /**
     * To search and add friend
     */
    class GetThread extends Thread {
        /**
         * Initialize a string to store username
         */
        private String userName;
        /**
         * Initialize get thread of username
         * @param userName string variable
         */
        public GetThread(String userName) {
            resultGet = null;
            this.userName = userName;
        }
        /**
         * to add new friend's name to database
         */
        @Override
        public void run() {
            MyHttpClient httpClient = new MyHttpClient(userName);
            Friend friendToShow = httpClient.runPullFriend();
            GalleryList galleryList;
            if (User.instance().isDownloadImage()) {
                galleryList = httpClient.runPullGalleryList();
            } else {
                galleryList = new GalleryList(friendToShow.getInventory().size());
            }
            putFriendInCache(friendToShow,galleryList);
            resultGet = true;
        }
    }

    /**
     * sort completed trades
     */
    private class SortByCompletedTrades implements Comparator<String> {
        /**
         * compare two trades
         * @param lhs a string variable
         * @param rhs a string variable
         * @return 1 or -1
         */
        @Override
        public int compare(String lhs, String rhs) {
            Friend aFriend = cache.get(lhs).first;
            Friend bFriend = cache.get(rhs).first;
            if (aFriend.getTradeCompleted() < bFriend.getTradeCompleted()) return 1;
            return -1;
        }
    }

    /**
     * get a list of trader
     * @return a list of trader
     */
    public ArrayList<String> getTopTraderList(){
        for (String friend : friends){
            if (!cache.containsKey(friend)) getByName(friend);
        }
        Friends listToReturn = (Friends)friends.clone();
        Collections.sort(listToReturn,new SortByCompletedTrades());
        for (int i = 0; i < listToReturn.size(); ++i){
            String friendName = listToReturn.get(i);
            String info = "";
            for (int j = 0; j < ( 15 - friendName.length()); ++j){
                info += " ";
            }
            info += "Trades Completed: ";
            info += Cache.getInstance().get(friendName).first.getTradeCompleted();
            listToReturn.set(i,friendName + info);
        }
        return listToReturn;
    }

}
