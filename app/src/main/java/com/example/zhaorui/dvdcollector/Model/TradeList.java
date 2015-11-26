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
        this.trades = new ArrayList<Trade>();
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
    public Trade get(int i){
        return trades.get(i);
    }

    /**
     * Get static String of resource url trade
     * @return resource url trade
     */
    public static String getResourceUrlTrade() {
        return RESOURCE_URL_TRADE;
    }

    /**
     * Get static String of searching url trade
     * @return searched url trade
     */
    public static String getSearchUrlTrade() {
        return SEARCH_URL_TRADE;
    }

    /**
     * get array list of trades
     * @return trades
     */
    public ArrayList<Trade> getTrades() {
        return trades;
    }

    /**
     * Initialize array list of trades
     * @param trades array list variable
     */
    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }
}
