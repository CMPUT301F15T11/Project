package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;

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
     * Initialize a Tradelist to store the trade list information.
     */
    private TradeList trades;
    /**
     * Initialize a static trade list to store as "TradeListController".
     */
    private static String TAG = "TradeListController";

    /**
     * Get the user's trade list and restore them.
     * @param trades
     */
    public TradeListController(TradeList trades) {
        this.trades = trades;
    }

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
    // Add a trade to the tradelist
    public void addTrade(String borrower, String owner, ArrayList<String> borrowerItemNames,
                         String ownerItemName, String type, String status, String id){
        Trade trade = new Trade(borrower,owner,borrowerItemNames,ownerItemName,type,status);
        if (trade.getType().equals("Current Outgoing") || trade.getType().equals("Past Outgoing")) {
            trade.setName(trade.getType() + "\nID: " + id);//set name
            trade.setId(id);//set id
        }else{
            trade.setName(trade.getType() + "\nID: " + id);//set name
            trade.setId(id);//set id
        }
        trades.add(trade);
        ObserverManager.getInstance().notifying("Trades");
    }

    /**
     * This function is called when other function need to know trades.
     * @return trades
     */
    public TradeList getTrades() {
        return trades;
    }

    // get all trades of the specified type, eg. "Current Outgoing"

    /**
     * Get all trades of specified type
     * @param type string variable of type
     * @return trades to return
     */
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

    /**
     * Get all trades of the specified status
     * @param status string variable
     * @return trades to return
     */
    public TradeList getTradeOfStatus(String status){
        TradeList tradesToReturn = new TradeList();
        for (Trade aTrade : trades.getTrades()){
            if (aTrade.getStatus().equals(status)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
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

    // get trade with a specified ID
    /**
     * Get trade with a specified ID
     * @param id string variable of ID
     * @return null
     */
    public Trade getTradeById(String id){
        for(Trade aTrade : trades.getTrades()){
            if(aTrade.getId().equals(id))
                return aTrade;
        }
        return null;
    }

    /**
     * This function is called when other function needs trade request
     * @return trades to return
     */
    public TradeList getTradeRequests(){
        TradeList tradesToReturn = new TradeList();
        for(Trade aTrade : trades.getTrades()){
            if (aTrade.getStatus().equals("Pending") && aTrade.getType().equals("Current Incoming")){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    /**
     * To add observer
     * @param o observer
     */
    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver("Trades", o);
    }

    // change the trade's type based on its current type
    /**
     * Change the trade's type based on its current type
     * @param id string variable of ID
     */
    public void changeTradeType(String id){
        for (Trade trade : trades.getTrades()){
            if (trade.getId().equals(id)){
                if(trade.getType().equals("Current Incoming")) {
                    trade.setType("Past Incoming");
                    trade.setName("Past Incoming" + " with " + trade.getBorrower() +
                            "\nID: " + trade.getId());
                    ObserverManager.getInstance().notifying("Trades");
                    return;
                }else if (trade.getType().equals("Current Outgoing")){
                    trade.setType("Past Outgoing");
                    trade.setName("Past Outgoing" + " with " + trade.getOwner() +
                            "\nID: " + trade.getId());
                    ObserverManager.getInstance().notifying("Trades");
                    return;
                }else {
                    return;
                    // do nothing
                }
            }
        }
        Log.e(TAG, "This trade's type has changed");
    }

    // change a trade request's status and type, triggered when accepting or declining a trade
    // pending --> In-progress
    // pending --> Declined, Current xxxx --> Past xxxx

    /**
     * Change status and type of trade request when accept or decline a trade
     * @param id string variable of IDs
     * @param isAccepted Boolean variable of whether been accepted
     */
    public void setTradeResult(String id, Boolean isAccepted){
        for (Trade trade : trades.getTrades()){
            if(trade.getId().equals(id)){
                if(isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("In-progress");
                    ObserverManager.getInstance().notifying("Trades");

                }else if(!isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("Declined");
                    changeTradeType(trade.getId());//have to change type to "past"
                    ObserverManager.getInstance().notifying("Trades");
                }
                return;
            }
        }

        Log.e(TAG, "Decision has been made on this trade request");
    }

    // set an trade's status from "In-progress" to "Complete"
    /**
     * Set status of trade from "In-progress" to "Complete"
     * @param id string variable of ID
     */
    public void setTradeComplete(String id){
        for (Trade trade : trades.getTrades()){
            if (trade.getId().equals(id)){
                switch (trade.getStatus()) {
                    case "Pending":
                        Toast.makeText(ContextUtil.getInstance(),"This trade hasn't been accepted", Toast.LENGTH_SHORT).show();
                        break;
                    case "In-progress"://Set to complete also set type to past
                        if(trade.getOwner().equals(User.instance().getProfile().getName())) {
                            trade.setStatus("Complete");
                            changeTradeType(trade.getId());
                            ObserverManager.getInstance().notifying("Trades");
                        }else {
                            Toast.makeText(ContextUtil.getInstance(),"Borrower can't set trade to complete", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Complete":
                        Toast.makeText(ContextUtil.getInstance(),"This trade is already complete", Toast.LENGTH_SHORT).show();
                        break;
                    case "Declined":
                        Toast.makeText(ContextUtil.getInstance(),"This trade cannot be set to complete", Toast.LENGTH_SHORT).show();
                        break;
                }
                return;
            }
        }

        Log.e(TAG,"Set the trade to complete");
    }

    /**
     * Update trade list
     * @param userName string variable of username
     */
    public void updateTradeList(String userName){
        MyHttpClient myHttpClient = new MyHttpClient(userName);
        this.trades = myHttpClient.runPullTradeList();
        //////////////////////////////////////////////////////////////////////////////////////////
        //TODO:可能不对
        User.instance().getTradeList().setTrades(trades.getTrades());
        ObserverManager.getInstance().notifying("Trades");
        Log.e(TAG, "Pulled tradelist");
    }

    /**
     * Push trade details
     * @param ownerName string variable of ownername
     * @param borrowerDvdNameBuffer arraylist variable of BorrowerDvdNameBuffer
     * @param ownerDvdNameBuffer string variable of ownerDvdNameBuffer
     * @param id string variable of ID
     */
    public void sendTrade(String ownerName, ArrayList<String> borrowerDvdNameBuffer,
                          String ownerDvdNameBuffer, String id){
        // push User(Borrower)'s tradelist
        MyHttpClient myHttpClientBorrower = new MyHttpClient(User.instance().getProfile().getName());
        myHttpClientBorrower.setTradeList(User.instance().getTradeList(), User.instance().getProfile().getName());
        myHttpClientBorrower.runPushTradeList();

        // pull owner's tradelist
        MyHttpClient myHttpClientOwner = new MyHttpClient(ownerName);
        TradeList tradeList = myHttpClientOwner.runPullTradeList();
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
        myHttpClientOwner.setTradeList(tradeListControllerOwner.getTrades(), ownerName);
        myHttpClientOwner.runPushTradeList();

        //send email to owner to notify him

        Log.e(TAG,"Send the trade");
        ObserverManager.getInstance().notifying("Trades");
    }

    /**
     * Push counter trade list
     * @param borrowerName string variable of borrower's name
     * @param ownerName string variable of owner's name
     * @param borrowerDvdNameBuffer array list of BorrowerDvdNameBuffer
     * @param ownerDvdNameBuffer string variable of OwnerDvdNameBuffer
     * @param id string variable ID
     */
    public void sendCounterTrade(String borrowerName, String ownerName, ArrayList<String> borrowerDvdNameBuffer,
                          String ownerDvdNameBuffer, String id){
        // borrower --> User(owner in the counter-trade)
        // owner --> One friend(borrower in the counter-trade)

        // push Borrower's tradelist
        MyHttpClient myHttpClientBorrower = new MyHttpClient(borrowerName);
        myHttpClientBorrower.setTradeList(User.instance().getTradeList(), borrowerName);
        myHttpClientBorrower.runPushTradeList();

        // pull owner's tradelist
        MyHttpClient myHttpClientOwner = new MyHttpClient(ownerName);
        TradeList tradeList = myHttpClientOwner.runPullTradeList();
        // add this new trade to borrower's tradelist
        // NOTE: In borrower's trade center, since this counter trade is a new trade request
        // the role of owner and borrower reverse
        TradeListController tradeListControllerOwner = new TradeListController(tradeList);
        tradeListControllerOwner.addTrade(borrowerName,
                ownerName,
                borrowerDvdNameBuffer,
                ownerDvdNameBuffer,
                "Current Incoming",
                "Pending",
                id);
        // push owner's tradelist
        myHttpClientOwner.setTradeList(tradeListControllerOwner.getTrades(), ownerName);
        myHttpClientOwner.runPushTradeList();

        Log.e(TAG, "Send the counter-trade");
        ObserverManager.getInstance().notifying("Trades");
    }

    /**
     * Owner Accept trades from borrower
     * @param tradeIndex int variable tradeindex
     */
    public void acceptTrade(int tradeIndex){
        Trade trade = getTradeRequests().get(tradeIndex);
        MyHttpClient myHttpClientOwner = new MyHttpClient(trade.getOwner(), this.trades);
        MyHttpClient myHttpClientBorrower = new MyHttpClient(trade.getBorrower());

        //set this dvd to not sharable because it's been borrowed
        InventoryController icOwner = new InventoryController();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //set owner(device user) item not-sharable
        //TODO: 需要修改此处
        //icOwner.getByName(trade.getOwnerItem()).setSharable(false);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //set this trade to current incoming & In-progress
        setTradeResult(trade.getId(), true);
        //update on the tradelist
        //push tradelist online
        myHttpClientOwner.runPushTradeList();

        //pull borrower's tradelist from the webservice
        TradeList tradeList = myHttpClientBorrower.runPullTradeList();
        TradeListController tradeListControllerBorrower = new TradeListController(tradeList);
        //get this trade and set this trade to current outgoing & In-progress
        tradeListControllerBorrower.setTradeResult(trade.getId(), true);
        //push online
        myHttpClientBorrower.setTradeList(tradeList,trade.getBorrower());
        myHttpClientBorrower.runPushTradeList();

        Log.e(TAG, "Accept the trade");
        ObserverManager.getInstance().notifying("Trades");

    }

    /**
     * Owner decline trades from borrowers
     * @param tradeIndex int variable of tradeIndex
     */
    public void declineTrade(int tradeIndex){
        Trade trade = getTradeRequests().get(tradeIndex);
        MyHttpClient myHttpClientOwner = new MyHttpClient(trade.getOwner(), this.trades);
        MyHttpClient myHttpClientBorrower = new MyHttpClient(trade.getBorrower());

        //set this trade to past incoming
        setTradeResult(trade.getId(), false);
        //update on the tradelist
        //push tradelist online
        myHttpClientOwner.runPushTradeList();

        //pull borrower's tradelist from the webservice
        TradeList tradeList = myHttpClientBorrower.runPullTradeList();
        TradeListController tradeListControllerBorrower = new TradeListController(tradeList);
        //get this trade and set this trade to current outgoing & In-progress
        tradeListControllerBorrower.setTradeResult(trade.getId(), false);
        //push online
        myHttpClientBorrower.setTradeList(tradeList,trade.getBorrower());
        myHttpClientBorrower.runPushTradeList();

        Log.e(TAG, "Declined the trade");
        ObserverManager.getInstance().notifying("Trades");
    }
}
