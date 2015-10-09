package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/10/9.
 */
public class Internet {
    private boolean connecting;
    private static Internet instance;

    private Internet() {
    }

    public static Internet getInstance() {
        if (instance==null) instance = new Internet();
        return instance;
    }

    public boolean isConnecting() {
        return connecting;
    }

    public void disconnect(){}

    public void connect(){}

    public void downloadData(){}
}
