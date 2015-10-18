package com.example.zhaorui.dvdcollector.Model;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;

/**
 * Created by dingkai on 15/10/9.
 */
public class testPhoto extends ActivityInstrumentationTestCase2 {
    public testPhoto(Class activityClass) {
        super(activityClass);
    }

    public void testAttachPhoto()throws Exception{
        DVD dvd = new DVD();
        dvd.attachPhoto("/image");
        assertTrue(dvd.hasImage);
    }

    public void testViewPhoto()throws Exception{
        DVD dvd = new DVD();
        assertTrue(dvd.viewPhoto());
    }

    public void testDeletePhoto()throws Exception{
        DVD dvd = new DVD();
        dvd.attachPhoto("/image");
        dvd.deletePhote();
        assertFalse(dvd.hasImage);
    }

    public void testPhotoSize()throws Exception{
        DVD dvd = new DVD();
        dvd.attachPhoto("/image bigger than 65536bytes");
        assertFalse(dvd.hasImage);
    }

    public void testDownloadPhoto() throws Exception{
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        DVD dvd = new DVD();
        dvd.attachPhoto("/image");
        friend.getInventory().add(dvd);
        assertTrue(user.search().searchInventory(1).get(1).downloadPhoto(new Configuration(true)));
    }
}
