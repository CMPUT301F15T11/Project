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
/**
 * The trade list controller
 */
public class TradeListController {
    /**
     * Initialize a static trade list to store as "TradeListController".
     */
    private static String TAG = "TradeListController";
    NotificationManager nm;
    /**
     * Initialize a static index to store as 0x123
     */
    private static final int NOTIFY_ID = 0x123;
    /**
     * Initialize a Tradelist to store the trade list information.
     */
    private TradeList trades;

    /**
     * Get the user's trade list and restore them.
     * @param trades
     */
    public TradeListController(TradeList trades) {
        this.trades = trades;
    }
    /**
     * Update trade list
     */
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
            if (trade.getChanged()==null)return;
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
    /**
     *Add trade to trade list
     * @param borrower string variable of borrowers
     * @param owner string variable of owner
     * @param borrowerItemNames arraylist variable of borrower item name
     * @param ownerItemName string variable of owner item name
     * @param type string variable of type
     * @param status string variable of status
     * @param id string variable of is
     */
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
    /**
     * This function is called when other function need to name of trades from given trade list.
     * @param trades string variable of trade
     * @return names
     */
    public ArrayList<String> getNames(TradeList trades){
        ArrayList<String> names = new ArrayList<>();
        for(Trade aTrade : trades.getTrades()){
            names.add(aTrade.getName());
        }
        return names;
    }

    // return IDs of all trades in the given TradeList
    /**
     * Return IDs of all trades in the given TradeList
     * @param trades string variable of trades
     * @return IDs
     */
    public ArrayList<String> getIds(TradeList trades){
        ArrayList<String> ids = new ArrayList<>();
        for(Trade aTrade : trades.getTrades()){
            ids.add(aTrade.getId());
        }
        return ids;
    }
    /**
     * To add observer
     * @param o observer
     */
    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver("Trades", o);
    }

    // set an trade's status from "In-progress" to "Complete"
    /**
     * Set status of trade from "In-progress" to "Complete"
     * @param id string variable of ID
     */
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
                    try {
                        tradeBorrower.setChanged("Complete");
                        tradeBorrower.setStatus("Complete");
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
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
    /**
     * Push trade details
     * @param ownerName string variable of ownername
     * @param borrowerDvdNameBuffer arraylist variable of BorrowerDvdNameBuffer
     * @param ownerDvdNameBuffer string variable of ownerDvdNameBuffer
     * @param id string variable of ID
     */
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
    /**
     * Owner Accept trades from borrower
     * @param tradeIndex int variable tradeindex
     */
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
        try {
            tradeBorrower.setChanged("In progress");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        tradeList.setTradeResult(trade.getId(),true);
        //push online
        myHttpClientBorrower.setTradeList(tradeList);
        myHttpClientBorrower.runPushTradeList();

        Log.e(TAG, "Accept the trade");
        ObserverManager.getInstance().notifying("Trades");
    }
    /**
     * Owner decline trades from borrowers
     * @param tradeIndex int variable of tradeIndex
     */
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
    /**
     * Get all trades of the specified status
     * @param status string variable
     * @return trades to return
     */
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
    /**
     * Get all trades of specified type
     * @param type string variable of type
     * @return trades to return
     */
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
    /**
     * This function is called when other function need to know trades.
     * @return trades
     */
    public TradeList getTradeRequests(){
        return trades.getTradeRequests();
    }
}