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

import java.util.ArrayList;

/**
 * <p>
 * The <code>Trade</code> class manages a trade information between the current user and other user.
 * This class providing get functions to allowed other class to know the trade information.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 01/11/15
 * @see java.util.ArrayList
 */
public class Trade {

    /**
     * A tag used for notifying users whether the status of
     * this trade has been changed
     */
    private String changed;

    /**
     * for showing in the listview
     */
    private String name;

    /**
     * for identify the trade
     */
    private String id;

    /**
     * Initialize a user to be the borrower in the trade.
     */
    private String borrower;

    /**
     * Initialize a user to be the owner in the trade.
     */
    private String owner;

    /**
     * Initialize a boolean to be the type of the trade.
     * Four types as follows:
     * Current Incoming/Current Outgoing/Past Incoming/Past Outgoing
     */
    private String type;

    /**
     * Initialize a boolean to be the status of the trade.
     * Four status as follows:
     * In-progress/Complete/Pending/Declined
     */
    private String status;

    /**
     * Initialize a array list to store the name of borrower's dvds in this trade.
     *
     */
    private ArrayList<String> borrowerItemList;

    /**
     * Initialize a string to store the name of owner's dvd in this trade
     *
     */
    private String ownerItem;
    /**
     * This function is called when trade DVDs
     * @param borrower a string variable of borrower
     * @param owner a string variable of owner
     * @param borrowerItemNames an array list of string variable of borrower item names
     * @param ownerItemName a string variable of owner item name
     * @param type a string variable of type
     * @param status a string variable of status
     */
    public Trade(String borrower, String owner, ArrayList<String> borrowerItemNames,
                 String ownerItemName, String type, String status) {
        this.borrower = borrower;
        this.owner = owner;
        this.borrowerItemList = borrowerItemNames;
        this.ownerItem = ownerItemName;
        this.type = type;
        this.status = status;
        this.changed = "";
    }

    /**
     * This function is called when other function need to know the type of the trade.
     * @return type, a boolean(True or False).
     */
    public String getType() {
        return type;
    }
    /**
     * Initialize string to store type of trade
     * @param type a string variable of type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Get status of trade
     * @return status
     */
    public String getStatus() {
        return status;
    }
    /**
     * Initialize a string to store status
     * @param status a string variable of status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Get borrower in trade
     * @return borrower
     */

    public String getBorrower() {
        return borrower;
    }
    /**
     * Initialize a string to store borrower
     * @param borrower a string variable
     */
    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }
    /**
     * Get Owner of trade
     * @return owner
     */
    public String getOwner() {
        return owner;
    }
    /**
     * Initialize a string to store owner
     * @param owner string variable
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    /**
     * Get borrower item list
     * @return borrower item list
     */
    public ArrayList<String> getBorrowerItemList() {
        return borrowerItemList;
    }
    /**
     * Get owner item
     * @return owner item
     */
    public String getOwnerItem() {
        return ownerItem;
    }
    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Initialize a string to store name
     * @param name string varibale
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get ID
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Initialize a string to store
     * @param id string variable
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Initialize a string to store
     * @param changed string variable
     */
    public void setChanged(String changed) {
        this.changed = changed;
    }

    /**
     * get changed
     * @return changed
     */
    public String getChanged() {
        return changed;
    }


}
