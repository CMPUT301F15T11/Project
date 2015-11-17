package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;

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

    public void addTrade(Trade trade){
        ArrayList<Trade> tm = trades.getTrades();
        tm.add(trade);
        trades.setTrades(tm);
    }

    // get all trades of the specified type, eg. "Current Outgoing"
    public ArrayList<Trade> getTradesOfType(String type){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : trades.getTrades()){
            if (aTrade.getType().equals(type)){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    // get all trades of the specified status,  eg. "In-progress"
    public ArrayList<Trade> getTradeOfStatus(String status){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this.trades.getTrades()){
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

    public void addObserver(Observer o){
        trades.getObs().addObserver(o);
    }
}
