package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.DVDAddActivity;
import com.example.zhaorui.dvdcollector.View.DVDInfoActivity;
import com.example.zhaorui.dvdcollector.View.MyInventoryActivity;
import com.example.zhaorui.dvdcollector.View.MyInventoryDialog;

import org.w3c.dom.Text;

/**
 * Created by dingkai on 15/10/9.
 */
public class testInventory extends ActivityInstrumentationTestCase2 {
    private String name = "Godfather";
    private String quantity = "1";
    private String quality = "HD";
    private String comment = "Nice movie";
    private Activity activity;

    public testInventory() {
        super(MyInventoryActivity.class);
    }

    public void testAdd() throws Exception{
        activity = getActivity();
        activity.openOptionsMenu();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null, false);
        getInstrumentation().invokeMenuActionSync(activity, R.id.add_to_my_inventory,0);
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = (EditText) activity.findViewById(R.id.ed_add_name);
                text.setText(name);
                text = (EditText) activity.findViewById(R.id.et_add_quantity);
                text.setText(quantity);
                text = (EditText) activity.findViewById(R.id.et_add_quality);
                text.setText(quality);
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
        assertEquals(dvd.getName(),name);
        assertEquals(dvd.getCategory(),"Games");
        assertEquals(dvd.getComments(),comment);
        assertEquals(dvd.getQuality(),quality);
        assertEquals(dvd.getQuantity(),quantity);
        assertTrue(dvd.isSharable());
        assertFalse(dvd.isHasPhoto());

    }

    public void testSeeDetails() throws Exception{
        testAdd();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDInfoActivity.class.getName(),null,false);
        // Code from http://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
        // Date: 2015-11-6
        // Catch DialogFragment
        final MyInventoryDialog dialog = (MyInventoryDialog)activity.getFragmentManager().findFragmentByTag("abc");
        assertTrue((dialog).getShowsDialog());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button check = dialog.getCheck();
                check.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        TextView text = (TextView) activity.findViewById(R.id.tv_name_dvdinfo);
        assertEquals(text.getText(),name);
        text = (TextView) activity.findViewById(R.id.tv_quality_dvdinfo);
        assertEquals(text.getText(),quality);
        text = (TextView) activity.findViewById(R.id.tv_quantity_dvdinfo);
        assertEquals(text.getText(),quantity);
        text = (TextView) activity.findViewById(R.id.tv_comment_view_dvdinfo);
        assertEquals(text.getText(),comment);

    }

    public void testAuthority() throws Exception{
        testAdd();
        assertTrue(User.instance().getInventory().get(0).isSharable());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null,false);
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
        final CheckBox sharable = (CheckBox)activity.findViewById(R.id.checkBox_sharable);
        assertTrue(sharable.isChecked());

        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sharable.setChecked(false);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

        assertFalse(User.instance().getInventory().get(0).isSharable());

    }

    public void testEditModify() throws Exception{
        testAdd();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null,false);
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
                text = (EditText) activity.findViewById(R.id.et_add_quality);
                text.setText(changed);
                text = (EditText) activity.findViewById(R.id.ed_add_comment);
                text.setText(changed);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        DVD dvd = User.instance().getInventory().get(0);
        assertEquals(dvd.getName(),changed);
        assertEquals(dvd.getComments(), changed);
        assertEquals(dvd.getQuality(),changed);
        assertEquals(dvd.getQuantity(), changed);

    }

    public void testDelete() throws Exception{
        testAdd();
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

        assertEquals(User.instance().getInventory().size(),0);

    }

    public void testCategory() throws Exception{
        testAdd();
        assertEquals(User.instance().getInventory().get(0).getCategory(), "Games");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(DVDAddActivity.class.getName(),null,false);
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
        final Spinner spinner = (Spinner)activity.findViewById(R.id.spinner);

        am = getInstrumentation().addMonitor(MyInventoryActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(1);
                Button save = (Button) activity.findViewById(R.id.btn_add_dvd_save);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        assertEquals(User.instance().getInventory().get(0).getCategory(),DVD.getCategories()[1]);

    }
}
