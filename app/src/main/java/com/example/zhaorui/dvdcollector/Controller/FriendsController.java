
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

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Cache;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.SimulatedDatabase;
import com.example.zhaorui.dvdcollector.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    /**
     * Initialize a new friends
     */
    private Friends friends;
    /**
     * Initialize a new cache
     */
    private Cache cache;
    /**
     * Initialize a new user http client
     */
    private UserHttpClient userHttpClient = new UserHttpClient();
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
        if (!cache.containsKey(name)) {
            Thread getThread = new GetThread(name);
            getThread.start();

            while (resultGet==null){//do nothing but wait
            }
        }
        ObserverManager.getInstance().notifying("Inventory");
    }

    /**
     * To get a friend from the friends list by index
     * @param index , an int variable.
     * @return the target friend's name.
     */
    public Friend get(int index){
        String name = friends.get(index);
        if (!cache.containsKey(name)) {
            Thread getThread = new GetThread(name);
            getThread.start();

            while (resultGet==null){//do nothing but wait
            }
        }
        return cache.get(name);
    }

    /**
     * Initialize an int variable to store target friend's name
     * @param index int variable
     * @return friend's name in string
     */
    public String getNameByIndex(int index){
        String name = friends.get(index);
        return name;
    }

    /**
     * This function is to put friends to cache
     * @param friend a string variable and is user's friend
     */
    public void putFriendInCache(Friend friend){
        String name = friend.getProfile().getName();
        if (!cache.containsKey(name)) {
            friend.getInventory().fresh();
            cache.put(name, friend);
        }
    }

    /**
     * Get friend's name by name
     * @param name string variable
     * @return friend's name in cache
     */
    public Friend getByName(String name){
        if (!cache.containsKey(name)) {
            Thread getThread = new GetThread(name);
            getThread.start();

            while (resultGet==null){//do nothing but wait
            }
        }
        return cache.get(name);
    }

    /**
     * To remove a friend from the friends list by index
     * @param index , an int variable.
     */
    public void remove(int index){
        String name = friends.get(index);
        friends.remove(name);
        if (!cache.containsKey(name)) {
            cache.remove(name);
        }
        ObserverManager.getInstance().notifying("Friends");
    }
    /**
     * This function is called when other function need to know all friends
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
    /**
     * Return true if the name exists in database
     * @param name a string variable a existing name in database
     * @return the existing name
     */
    public boolean nameExist(String name){
        //return SimulatedDatabase.nameExist(name);
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
            this.userName = userName;
        }

        /**
         * to add new friend's name to database
         */
        @Override
        public void run() {
            Friend friendToShow = userHttpClient.pullFriend(userName);
            friends.add(userName);
            putFriendInCache(friendToShow);
            resultGet = true;
        }
    }
}
