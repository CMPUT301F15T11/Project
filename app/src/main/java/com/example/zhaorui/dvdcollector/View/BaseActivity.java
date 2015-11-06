package com.example.zhaorui.dvdcollector.View;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.zhaorui.dvdcollector.R;

/**
 * Created by teppie on 06/10/15.
 */
public class BaseActivity extends Activity {//////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);//////////////////
        super.onCreate(savedInstanceState);
    }

}