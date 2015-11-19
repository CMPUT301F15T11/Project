
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

import com.example.zhaorui.dvdcollector.Controller.DataManager;

/**
 * <p>
 * The <code>User</code> class manages the current user's information about his trades,friends,inventory and profile.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 01/11/15
 */
public class User{
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t11/users/";
    private static final String SEARCH_URL = "http://cmput301.softwareprocess.es:8080/cmput301f15t11/users/_search";
    static private User instance;
    /**
     * initialize a Friends to store user's friends.
     */
    private Friends friends;
    /**
     * initialize a Inventory to store user's inventory.
     */
    private Inventory inventory;
    /**
     * initialize a Userprofile to store user's profile.
     */
    private UserProfile profile;
    /**
     * initialize a user.
     */
    private TradeList tradeList;

    private User() {
        friends = new Friends();
        inventory = new Inventory();
        profile = new UserProfile();
        tradeList = new TradeList();
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public static User instance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public TradeList getTradeList(){return tradeList;}
    /**
     * Call <code>Friends</code> class to get the user's friends.
     * @return the user's friends.
     */
    public Friends getFriends() {
        return friends;
    }
    /**
     * Call <code>Inventory</code> class to get the user's inventories.
     * @return the user's inventories.
     */
    public Inventory getInventory() {
        return inventory;
    }
    /**
     * Call <code>UserProfile</code> class to get the user's prifile information.
     * @return the user's profile information.
     */
    public UserProfile getProfile() {
        return profile;
    }

    public static String getResourceUrl() {
        return RESOURCE_URL;
    }

    public static String getSearchUrl() {
        return SEARCH_URL;
    }
}
