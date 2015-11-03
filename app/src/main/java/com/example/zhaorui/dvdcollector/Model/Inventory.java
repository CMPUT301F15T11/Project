package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dingkai on 15/10/9.
 */
public class Inventory extends ArrayList<DVD>{
    private Obs obs = new Obs();

    private class Obs extends Observable{
        public void notifyObservers(Object data){
            super.setChanged();
            super.notifyObservers(data);
        }
    }

    public Observable getObs() {
        return obs;
    }

    public void notifyObservers(Object data){
        obs.notifyObservers(data);
    }
}
