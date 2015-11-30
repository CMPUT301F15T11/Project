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
    /**
     *Initialize array list of trades
     */
    private ArrayList<Trade> trades;
    /**
     * Initialize trade of trade list
     * @param trades trades from trades list
     */
    public TradeList(ArrayList<Trade> trades) {
        this.trades = trades;
    }
    /**
     * Initialize array list of trade list
     */
    public TradeList() {
        this.trades = new ArrayList<>();
    }
    /**
     * Add trades
     * @param trade array list of variable
     */
    public void add(Trade trade){
        trades.add(trade);
    }
    /**
     * Get size of trades
     * @return size of trades
     */
    public int size(){
        return trades.size();
    }
    /**
     * Get trade by index
     * @param i int variable
     * @return  trade by index
     */
    public Trade get(int i) throws NullPointerException,IndexOutOfBoundsException{
        try {
            return trades.get(i);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * get array list of trades
     * @return trades
     */
    public ArrayList<Trade> getTrades() {
        return trades;
    }

    // change a trade request's status and type, triggered when accepting or declining a trade
    // pending --> In-progress
    // pending --> Declined, Current xxxx --> Past xxxx

    /**
     * set the result of the trade
     * @param id a string variable
     * @param isAccepted a Boolean variable
     */
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

    /**
     * change the type of the trade
     * @param id a string variable
     */
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

    /**
     * get trade by its id
     * @param id a string variable
     * @return a trade or null
     */
    public Trade getTradeById(String id){
        for(Trade aTrade : trades){
            if(aTrade.getId().equals(id))
                return aTrade;
        }
        return null;
    }

    /**
     * get request of trade
     * @return trade
     */
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
