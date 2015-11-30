package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.TradeCenterActivity;
import com.example.zhaorui.dvdcollector.View.TradeRequestDetailActivity;
import com.example.zhaorui.dvdcollector.View.TradeRequestsActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxi on 15-11-29.
 */
public class testUS040301 extends ActivityInstrumentationTestCase2 {
    Activity activity;
    public testUS040301() {
        super(TradeCenterActivity.class);
    }
    public void testDecline() throws Exception {
        int size=User.instance().getTradeList().size();
        activity = getActivity();
        DataManager.instance().initFile("test", "test@test");
        assertNotNull(activity.findViewById(R.id.button_tradeRequests));
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(TradeRequestsActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button TradeR = (Button) activity.findViewById(R.id.button_tradeRequests);
                TradeR.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

        am = getInstrumentation().addMonitor(TradeRequestDetailActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listView_trade_requests);
                View v = listView.getChildAt(0);
                //listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);



    }

}
