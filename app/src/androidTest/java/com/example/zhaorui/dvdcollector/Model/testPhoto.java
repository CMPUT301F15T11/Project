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
