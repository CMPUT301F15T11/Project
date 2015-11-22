package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by zhaorui on 11/16/15.
 */
public class Temp {
    static public Temp instance;
    public ArrayList<Observer> backUpObservers;
    private Temp(){

    }

    public static Temp getInstance() {
        if (instance == null){
            instance = new Temp();
        }
        return instance;
    }
}
