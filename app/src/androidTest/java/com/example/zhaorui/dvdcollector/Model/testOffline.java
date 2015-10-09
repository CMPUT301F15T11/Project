package com.example.zhaorui.dvdcollector.Model;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by dingkai on 15/10/9.
 */
public class testOffline extends ActivityInstrumentationTestCase2 {
    public testOffline(Class activityClass) {
        super(activityClass);
    }

    public void testMakeOffline()throws Exception{
        Internet.getInstance().disconnect();
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        Internet.getInstance().connect();
        Internet.getInstance().downloadData();
        assertTrue(inventory.get(1) == dvd);
    }

    public void testPushTradeOnline()throws Exception{
        Internet.getInstance().disconnect();
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        Internet.getInstance().connect();
        Internet.getInstance().downloadData();
        assertTrue(borrower.getTradeList().get(1).getOwner() == owner);
    }

    public void testCacheOffline()throws Exception{
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        Internet.getInstance().disconnect();
        Inventory friendInventory = user.search().searchInventory(1);
        assertTrue(friend.getInventory() == friendInventory);
    }
}
