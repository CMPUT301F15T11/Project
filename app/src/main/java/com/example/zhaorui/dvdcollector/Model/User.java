package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;

/**
 * Created by dingkai on 15/10/9.
 */
public class User {

    private String profile;
    private String name;
    private Friends friends;
    private Inventory inventory;
    private Search search;
    private ArrayList<Trade> tradeList;
    private boolean notify = false;

    public User() {
        name = "default name"; // for testing
        friends = new Friends(this);
        inventory = new Inventory();
        search = new Search(this);
    }

    public String getProfile() {
        return profile;
    }

    public Friends friends(){return friends;}

    public Search search(){return search;}

    public Inventory getInventory(){return inventory;}

    public ArrayList<Trade> getTradeList(){return tradeList;}

    public boolean isNotify() {return notify;}

    public ArrayList<Trade> browseTrade(String standing){return null;}

    public void editProfile(String profile,Configuration confirm){}
}
