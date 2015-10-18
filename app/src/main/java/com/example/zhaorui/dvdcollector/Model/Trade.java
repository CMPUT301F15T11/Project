package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;

/**
 * Created by dingkai on 15/10/9.
 */
public class Trade {
    private User borrower;
    private User owner;
    private boolean status;

    public ArrayList<DVD> itemList;

    public Trade(User borrower, User owner) {
        this.borrower = borrower;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public User getBorrower() {
        return borrower;
    }

    public boolean getStatus() {
        return status;
    }

    public void decide(boolean decision){}

    public void counterTrade(){}

    public boolean email(){
        String str = "";
        if (str == "email already sent") return false;//wait for coding
        return true;
    }
}
