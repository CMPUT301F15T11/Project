package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.User;

import java.util.ArrayList;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeController {
    private Trade trade;

    public TradeController(Trade trade) {
        this.trade = trade;
    }

    public TradeController() {
    }

    public void setName(){
        if (trade.getType().equals("Current Outgoing") || trade.getType().equals("Past Outgoing")) {
            trade.setName(trade.getType() + " with " + trade.getOwner() +
                    "\nID: " + String.valueOf(System.currentTimeMillis()));
        }else{
            trade.setName(trade.getType() + " trade with " + trade.getBorrower() +
                    "\nID: " + String.valueOf(System.currentTimeMillis()));
        }
    }

    public void changeBorrower(String borrowerName){
        trade.setBorrower(borrowerName);
    }

    public void changeOwner(String ownerName){
        trade.setOwner(ownerName);
    }

    public void changeType(String type){
        trade.setType(type);
    }

    public void changeStatus(String status){
        trade.setStatus(status);
    }

    public void addBorrowerItem(ArrayList<String> dvds){
        trade.setBorrowerItemList(dvds);
    }

    public void addOwnerItem(String dvd){
        trade.setOwnerItem(dvd);
    }

    public void setTradeComplete(){
        changeStatus("Complete");
    }

    public void setTradeResult(Boolean isAccepted){
        if(isAccepted){
            if (trade.getType().equals("Current Outgoing")) {
                changeStatus("In-progress");
            }
            else {
                changeStatus("In-progress");
            }
        }else {
            if (trade.getType().equals("Current Outgoing")) {
                changeType("Past Outgoing");
            }
            else {
                changeType("Past Incoming");
            }
        }

    }
}
