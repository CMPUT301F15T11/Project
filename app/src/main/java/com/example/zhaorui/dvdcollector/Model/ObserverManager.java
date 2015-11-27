package com.example.zhaorui.dvdcollector.Model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by zhaorui on 11/20/15.
 */
public class ObserverManager{
    static private ObserverManager instance;
    final static private String[] keys = {"Inventory","Friends","Trades","Profile","Gallery"};
    private HashMap<Object,MyObservable> observables;
    private ObserverManager(){
        observables = new HashMap<>();
        for (String key : keys){
            observables.put(key,new MyObservable());
        }
    }

    public static ObserverManager getInstance() {
        if (instance == null){
            instance = new ObserverManager();
        }
        return instance;
    }

    public void addObserver(String key, Observer ob){
        observables.get(key).addObserver(ob);
    }

    public void notifying(String key){
        observables.get(key).notifying();
    }

    public void observeAll(Observer ob){
        for (String key : keys){
            observables.get(key).addObserver(ob);
        }
    }
}
