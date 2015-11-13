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

    public void changeBorrower(String borrowerName){
        trade.setBorrower(borrowerName);
    }

    public void changeOwner(String ownerName){
        trade.setOwner(ownerName);
    }

    public void changeType(String type){
        trade.setType(type);
    }

    public void changeStatus(boolean status){
        trade.setStatus(status);
    }

    public void addBorrowerItem(ArrayList<DVD> dvds){
        trade.setBorrowerItemList(dvds);
    }

    public void addOwnerItem(DVD dvd){
        trade.setOwnerItem(dvd);
    }
}
