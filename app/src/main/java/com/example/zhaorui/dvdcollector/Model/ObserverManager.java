package com.example.zhaorui.dvdcollector.Model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by zhaorui on 11/20/15.
 */
public class ObserverManager{
    static private ObserverManager instance;
    private HashMap<Object,MyObservable> observables;
    private ObserverManager(){
        observables = new HashMap<>();
    }

    public static ObserverManager getInstance() {
        if (instance == null){
            instance = new ObserverManager();
        }
        return instance;
    }

    public void addObserver(Object o, Observer ob){
        if (observables.get(o) == null) setObservable(o);
        observables.get(o).addObserver(ob);
    }

    public void notifying(Object o){
        if (observables.get(o) == null) setObservable(o);
        observables.get(o).notifying();
    }

    private void setObservable(Object o){
        observables.put(o,new MyObservable());
    }
}
