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
