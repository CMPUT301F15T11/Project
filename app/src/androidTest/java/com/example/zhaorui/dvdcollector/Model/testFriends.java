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