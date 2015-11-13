package com.example.zhaorui.dvdcollector.Model;

import com.example.zhaorui.dvdcollector.Model.Trade;

import java.util.ArrayList;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeManager{
    /*
    A List containing all trades related to the device user
     */
    private ArrayList<Trade> trades;

    public TradeManager() {
        this.trades = new ArrayList<>();
    }

    public Trade getTradeAtIndex(int index){
        return this.trades.get(index);
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    // get all trades of the specified type, eg. "Current Outgoing"
    public ArrayList<Trade> getTradesOfType(String type){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this.trades){
            if (aTrade.getType().equals(type)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public ArrayList<String> getNames(ArrayList<Trade> trades){
        ArrayList<String> names = new ArrayList<>();
        for(Trade aTrade : trades){
            names.add(aTrade.getName());
        }
        return names;
    }

    public int getSize(){
        return this.trades.size();
    }

}
