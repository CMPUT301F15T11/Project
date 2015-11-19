package com.example.zhaorui.dvdcollector.Model;

import com.example.zhaorui.dvdcollector.Model.Trade;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeList {
    /*
    A List containing all trades related to the device user
     */
    private Obs obs;

    private ArrayList<Trade> trades;

    public TradeList() {
        obs = new Obs();
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
//        obs.notifying();
    }

    public int getSize(){
        return this.trades.size();
    }

    private class Obs extends Observable {
        public void notifying(){
            super.setChanged();
            super.notifyObservers();
        }
    }
    public void notifying(){obs.notifying();}
    public Observable getObs() {
        return obs;
    }

}
