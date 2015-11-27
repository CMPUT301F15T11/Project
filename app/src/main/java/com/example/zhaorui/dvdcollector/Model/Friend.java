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

import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * <p>
 * The <code>Friend</code> manage the selected friend's information of the current user.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 04/11/15
 */
public class Friend {
    public static final String URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t11/friend/";
    /**
     * Initialize a Inventory to store the friends inventory.
     *
     */
    private Inventory inventory;
    /**
     * Initialize a UserProfile to store the friends profile.
     *
     */
    private UserProfile profile;
    //private TradeList tradeList;
    /**
     * To get the selected friend's information.
     * @param user ,the selected friend.
     */

    public Friend(User user){
        //only push sharable dvds online
        InventoryController inventoryController = new InventoryController();
        inventoryController.setInventory(user.getInventory());
        inventory = inventoryController.getSharableInventory();
        profile = user.getProfile();
    }
    /**
     * To store the select friend's information.
     */

    public Friend(Inventory inventory, UserProfile profile) {
        this.inventory = inventory;
        this.profile = profile;
    }
    /**
     * To get the friend's inventories
     * @return Inventory, which contain dvds owns by the friend
     */
    public Inventory getInventory() {
        return inventory;
    }
    /**
     * To get the friend's profile
     * @return profile, the friend's personal information.
     */
    public UserProfile getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return profile.getName();
    }
}
