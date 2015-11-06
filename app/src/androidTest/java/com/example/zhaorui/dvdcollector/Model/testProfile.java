package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.ConfigActivity;
import com.example.zhaorui.dvdcollector.View.MyInventoryActivity;
import com.example.zhaorui.dvdcollector.View.MyProfileActivity;

import org.w3c.dom.Text;

/**
 * Created by dingkai on 15/11/6.
 */
public class testProfile extends ActivityInstrumentationTestCase2 {
    private Activity activity;

    public testProfile() {
        super(ConfigActivity.class);
    }

    public void testViewProfile() throws Exception {
        activity = getActivity();
        DataManager.instance().initFile("test");
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(MyProfileActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button profile = (Button) activity.findViewById(R.id.btn_to_my_profile);
                profile.performClick();
            }
        });

        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

        TextView text = (TextView) activity.findViewById(R.id.name_my_profile);
        assertEquals(text.getText(),"test");
    }

    public void testEditProfile() throws Exception {
        testViewProfile();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(ConfigActivity.class.getName(),null,false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) activity.findViewById(R.id.city_my_profile);
                text.setText("Edmonton");
                text = (TextView) activity.findViewById(R.id.contact_my_profile);
                text.setText("dingkai@ualberta.ca");
                Button save = (Button) activity.findViewById(R.id.btn_save_my_profile);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        UserProfile profile = User.instance().getProfile();

        assertEquals(profile.getName(),"test");
        assertEquals(profile.getCity(),"Edmonton");
        assertEquals(profile.getContact(),"dingkai@ualberta.ca");
    }
}
