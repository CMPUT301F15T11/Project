package com.example.zhaorui.dvdcollector.Controller;

import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeListController {
    private TradeList trades;

    public TradeListController(TradeList trades) {
        this.trades = trades;
    }

    public void addTrade(String borrower, String owner, ArrayList<String> borrowerItemNames,
                         String ownerItemName, String type, String status){
        Trade trade = new Trade(borrower,owner,borrowerItemNames,ownerItemName,type,status);
        if (trade.getType().equals("Current Outgoing") || trade.getType().equals("Past Outgoing")) {
            trade.setName(trade.getType() + " with " + trade.getOwner() +
                    "\nID: " + String.valueOf(System.currentTimeMillis()));
        }else{
            trade.setName(trade.getType() + " trade with " + trade.getBorrower() +
                    "\nID: " + String.valueOf(System.currentTimeMillis()));
        }
        trades.add(trade);
        ObserverManager.getInstance().notifying(this);
    }

    // get all trades of the specified type, eg. "Current Outgoing"
    public ArrayList<Trade> getTradesOfType(String type){
        TradeList tradesToReturn = new TradeList();
        for (Trade aTrade : trades){
            if (aTrade.getType().equals(type)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    // get all trades of the specified status,  eg. "In-progress"
    public ArrayList<Trade> getTradeOfStatus(String status){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this.trades){
            if (aTrade.getStatus().equals(status)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    // returns the names of all input trades in an arraylist
    // in order to show the names in the listview
    public ArrayList<String> getNames(ArrayList<Trade> trades){
        ArrayList<String> names = new ArrayList<>();
        for(Trade aTrade : trades){
            names.add(aTrade.getName());
        }
        return names;
    }

    public Trade getTradeByName(String name){
        for(Trade aTrade : trades){
            if(aTrade.getName().equals(name))
                return aTrade;
        }
        return null;
    }

    public ArrayList<Trade> getTradeRequests(){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for(Trade aTrade : trades){
            if (aTrade.getStatus().equals("Pending") && aTrade.getType().equals("Current Incoming")){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public int getSuccessTimes(){
        int count = 0;
        for(Trade aTrade : trades){
            if (aTrade.getStatus()!="Declined"){
                count++;
            }
        }
        return count;
    }

    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver(this,o);
    }

    // change the trade's type based on its current type
    public void setTradeType(String name){
        for (Trade trade : trades){
            if (trade.getName().equals(name)){
                if(trade.getType().equals("Current Incoming")) {
                    trade.setType("Past Incoming");
                    ObserverManager.getInstance().notifying(this);
                    return;
                }else if (trade.getType().equals("Current Outgoing")){
                    trade.setType("Past Outgoing");
                    ObserverManager.getInstance().notifying(this);
                    return;
                }else {
                    return;
                    // do nothing
                }
            }
        }
    }

    // change a trade request's status and type
    public void setTradeResult(String name, Boolean isAccepted){
        for (Trade trade : trades){
            if(trade.getName().equals(name)){
                if(isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("In-progress");
                    //////
                }else if(!isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("Declined");
                    setTradeType(trade.getName());
                }
                break;
            }
        }
    }

    // set an In-progress trade to Complete status
    public void setTradeComplete(String name){
        for (Trade trade : trades){
            if (trade.getName().equals(name)){
                switch (trade.getStatus()) {
                    case "Pending":
                        break;
                    case "In-progress"://Set to complete also set type to past
                        if(trade.getOwner().equals(User.instance().getProfile().getName())) {
                            trade.setType("Complete");
                            setTradeType(trade.getName());
                            ObserverManager.getInstance().notifying(this);
                        }
                        break;
                    case "Complete":
                        break;
                    case "Declined":
                        break;
                }
                break;
            }
        }
    }
}
