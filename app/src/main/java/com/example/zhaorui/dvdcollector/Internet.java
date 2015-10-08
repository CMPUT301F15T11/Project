package com.example.zhaorui.dvdcollector;

/**
 * Created by zhaorui on 10/7/15.
 */
public class Internet {
    private boolean connectability = Boolean.FALSE;

    public boolean isConnected(){
        return this.connectability;
    }

    public void pushChangeOnline(){}

    public void pushTradeOnline(){}

    public void cacheOffline(){}
}