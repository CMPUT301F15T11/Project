package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.DVDAddActivity;
import com.example.zhaorui.dvdcollector.View.FriendInventoryActivity;
import com.example.zhaorui.dvdcollector.View.FriendListActivity;
import com.example.zhaorui.dvdcollector.View.FriendListDialog;
import com.example.zhaorui.dvdcollector.View.MyInventoryActivity;
import com.example.zhaorui.dvdcollector.View.MyInventoryDialog;
import com.example.zhaorui.dvdcollector.View.SearchDialog;


/**
 * Created by dingkai on 15/10/9.
 */
public class testFriends extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    String name = "Jack";

    public testFriends() {
        super(FriendListActivity.class);
    }

    public void testAddFriend() throws Exception {
        DataManager.instance().initFile("test");
        assertEquals(User.instance().getFriends().size(), 0);
        activity = getActivity();
        activity.openOptionsMenu();
        getInstrumentation().invokeMenuActionSync(activity, R.id.friendlist_add, 0);
        getInstrumentation().waitForIdleSync();
        final SearchDialog dialog = (SearchDialog)activity.getFragmentManager().findFragmentByTag("abc");
        assertTrue((dialog).getShowsDialog());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText text = dialog.getEditText();
                text.setText(name);
                Button search = dialog.getSearch();
                search.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals(User.instance().getFriends().size(),1);
        assertEquals(User.instance().getFriends().get(0),"Jack");
    }

    public void testRemoveFriend() throws Exception {
        testAddFriend();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = (ListView) activity.findViewById(R.id.listViewFriendList);
                View v = listView.getChildAt(0);
                listView.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        final FriendListDialog dialog = (FriendListDialog)activity.getFragmentManager().findFragmentByTag("abc");
        assertTrue((dialog).getShowsDialog());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button remove = dialog.getRemove();
                remove.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        assertEquals(User.instance().getFriends().size(),0);

    }

}