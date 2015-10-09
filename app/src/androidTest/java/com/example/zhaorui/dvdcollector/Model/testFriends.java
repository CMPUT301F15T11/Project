package com.example.zhaorui.dvdcollector.Model;

import android.test.ActivityInstrumentationTestCase2;


/**
 * Created by dingkai on 15/10/9.
 */
public class testFriends extends ActivityInstrumentationTestCase2 {
    public testFriends(Class activityClass) {
        super(activityClass);
    }

    public void testTrack() throws Exception {
        User user= new User();
        assertTrue(Friends.track("default name") == user);
    }

    public void testAddFriend() throws Exception {
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        assertTrue(user.friends().getFriend(1)==friend);
    }

    public void testRemoveFriend() throws Exception {
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        user.friends().remove(1);
        assertTrue(user.friends().getSize()==0);
    }

    public void testCreateProfile() throws Exception {
        User user = new User();
        user.editProfile("default profile", new Configuration(true));
        assertTrue(user.getProfile().equals("default profile"));
    }

    public void testViewProfile() throws Exception {
        User user = new User();
        User friend = new User();
        user.friends().add(friend);
        String profile = user.friends().viewProfile(1);
        assertTrue(friend.getProfile().equals(profile));
    }
}