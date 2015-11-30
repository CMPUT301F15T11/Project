package com.example.zhaorui.dvdcollector.Model;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import com.example.zhaorui.dvdcollector.R;
import com.example.zhaorui.dvdcollector.View.ConfigActivity;
import com.example.zhaorui.dvdcollector.View.SwitchDownloadActivity;

/**
 * Created by wsong1 on 2015/11/24 0024.
 */
public class testConfiguration extends ActivityInstrumentationTestCase2 {
    private Activity activity;
    public testConfiguration() {
        super(ConfigActivity.class);
    }

    public void testIsConfirm() throws Exception {
        activity = getActivity();
        activity.openOptionsMenu();
        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(SwitchDownloadActivity.class.getName(),null, false);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button download = (Button) activity.findViewById(R.id.btn_download_switch);
                download.performClick();
            }
        });
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);

        am = getInstrumentation().addMonitor(ConfigActivity.class.getName(),null,false);
        final CheckBox box = (CheckBox) activity.findViewById(R.id.checkbox_download);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                box.setChecked(true);
                Button save = (Button) activity.findViewById(R.id.btn_download_switch);
                save.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        activity = am.waitForActivityWithTimeout(1000);
        getInstrumentation().removeMonitor(am);
        assertTrue(box.isChecked());
    }


}