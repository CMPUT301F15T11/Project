package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.View.DVDInfoActivity;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeListController {
    private TradeList trades;
    private static String TAG = "TradeListController";

    public TradeListController(TradeList trades) {
        this.trades = trades;
    }

    public void addTrade(String borrower, String owner, ArrayList<String> borrowerItemNames,
                         String ownerItemName, String type, String status, String id){
        Trade trade = new Trade(borrower,owner,borrowerItemNames,ownerItemName,type,status);
        if (trade.getType().equals("Current Outgoing") || trade.getType().equals("Past Outgoing")) {
            trade.setName(trade.getType() + " with " + trade.getOwner() +
                    "\nID: " + id);
            trade.setId(id);
        }else{
            trade.setName(trade.getType() + " trade with " + trade.getBorrower() +
                    "\nID: " + id);
            trade.setId(id);
        }
        trades.add(trade);
        ObserverManager.getInstance().notifying("Trades");
    }

    public TradeList getTrades() {
        return trades;
    }

    // get all trades of the specified type, eg. "Current Outgoing"
    public TradeList getTradesOfType(String type){
        TradeList tradesToReturn = new TradeList();
        for (Trade aTrade : trades.getTrades()){
            if (aTrade.getType().equals(type)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    // get all trades of the specified status,  eg. "In-progress"
    public TradeList getTradeOfStatus(String status){
        TradeList tradesToReturn = new TradeList();
        for (Trade aTrade : trades.getTrades()){
            if (aTrade.getStatus().equals(status)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    // returns the names of all input trades in an arraylist
    // in order to show the names in the listview
    public ArrayList<String> getNames(TradeList trades){
        ArrayList<String> names = new ArrayList<>();
        for(Trade aTrade : trades.getTrades()){
            names.add(aTrade.getName());
        }
        return names;
    }

    public Trade getTradeByName(String name){
        for(Trade aTrade : trades.getTrades()){
            if(aTrade.getId().equals(name))
                return aTrade;
        }
        return null;
    }

    public TradeList getTradeRequests(){
        TradeList tradesToReturn = new TradeList();
        for(Trade aTrade : trades.getTrades()){
            if (aTrade.getStatus().equals("Pending") && aTrade.getType().equals("Current Incoming")){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public int getSuccessTimes(){
        int count = 0;
        for(Trade aTrade : trades.getTrades()){
            if (aTrade.getStatus()!="Declined"){
                count++;
            }
        }
        return count;
    }

    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver("Trades",o);
    }

    // change the trade's type based on its current type
    public void changeTradeType(String id){
        for (Trade trade : trades.getTrades()){
            if (trade.getId().equals(id)){
                if(trade.getType().equals("Current Incoming")) {
                    trade.setType("Past Incoming");
                    ObserverManager.getInstance().notifying("Trades");
                    return;
                }else if (trade.getType().equals("Current Outgoing")){
                    trade.setType("Past Outgoing");
                    ObserverManager.getInstance().notifying("Trades");
                    return;
                }else {
                    return;
                    // do nothing
                }
            }
        }
    }
    // change a trade request's status and type
    // pending --> In-progress
    // pending --> Declined, Current xxxx --> Past xxxx
    public void setTradeResult(String id, Boolean isAccepted){
        for (Trade trade : trades.getTrades()){
            if(trade.getId().equals(id)){
                if(isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("In-progress");
                    ObserverManager.getInstance().notifying("Trades");
                    //////
                }else if(!isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("Declined");
                    changeTradeType(trade.getId());
                    ObserverManager.getInstance().notifying("Trades");
                }
                break;
            }
        }
    }

    // set an In-progress trade to Complete status
    public void setTradeComplete(String id){
        for (Trade trade : trades.getTrades()){
            if (trade.getId().equals(id)){
                switch (trade.getStatus()) {
                    case "Pending":
                        Toast.makeText(ContextUtil.getInstance(),"This trade hasn't been accepted", Toast.LENGTH_SHORT).show();
                        break;
                    case "In-progress"://Set to complete also set type to past
                        if(trade.getOwner().equals(User.instance().getProfile().getName())) {
                            trade.setType("Complete");
                            changeTradeType(trade.getId());
                            ObserverManager.getInstance().notifying("Trades");
                        }
                        break;
                    case "Complete":
                        Toast.makeText(ContextUtil.getInstance(),"This trade is already complete", Toast.LENGTH_SHORT).show();
                        break;
                    case "Declined":
                        Toast.makeText(ContextUtil.getInstance(),"This trade cannot be set to complete", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            }
        }
    }

    public void pullTrade(String userName){
        HttpClient httpClient = new HttpClient(userName);
        this.trades = httpClient.runPullTradeList();
        ////////////////////////////////////////////////////////////
        User.instance().getTradeList().setTrades(trades.getTrades());
        Log.e(TAG, "Pulled tradelist");
    }

    public void sendTrade(String ownerName, ArrayList<String> borrowerDvdNameBuffer,
                          String ownerDvdNameBuffer, String id){
        // push User(Borrower)'s tradelist
        HttpClient httpClientBorrower = new HttpClient(User.instance().getProfile().getName());
        httpClientBorrower.setTradeList(User.instance().getTradeList(), User.instance().getProfile().getName());
        httpClientBorrower.runPushTradeList();

        // pull owner's tradelist
        HttpClient httpClient = new HttpClient(ownerName);
        TradeList tradeList = httpClient.runPullTradeList();
        // add this new trade to owner's tradelist
        TradeListController tradeListControllerOwner = new TradeListController(tradeList);
        tradeListControllerOwner.addTrade(User.instance().getProfile().getName(),
                ownerName,
                borrowerDvdNameBuffer,
                ownerDvdNameBuffer,
                "Current Incoming",
                "Pending",
                id);
        // push owner's tradelist
        httpClient.setTradeList(tradeListControllerOwner.getTrades(), ownerName);
        httpClient.runPushTradeList();

        //send email to owner to notify him

        Log.e(TAG,"Send the trade");
    }

    public void acceptTrade(int tradeIndex){
        Trade trade = getTradeRequests().get(tradeIndex);
        HttpClient httpClientOwner = new HttpClient(trade.getOwner(), this.trades);
        HttpClient httpClientBorrower = new HttpClient(trade.getBorrower());

        //set this dvd to not sharable because it's been borrowed
        InventoryController icOwner = new InventoryController();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //set owner(device user) item not-sharable
        icOwner.getByName(trade.getOwnerItem()).setSharable(false);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //set this trade to current incoming & In-progress
        setTradeResult(trade.getId(), true);
        //update on the tradelist
        //push tradelist online
        httpClientOwner.runPushTradeList();

        //pull borrower's tradelist from the webservice
        TradeList tradeList = httpClientBorrower.runPullTradeList();
        TradeListController tradeListControllerBorrower = new TradeListController(tradeList);
        //get this trade and set this trade to current outgoing & In-progress
        tradeListControllerBorrower.setTradeResult(trade.getId(), true);
        //push online
        httpClientBorrower.setTradeList(tradeList,trade.getBorrower());
        httpClientBorrower.runPushTradeList();

        Log.e(TAG,"Accept the trade");

    }

    public void declineTrade(int tradeIndex){
        Trade trade = getTradeRequests().get(tradeIndex);
        HttpClient httpClientOwner = new HttpClient(trade.getOwner(), this.trades);
        HttpClient httpClientBorrower = new HttpClient(trade.getBorrower());

        //set this trade to past incoming
        setTradeResult(trade.getId(), false);
        //update on the tradelist
        //push tradelist online
        httpClientOwner.runPushTradeList();

        //pull borrower's tradelist from the webservice
        TradeList tradeList = httpClientBorrower.runPullTradeList();
        TradeListController tradeListControllerBorrower = new TradeListController(tradeList);
        //get this trade and set this trade to current outgoing & In-progress
        tradeListControllerBorrower.setTradeResult(trade.getId(), false);
        //push online
        httpClientBorrower.setTradeList(tradeList,trade.getBorrower());
        httpClientBorrower.runPushTradeList();

        Log.e(TAG, "Declined the trade");
    }
}
