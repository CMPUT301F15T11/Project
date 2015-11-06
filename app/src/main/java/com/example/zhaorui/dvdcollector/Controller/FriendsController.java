
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

import com.example.zhaorui.dvdcollector.Model.Cache;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.SimulatedDatabase;
import com.example.zhaorui.dvdcollector.Model.User;
import com.google.gson.Gson;
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
    private Friends friends;
    private Cache cache;

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
        friends.notifying();
    }
    /**
     * To get a friend from the friends list by index
     * @param index , an int variable.
     * @return the target friend's name.
     */
    public Friend get(int index){
        String name = friends.get(index);
        if (!cache.containsKey(name)) {
            Gson gson = new Gson();
            Type friendType = new TypeToken<Friend>(){}.getType();
            Friend friend;
            String jsonValue = SimulatedDatabase.get(name);
            friend = gson.fromJson(jsonValue,friendType);
            cache.put(name, friend);
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
        friends.notifying();
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
        friends.getObs().addObserver(o);
    }

    public boolean nameExist(String name){ return SimulatedDatabase.nameExist(name);}
}
