package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.provider.ContactsContract;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.DVDAddActivity;
import com.example.zhaorui.dvdcollector.View.DVDInfoActivity;
import com.example.zhaorui.dvdcollector.View.MyInventoryActivity;
import com.example.zhaorui.dvdcollector.View.MyInventoryDialog;
import com.example.zhaorui.dvdcollector.View.PhotoActivity;

/**
 * Created by wsong1 on 2015/11/29 0029.
 */
public class testPhoto extends ActivityInstrumentationTestCase2 {
    private String name = "Godfather";
    private String quantity = "1";
    private String comment = "Nice movie";
    private Activity activity;

    public testPhoto() {super(MyInventoryActivity.class);}

    public void testAttachPhoto(){
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
        assertEquals(dvd.getCategory(),"Games");
        assertEquals(bar.length, 1);
        assertEquals(dvd.getComments(), comment);
        assertEquals(dvd.getQuantity(), quantity);
        assertTrue(dvd.isSharable());

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        am = getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null,false);
        final MyInventoryDialog dialog = (MyInventoryDialog)activity.getFragmentManager().findFragmentByTag("abc");
        assertTrue((dialog).getShowsDialog());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button edit = dialog.getEdit();
                edit.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        final String changed = "Changed";
        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = (EditText) activity.findViewById(R.id.ed_add_name);
                text.setText(changed);
                text = (EditText) activity.findViewById(R.id.et_add_quantity);
                text.setText(changed);
                bar[0] = (RatingBar) activity.findViewById(R.id.ratingBar);
                bar[0].setRating(3.2f);
                text = (EditText) activity.findViewById(R.id.ed_add_comment);
                text.setText(changed);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

    }

    public void testViewPhoto(){
        activity = getActivity();
        activity.openOptionsMenu();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null, false);
        getInstrumentation().invokeMenuActionSync(activity, R.id.add_to_my_inventory, 0);
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
        assertEquals(dvd.getCategory(), "Games");
        assertEquals(bar.length, 1);
        assertTrue(dvd.isSharable());

    }

    public void testDeletePhoto(){
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
                text.setText("123");
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
        assertEquals(dvd.getCategory(),"Games");
        assertEquals(bar.length, 1);
        assertTrue(dvd.isSharable());

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        final MyInventoryDialog dialog = (MyInventoryDialog)activity.getFragmentManager().findFragmentByTag("abc");
        assertTrue((dialog).getShowsDialog());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button remove = dialog.getRemove();
                remove.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertEquals(User.instance().getInventory().size(), 2);
    }

    public void testCheckSize(){
        activity = getActivity();
        activity.openOptionsMenu();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null, false);
        getInstrumentation().invokeMenuActionSync(activity, R.id.add_to_my_inventory, 0);
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
        assertEquals(dvd.getCategory(), "Games");
        assertEquals(bar.length, 1);
        assertTrue(dvd.isSharable());
        PhotoActivity photoActivity = new PhotoActivity();
        assertEquals(photoActivity.getChangingConfigurations(), 0);
    }

    public void testManualDownload(){
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
                text.setText("789");
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
        assertEquals(bar.length, 1);
        assertTrue(dvd.isSharable());

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        am = getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null,false);
        final MyInventoryDialog dialog = (MyInventoryDialog)activity.getFragmentManager().findFragmentByTag("abc");
        assertTrue((dialog).getShowsDialog());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button edit = dialog.getEdit();
                edit.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        final String changed = "Changed";
        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = (EditText) activity.findViewById(R.id.ed_add_name);
                text.setText(changed);
                text = (EditText) activity.findViewById(R.id.et_add_quantity);
                text.setText(changed);
                bar[0] = (RatingBar) activity.findViewById(R.id.ratingBar);
                bar[0].setRating(3.2f);
                text = (EditText) activity.findViewById(R.id.ed_add_comment);
                text.setText(changed);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
    }
}