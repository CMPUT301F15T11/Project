package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.BrowseTradeLogActivity;
import com.example.zhaorui.dvdcollector.View.TradeCenterActivity;

/**
 * Created by zhangxi on 15-11-29.
 */
public class testUS040801flow1 extends ActivityInstrumentationTestCase2 {
    Activity activity;
    public testUS040801flow1() {
        super(TradeCenterActivity.class);
    }

    public void testBrowsePast1() throws Exception {
        activity = getActivity();
        DataManager.instance().initFile("test", "test@test");
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(BrowseTradeLogActivity.class.getName(), null, false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button TradeL = (Button) activity.findViewById(R.id.button_trade_logs);
                TradeL.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

        am = getInstrumentation().addMonitor(BrowseTradeLogActivity.class.getName(), null, false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button past_incoming = (Button) activity.findViewById(R.id.btn_browse_trades_past_incoming);
                past_incoming.performClick();


            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);



    }
}
