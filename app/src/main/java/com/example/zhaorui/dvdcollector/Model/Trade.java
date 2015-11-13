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
    private String name; //用于在listview中展示
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
     * Initialize a boolean to be the type of the trade.
     */
    private String type;// 分为四种， Current Incoming/Current Outgoing/Past Incoming/Past Outgoing

    private String status;//分为两种，Pending/Accepted/Rejected
    /**
     * Initialize a array list to store the dvds in the current trade.
     *
     */
    public ArrayList<DVD> borrowerItemList;

    public DVD ownerItem;
    /**
     * To set the borrower and the owner of the trade
     * @param borrower , a user variable
     * @param owner ,a user variable
     */
    /*
    public Trade(User borrower, User owner) {
        this.borrower = borrower;
        this.owner = owner;
    }
    */

    public Trade() {
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

    /*
    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }*/

    /*
    public void setOwner(User owner) {
        this.owner = owner;
    }*/

    public void setType(String type) {
        this.type = type;
    }

    public void setBorrowerItemList(ArrayList<DVD> borrowerItemList) {
        this.borrowerItemList = borrowerItemList;
    }

    public void setOwnerItem(DVD ownerItem) {
        this.ownerItem = ownerItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<DVD> getBorrowerItemList() {
        return borrowerItemList;
    }

    public DVD getOwnerItem() {
        return ownerItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
