package com.example.zhaorui.dvdcollector.Controller;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

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
        ArrayList<Trade> tm = trades.getTrades();
        tm.add(trade);
        trades.setTrades(tm);

        //此时还需要给owner发送email，告知交易请求，以此来实现notification

    }

}
