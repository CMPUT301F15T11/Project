package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.StartTradeActivity;
import com.example.zhaorui.dvdcollector.View.TradeCenterActivity;

/**
 * Created by zhangxi on 15-11-29.
 */
public class testUS040101 extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    Button newTrade;

    public testUS040101() {
        super(TradeCenterActivity.class);
    }

    //US04.01.01
    public void testNewTrade() throws Exception {
        activity = getActivity();
        DataManager.instance().initFile("test", "test@test");
        assertNotNull(activity.findViewById(R.id.button_new_trade));
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(StartTradeActivity.class.getName(), null, false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newTrade = (Button) activity.findViewById(R.id.button_new_trade);
                newTrade.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

        am = getInstrumentation().addMonitor(TradeCenterActivity.class.getName(), null, false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button send = (Button) activity.findViewById(R.id.btn_send_trade_request);
                send.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

    }
}