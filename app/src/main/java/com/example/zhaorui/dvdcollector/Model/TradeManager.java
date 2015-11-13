package com.example.zhaorui.dvdcollector.Model;

import com.example.zhaorui.dvdcollector.Model.Trade;

import java.util.ArrayList;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeManager extends ArrayList<Trade>{
    /*
    A List containing all trades related to the device user
     */


    public TradeManager() {
    }
    public Trade getTradeAtIndex(int index){
        return this.get(index);
    }

    public ArrayList<Trade> getAllCurrentIncomingTrades(){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this){
            if (aTrade.getType()=="Current Incoming"){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public ArrayList<Trade> getAllCurrentOutgoingTrades(){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this){
            if (aTrade.getType()=="Current Outgoing"){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public ArrayList<Trade> getAllPastIncomingTrades(){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this){
            if (aTrade.getType()=="Past Incoming"){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public ArrayList<Trade> getAllPastOutingTrades(){
        ArrayList<Trade> tradesToReturn = new ArrayList<>();
        for (Trade aTrade : this){
            if (aTrade.getType()=="Past Outgoing"){
                tradesToReturn.add(aTrade);
            }
        }
        return tradesToReturn;
    }

    public ArrayList<String> getListableNames(TradeManager trades){
        ArrayList<String> names = new ArrayList<>();
        for(Trade aTrade : trades){
            names.add("Trade with "+aTrade.getBorrower());
        }
        return names;
    }

}
