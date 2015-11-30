package com.example.zhaorui.dvdcollector.Model;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Trade;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeList{
    /*
    A List containing all trades related to the device user
     */

    private static final String TAG = "TradeList";

    private ArrayList<Trade> trades;

    public TradeList(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public TradeList() {
        this.trades = new ArrayList<>();
    }

    public void add(Trade trade){
        trades.add(trade);
    }

    public int size(){
        return trades.size();
    }

    public Trade get(int i) throws NullPointerException,IndexOutOfBoundsException{
        try {
            return trades.get(i);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Trade> getTrades() {
        return trades;
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

        Log.d(TAG, "Decision has been made on this trade request");
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
        Log.d(TAG, "This trade's type has changed");
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
