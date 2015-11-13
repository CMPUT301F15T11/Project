package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeManager;

import java.util.ArrayList;

/**
 * Created by teppie on 12/11/15.
 */
public class TradeManagerController {
    private TradeManager trades;

    public TradeManagerController(TradeManager trades) {
        this.trades = trades;
    }

    public void addTrade(Trade trade){
        trades.add(trade);
    }
}
