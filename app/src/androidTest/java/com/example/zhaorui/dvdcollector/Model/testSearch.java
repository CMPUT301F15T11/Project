package com.example.zhaorui.dvdcollector.Model;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by dingkai on 15/10/9.
 */
public class testSearch extends ActivityInstrumentationTestCase2 {
    public testSearch(Class activityClass) {
        super(activityClass);
    }

    public void testSearchInventory() throws Exception {
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        Inventory friendInventory = user.search().searchInventory(1);
        assertTrue(friend.getInventory() == friendInventory);
    }

    public void testSearchByCategory() throws Exception {
        User user = new User();
        User friend = new User();
        DVD dvd = new DVD();
        friend.getInventory().add(dvd);
        friend.getInventory().category(1,"rock");
        user.friends().add(friend);
        Inventory friendInventory = user.search().searchByCategory("rock");
        assertTrue(friendInventory.get(1).getCategory().equals("rock"));
    }

    public void testSearchByText() throws Exception {
        User user = new User();
        User friend = new User();
        DVD dvd = new DVD();
        friend.getInventory().add(dvd);
        friend.getInventory().editModify(1, "hi");
        user.friends().add(friend);
        Inventory friendInventory = user.search().searchByText("hi");
        assertTrue(friendInventory.get(1).getDetail().equals("hi"));
    }

    public void testSearchShared() throws Exception {
        User user = new User();
        User friend = new User();
        DVD dvd = new DVD();
        friend.getInventory().add(dvd);
        friend.getInventory().authority(1,false);
        user.friends().add(friend);
        Inventory friendInventory = user.search().searchShared();
        assertTrue(friendInventory.getSize()==0);
    }
}