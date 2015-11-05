package com.example.zhaorui.dvdcollector.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dingkai on 15/10/9.
 */
public class Friends extends ArrayList<String>{
    private Obs obs;

    public Friends(){obs = new Obs();}

    private class Obs extends Observable{
        public void notifying(){
            super.setChanged();
            super.notifyObservers();
        }
    }
    public void notifying(){obs.notifying();}
    public Observable getObs() {
        return obs;
    }
}
