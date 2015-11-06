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
 * Created by dingkai on 15/11/4.
 */
public class FriendsController {
    private Friends friends;
    private Cache cache;

    public FriendsController(){
        friends = User.instance().getFriends();
        cache = Cache.getInstance();
    }

    public void add(String name){
        friends.add(name);
        friends.notifying();
    }

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

    public void remove(int index){
        String name = friends.get(index);
        friends.remove(name);
        if (!cache.containsKey(name)) {
            cache.remove(name);
        }
        friends.notifying();
    }

    public Friends getFriends() {
        return friends;
    }

    public void addObserver(Observer o){
        friends.getObs().addObserver(o);
    }

    public boolean nameExist(String name){ return SimulatedDatabase.nameExist(name);}
}
