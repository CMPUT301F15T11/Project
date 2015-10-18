package com.example.zhaorui.dvdcollector.Model;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by dingkai on 15/10/9.
 */
public class testConfiguration extends ActivityInstrumentationTestCase2 {
    public testConfiguration(Class activityClass) {
        super(activityClass);
    }

    public void testImageDownload(){
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        DVD dvd = new DVD();
        dvd.attachPhoto("/image");
        friend.getInventory().add(dvd);
        Configuration confirm = new Configuration(false);
        assertFalse(user.search().searchInventory(1).get(1).downloadPhoto(confirm));
    }

    public void testEditProfile(){
        User user = new User();
        user.editProfile("default profile", new Configuration(true));
        user.editProfile("changed profile", new Configuration(false));
        assertTrue(user.getProfile().equals("default profile"));
    }
}
