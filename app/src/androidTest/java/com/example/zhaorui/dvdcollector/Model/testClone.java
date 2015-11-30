package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.FriendListActivity;
import com.example.zhaorui.dvdcollector.View.FriendListDialog;
import com.example.zhaorui.dvdcollector.View.MyInventoryActivity;
import com.example.zhaorui.dvdcollector.View.SearchDialog;

import junit.framework.TestCase;

/**
 * Created by wsong1 on 2015/11/28 0028.
 */
public class testClone extends ActivityInstrumentationTestCase2 {
    public testClone() {super(MyInventoryActivity.class);}
    private Activity activity;

    public void testClone(){
        DataManager.instance().initFile("test", "test@gmail.com");
        assertEquals(User.instance().getFriends().size(), 0);
        activity = getActivity();
        activity.openOptionsMenu();
        getInstrumentation().invokeMenuActionSync(activity, R.id.friendlist_add, 0);
        getInstrumentation().waitForIdleSync();
        final SearchDialog dialog = (SearchDialog)activity.getFragmentManager().findFragmentByTag("Will");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = dialog.getEditText();
                text.setText("test");
                Button search = dialog.getSearch();
                search.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals(User.instance().getFriends().size(), 1);
        assertEquals(User.instance().getFriends().get(0), "Will");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewFriendList);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();
        final FriendListDialog dialog1 = (FriendListDialog)activity.getFragmentManager().findFragmentByTag("Will");
        assertTrue((dialog1).getShowsDialog());

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button checkInventory = dialog1.getCheckInventory();
                checkInventory.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewMyInventory);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });



    }


}