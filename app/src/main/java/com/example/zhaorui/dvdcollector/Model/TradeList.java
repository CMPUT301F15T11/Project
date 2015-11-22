package com.example.zhaorui.dvdcollector.Model;

import com.example.zhaorui.dvdcollector.Model.Trade;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeList{
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
}
