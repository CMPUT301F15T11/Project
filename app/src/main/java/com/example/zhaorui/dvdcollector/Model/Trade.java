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
     * Initialize a string to store name
     */
    // for showing in the listview
    private String name;

    /**
     * Initialize a string to store ID
     */
    // for identify the trade
    private String id;

    /**
     * Initialize a user to be the borrower in the trade.
     */
    //private User borrower;
    private String borrower;

    /**
     * Initialize a user to be the owner in the trade.
     */
    //private User owner;
    private String owner;

    /**
     * Initialize a string to store type of trade
     */
    private String type;// 分为四种， Current Incoming/Current Outgoing/Past Incoming/Past Outgoing

    /**
     *Initialize a string to store status
     */
    private String status;//4 different status，In progress/Complete/Pending/Declined

    /**
     * Initialize a array list to store the dvds in the current trade.
     */
    private ArrayList<String> borrowerItemList;

    /**
     * Initialize a string to store owner item
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

    }

    /**
     * This function is called when other function need to know the owner of the trade.
     * @return owner, a user variable.
     */
    /*
    public User getOwner() {
        return owner;
    }*/
    /**
     * This function is called when other function need to know the borrower of the trade.
     * @return borrower, a user variable.
     */
    /*
    public User getBorrower() {
        return borrower;
    }*/


    /**
     * This function is called when other function need to know the type of the trade.
     * @return type, a boolean(True or False).
     */
    public String getType() {
        return type;
    }

    /**
     * To set the decision of this trade.
     * @param decision, a boolean(True or False).
     */
    public void decide(boolean decision){}

    /**
     * If the owner an the borrower can't agree with each other
     * this function will be called to offer a counter trade.
     */
    public void counterTrade(){}

    /**
     *Send email for counter trade.
     */
    public boolean email(){
        String str = "";
        if (str == "email already sent") return false;//wait for coding
        return true;
    }

    /**
     * Initialize string to store type of trade
     * @param type a string variable of type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Initialize array list to store borrower item list
     * @param borrowerItemList array list variable of borrower item list
     */
    public void setBorrowerItemList(ArrayList<String> borrowerItemList) {
        this.borrowerItemList = borrowerItemList;
    }

    /**
     * Initialize a string to store owner item
     * @param ownerItem string variable
     */
    public void setOwnerItem(String ownerItem) {
        this.ownerItem = ownerItem;
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
}
