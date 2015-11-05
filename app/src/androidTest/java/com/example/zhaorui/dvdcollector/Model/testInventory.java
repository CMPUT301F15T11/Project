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

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;

/**
 * Created by dingkai on 15/10/9.
 */
public class testInventory extends ActivityInstrumentationTestCase2 {
    public testInventory(Class activityClass) {
        super(activityClass);
    }

    public void testAdd() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        assertTrue(inventory.get(1) == dvd);
    }

    public void testSeeDetails() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        assertTrue(inventory.seeDetails(1).equals(dvd.getDetail()));
    }

    public void testAuthority() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.authority(1, true);
        assertTrue(dvd.isShareable());
    }

    public void testEditModify() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.editModify(1,"got changes");
        assertTrue(dvd.getDetail().equals("got changes"));
    }

    public void testDelete() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.delete(1);
        assertTrue(inventory.getSize() == 0);
    }

    public void testCategory() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.category(1,"rock!");
        assertTrue(dvd.getCategory().equals( "rock!"));
    }
}
