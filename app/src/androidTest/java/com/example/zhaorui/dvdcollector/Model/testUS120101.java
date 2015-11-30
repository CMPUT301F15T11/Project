package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.TradeCenterActivity;
import com.example.zhaorui.dvdcollector.View.TradeRequestsActivity;

/**
 * Created by zhangxi on 15-11-29.
 */
public class testUS120101 extends ActivityInstrumentationTestCase2 {
    Activity activity;
    public testUS120101() {
        super(TradeCenterActivity.class);
    }

    public void testTopTraders() throws Exception {
        activity = getActivity();
        DataManager.instance().initFile("test", "test@test");
        assertNotNull(activity.findViewById(R.id.button_tradeRequests));
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(TradeRequestsActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button TradeR = (Button) activity.findViewById(R.id.button_top_traders);
                TradeR.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
    }
}
