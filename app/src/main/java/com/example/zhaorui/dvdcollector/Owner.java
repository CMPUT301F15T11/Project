package com.example.zhaorui.dvdcollector;

import java.util.List;

/**
 * Created by zhaorui on 10/7/15.
 */
public class Owner {
    private List<Owner> myFriendList;
    private Inventory myInventory;
    private Profile myProfile;

    public Owner(String userName, String contact, String city) {
        this.myInventory = new Inventory();
        this.myProfile = new Profile(userName, contact, city);
        this.myFriendList = new List<Owner>();
    }

}
