package com.example.zhaorui.dvdcollector.Model;

import java.util.Observable;

/**
 * Created by zhaorui on 11/16/15.
 */
public class MyObservable extends Observable {
    public void notifying(){
        super.setChanged();
        super.notifyObservers();
    }
}
