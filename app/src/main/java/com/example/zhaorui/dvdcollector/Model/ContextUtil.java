package com.example.zhaorui.dvdcollector.Model;

import android.app.Application;

/**
 * Created by dingkai on 15/11/3.
 */
public class ContextUtil extends Application{
    private static ContextUtil instance;

    public static ContextUtil getInstance() {
        return instance;
    }

    public void onCreate(){
        super.onCreate();
        instance = this;
    }
}
