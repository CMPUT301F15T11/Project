package com.example.zhaorui.dvdcollector.Model;

import com.example.zhaorui.dvdcollector.View.BaseActivity;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dingkai on 15/10/9.
 */
public class User{
    static private User instance;
    static private Trades trades;
    static private Friends friends;
    static private Inventory inventory;
    static private UserProfile profile;
    private User() {
        trades = new Trades();
        friends = new Friends();
        inventory = new Inventory();
        profile = new UserProfile();
    }

    public static User instance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public static Trades getTrades() {
        return trades;
    }

    public static Friends getFriends() {
        return friends;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static UserProfile getProfile() {
        return profile;
    }
}
