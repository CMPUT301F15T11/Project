package com.example.zhaorui.dvdcollector.Model;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Trade;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeList{
    private static final String TAG = "TradeList";
    private static final String RESOURCE_URL_TRADE = "http://cmput301.softwareprocess.es:8080/cmput301f15t11/tradelist/";
    private static final String SEARCH_URL_TRADE = "http://cmput301.softwareprocess.es:8080/cmput301f15t11/tradelist/_search";
    /*
    A List containing all trades related to the device user
     */

    private ArrayList<Trade> trades;

    public TradeList(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public TradeList() {
        this.trades = new ArrayList<Trade>();
    }

    public void add(Trade trade){
        trades.add(trade);
    }

    public int size(){
        return trades.size();
    }

    public Trade get(int i){
        return trades.get(i);
    }

    public static String getResourceUrlTrade() {
        return RESOURCE_URL_TRADE;
    }

    public static String getSearchUrlTrade() {
        return SEARCH_URL_TRADE;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }


    // change a trade request's status and type, triggered when accepting or declining a trade
    // pending --> In-progress
    // pending --> Declined, Current xxxx --> Past xxxx
    public void setTradeResult(String id, Boolean isAccepted){
        for (Trade trade : trades){
            if(trade.getId().equals(id)){
                if(isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("In-progress");

                }else if(!isAccepted && trade.getStatus().equals("Pending")){
                    trade.setStatus("Declined");
                    changeTradeType(trade.getId());//have to change type to "past"
                }
                return;
            }
        }

        Log.e(TAG, "Decision has been made on this trade request");
    }

    // change the trade's type based on its current type
    public void changeTradeType(String id){
        for (Trade trade : trades){
            if (trade.getId().equals(id)){
                if(trade.getType().equals("Current Incoming")) {
                    trade.setType("Past Incoming");
                    trade.setName("Past Incoming" + " with " + trade.getBorrower() +
                            "\nID: " + trade.getId());
                    return;
                }else if (trade.getType().equals("Current Outgoing")){
                    trade.setType("Past Outgoing");
                    trade.setName("Past Outgoing" + " with " + trade.getOwner() +
                            "\nID: " + trade.getId());
                    return;
                }else {
                    return;
                    // do nothing
                }
            }
        }
        Log.e(TAG, "This trade's type has changed");
    }
    // get trade with a specified ID
    public Trade getTradeById(String id){
        for(Trade aTrade : trades){
            if(aTrade.getId().equals(id))
                return aTrade;
        }
        return null;
    }

    public TradeList getTradeRequests(){
        TradeList tradesToReturn = new TradeList();
        for(Trade aTrade : trades){
            if (aTrade.getStatus().equals("Pending") && aTrade.getType().equals("Current Incoming")){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

}
