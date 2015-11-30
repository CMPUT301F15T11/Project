package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.DVDAddActivity;
import com.example.zhaorui.dvdcollector.View.FriendInventoryDialog;
import com.example.zhaorui.dvdcollector.View.FriendListDialog;
import com.example.zhaorui.dvdcollector.View.MyInventoryActivity;
import com.example.zhaorui.dvdcollector.View.SearchDialog;

import junit.framework.TestCase;

/**
 * Created by wsong1 on 2015/11/28 0028.
 */
public class testSearch extends ActivityInstrumentationTestCase2 {
    String name = "test3";
    private String quantity = "1";
    private String comment = "Nice movie";
    private Activity activity;
    public testSearch() {
        super(MyInventoryActivity.class);
    }

    public void testSearchFriendInventory(){
        activity = getActivity();
        activity.openOptionsMenu();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null, false);
        getInstrumentation().invokeMenuActionSync(activity, R.id.add_to_my_inventory,0);
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        final RatingBar[] bar = new RatingBar[1];
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = (EditText) activity.findViewById(R.id.ed_add_name);
                text.setText(name);
                text = (EditText) activity.findViewById(R.id.et_add_quantity);
                text.setText(quantity);
                // Code from https://android.googlesource.com/platform/cts/+/719bcd0dfdbe2c015746dc9738390c59c53f8268/tests/tests/widget/src/android/widget/cts/RatingBarTest.java
                // Date: 2015-11-28
                // Catch Ratingbar Test
                bar[0] = (RatingBar) activity.findViewById(R.id.ratingBar);
                bar[0].setRating(2.2f);
                text = (EditText) activity.findViewById(R.id.ed_add_comment);
                text.setText(comment);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        DVD dvd = User.instance().getInventory().get(0);
        assertEquals(dvd.getName(), name);
        assertEquals(dvd.getCategory(), "Games");
        assertEquals(bar.length, 1);
        assertEquals(dvd.getComments(), comment);
        assertEquals(dvd.getQuantity(), quantity);
        assertTrue(dvd.isSharable());
    }

    public void testSearchFriendCategory(){
        activity = getActivity();
        activity.openOptionsMenu();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null, false);
        getInstrumentation().invokeMenuActionSync(activity, R.id.add_to_my_inventory,0);
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        final RatingBar[] bar = new RatingBar[1];
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = (EditText) activity.findViewById(R.id.ed_add_name);
                text.setText(name);
                text = (EditText) activity.findViewById(R.id.et_add_quantity);
                text.setText(quantity);
                // Code from https://android.googlesource.com/platform/cts/+/719bcd0dfdbe2c015746dc9738390c59c53f8268/tests/tests/widget/src/android/widget/cts/RatingBarTest.java
                // Date: 2015-11-28
                // Catch Ratingbar Test
                bar[0] = (RatingBar) activity.findViewById(R.id.ratingBar);
                bar[0].setRating(2.2f);
                text = (EditText) activity.findViewById(R.id.ed_add_comment);
                text.setText(comment);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        DVD dvd = User.instance().getInventory().get(0);
        assertEquals(dvd.getName(), name);
        assertEquals(dvd.getCategory(), "Games");
        assertEquals(bar.length, 1);
        assertEquals(dvd.getComments(), comment);
        assertEquals(dvd.getQuantity(), quantity);
        assertTrue(dvd.isSharable());

    }
}
