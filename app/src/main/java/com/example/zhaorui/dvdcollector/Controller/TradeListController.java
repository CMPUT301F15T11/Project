package com.example.zhaorui.dvdcollector.Controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeListController {
    private static String TAG = "TradeListController";
    NotificationManager nm;
    private static final int NOTIFY_ID = 0x123;
    private TradeList trades;

    public TradeListController(TradeList trades) {
        this.trades = trades;
    }

    public void updateTradeList(){
        Log.e("UPDATE", "Now we are in updateTradeList");
        if (!ContextUtil.getInstance().isConnected())return;
        MyHttpClient httpClient = new MyHttpClient(User.instance().getProfile().getName());
        TradeList tradesReceive = httpClient.runPullTradeList();
        if (tradesReceive == null) {
            tradesReceive = User.instance().getTradeList();
            Log.e("Now in update","tradeRevice is null");
            httpClient.runPushTradeList();
            return;
        }
        User.instance().setTradeList(tradesReceive);
        trades = tradesReceive;
        DVD dvd;
        for (Trade trade:trades.getTrades()){
            if (trade.getChanged().equals("Pending")){

                // giving notification if a new trade request arrives
                nm = (NotificationManager)ContextUtil.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new Notification.Builder(ContextUtil.getInstance())
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_album_white_48dp)
                        .setTicker("Notification")
                        .setContentTitle("A new trade request")
                        .setContentText("You have a new trade request")
                        .build();
                nm.notify(NOTIFY_ID, notification);

                Log.e("jlhskjldfk", "trade changed to none pending");
                trade.setChanged("");

            }else if (trade.getChanged().equals("In progress")){
                for(String dvdName:trade.getBorrowerItemList()) {
                    dvd = User.instance().getInventory().getByName(dvdName);
                    if (dvd != null) {
                        dvd.setSharable(false);
                        Log.e("jlhskjldfk","dvd set to not sharable");
                    }
                }
                Log.e("jlhskjldfk", "trade changed to none in-progress");
                trade.setChanged("");

            }else if(trade.getChanged().equals("Declined")){

                Log.e("jlhskjldfk", "trade changed to none declined");
                trade.setChanged("");
            }else if(trade.getChanged().equals("Complete")){

                for(String dvdName:trade.getBorrowerItemList()) {
                    dvd = User.instance().getInventory().getByName(dvdName);
                    if (dvd != null) {
                        dvd.setSharable(true);
                    }
                }
                trade.setChanged("");
            }
        }
        httpClient.setTradeList(trades);
        Log.e("Push in update", "");
        httpClient.runPushTradeList();
    }

    // Add a trade to the tradelist
    public void addTrade(String borrower, String owner, ArrayList<String> borrowerItemNames,
                         String ownerItemName, String type, String status, String id){
        updateTradeList();
        Trade tradeBorrower = new Trade(borrower,owner,borrowerItemNames,ownerItemName,type,status);
        tradeBorrower.setName(tradeBorrower.getType() + "\nID: " + id);//set name
        tradeBorrower.setId(id);//set id
        Log.e("Adding a trade", "borrower name is: " + tradeBorrower.getName());
        trades.add(tradeBorrower);
        MyHttpClient httpClient = new MyHttpClient(borrower,trades);
        Log.e("In add trade","Push my tradelist");
        httpClient.runPushTradeList();
        ObserverManager.getInstance().notifying("Trades");
    }


    // returns the names of all trades in the given TradeList
    public ArrayList<String> getNames(TradeList trades){
        ArrayList<String> names = new ArrayList<>();
        for(Trade aTrade : trades.getTrades()){
            names.add(aTrade.getName());
        }
        return names;
    }

    // return IDs of all trades in the given TradeList
    public ArrayList<String> getIds(TradeList trades){
        ArrayList<String> ids = new ArrayList<>();
        for(Trade aTrade : trades.getTrades()){
            ids.add(aTrade.getId());
        }
        return ids;
    }

    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver("Trades", o);
    }

    // set an trade's status from "In-progress" to "Complete"
    public void setTradeComplete(String id) throws NullPointerException{
        //updateTradeList();
        Trade trade = trades.getTradeById(id);
        switch (trade.getStatus()) {
            case "Pending":
                Toast.makeText(ContextUtil.getInstance(), "This trade hasn't been accepted", Toast.LENGTH_SHORT).show();
                break;
            case "In-progress"://Set to complete also set type to past
                if (trade.getOwner().equals(User.instance().getProfile().getName())) {
                    trade.setStatus("Complete");
                    Log.e(TAG, trade.getStatus());
                    trades.changeTradeType(trade.getId());
                    User.instance().getInventory().getByName(trade.getOwnerItem()).setSharable(true);
                    MyHttpClient httpClient = new MyHttpClient(User.instance().getProfile().getName(),trades);
                    httpClient.runPushTradeList();

                    MyHttpClient myHttpClientBorrower = new MyHttpClient(trade.getBorrower());
                    //pull borrower's tradelist from the webservice
                    TradeList tradeList = myHttpClientBorrower.runPullTradeList();
                    Trade tradeBorrower = tradeList.getTradeById(trade.getId());
                    tradeBorrower.setChanged("Complete");
                    tradeBorrower.setStatus("Complete");
                    tradeList.changeTradeType(tradeBorrower.getId());

                    //push online
                    myHttpClientBorrower.setTradeList(tradeList);
                    myHttpClientBorrower.runPushTradeList();
                    ObserverManager.getInstance().notifying("Trades");
                } else {
                    Toast.makeText(ContextUtil.getInstance(), "Borrower can't set trade to complete", Toast.LENGTH_SHORT).show();
                }
                break;
            case "Complete":
                Toast.makeText(ContextUtil.getInstance(), "This trade is already complete", Toast.LENGTH_SHORT).show();
                break;
            case "Declined":
                Toast.makeText(ContextUtil.getInstance(), "This trade cannot be set to complete", Toast.LENGTH_SHORT).show();
                break;

        }
        Log.e(TAG, "Set the trade to complete");
    }

    public void sendTrade(String ownerName, ArrayList<String> borrowerDvdNameBuffer,
                          String ownerDvdNameBuffer, String id) throws NullPointerException{
        Log.e("Now we are in ", "sendTrade");
        MyHttpClient myHttpClientOwner = new MyHttpClient(ownerName);
        TradeList tradeList = myHttpClientOwner.runPullTradeList();
        // add this new trade to owner's tradelist
        Trade tradeOwner = new Trade(User.instance().getProfile().getName(),ownerName,
                borrowerDvdNameBuffer,ownerDvdNameBuffer,"Current Incoming","Pending");
        tradeOwner.setName(tradeOwner.getType() + "\nID: " + id);//set name
        tradeOwner.setId(id);//set id
        tradeOwner.setChanged("Pending");
        tradeList.add(tradeOwner);

        // push owner's tradelist
        myHttpClientOwner.setTradeList(tradeList);
        Log.e("In send trade", "Send owner's tradelist");
        myHttpClientOwner.runPushTradeList();

        Log.e(TAG,"Send the trade");
        ObserverManager.getInstance().notifying("Trades");
    }

    public void acceptTrade(int tradeIndex) throws NullPointerException{
        Trade trade = trades.getTradeRequests().get(tradeIndex);
        MyHttpClient myHttpClientOwner = new MyHttpClient(trade.getOwner(), this.trades);

        //set this dvd to not sharable because it's been borrowed
        //set owner(device user) item not-sharable
        User.instance().getInventory().getByName(trade.getOwnerItem()).setSharable(false);
        //set this trade to current incoming & In-progress
        trades.setTradeResult(trade.getId(), true);
        //update on the tradelist
        //push tradelist online
        myHttpClientOwner.runPushTradeList();

        MyHttpClient myHttpClientBorrower = new MyHttpClient(trade.getBorrower());
        //pull borrower's tradelist from the webservice
        TradeList tradeList = myHttpClientBorrower.runPullTradeList();
        Trade tradeBorrower = tradeList.getTradeById(trade.getId());
        tradeBorrower.setChanged("In progress");
        tradeList.setTradeResult(trade.getId(),true);
        //push online
        myHttpClientBorrower.setTradeList(tradeList);
        myHttpClientBorrower.runPushTradeList();

        Log.e(TAG, "Accept the trade");
        ObserverManager.getInstance().notifying("Trades");
    }

    public void declineTrade(int tradeIndex) throws NullPointerException{
        Trade trade = trades.getTradeRequests().get(tradeIndex);
        MyHttpClient myHttpClientOwner = new MyHttpClient(trade.getOwner(), this.trades);
        //set this trade to current incoming & In-progress
        trades.setTradeResult(trade.getId(), false);
        //update on the tradelist
        //push tradelist online
        myHttpClientOwner.runPushTradeList();

        MyHttpClient myHttpClientBorrower = new MyHttpClient(trade.getBorrower());
        //pull borrower's tradelist from the webservice
        TradeList tradeList = myHttpClientBorrower.runPullTradeList();
        Trade tradeBorrower = tradeList.getTradeById(trade.getId());
        try {
            tradeBorrower.setChanged("In progress");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        tradeList.setTradeResult(trade.getId(), false);
        //push online
        myHttpClientBorrower.setTradeList(tradeList);
        myHttpClientBorrower.runPushTradeList();

        Log.e(TAG, "Accept the trade");
        ObserverManager.getInstance().notifying("Trades");
    }

    // get all trades of the specified status,  eg. "In-progress"
    public TradeList getTradeOfStatus(String status){
        updateTradeList();
        TradeList tradesToReturn = new TradeList();
        for (Trade aTrade : trades.getTrades()){
            if (aTrade.getStatus().equals(status)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    // get all trades of the specified type, eg. "Current Outgoing"
    public TradeList getTradesOfType(String type){
        updateTradeList();
        TradeList tradesToReturn = new TradeList();
        for (Trade aTrade : trades.getTrades()){
            if (aTrade.getType().equals(type)){
                Log.e(TAG,type);
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public TradeList getTradeRequests(){
        return trades.getTradeRequests();
    }
}