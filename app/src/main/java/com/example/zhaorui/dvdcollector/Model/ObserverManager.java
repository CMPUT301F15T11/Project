package com.example.zhaorui.dvdcollector.Model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
/**
 * <p>
 * The <code>ObserverManager</code> class manages all observers in the application.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 */
public class ObserverManager{
    /**
     * Initialize a static observermanager instance
     */
    static private ObserverManager instance;
    final static private String[] keys = {"Inventory","Friends","Trades","Profile","Gallery"};
    /**
     * Initialize hashmap observables
     */
    private HashMap<Object,MyObservable> observables;

    /**
     * Store log to observer
     */
    private ObserverManager(){
        observables = new HashMap<>();
        for (String key : keys){
            observables.put(key,new MyObservable());
        }
    }
    /**
     * Get instance value from observer
     * @return instance
     */
    public static ObserverManager getInstance() {
        if (instance == null){
            instance = new ObserverManager();
        }
        return instance;
    }
    /**
     * Add keys to observer
     * @param key string variable of key
     * @param ob observer
     */
    public void addObserver(String key, Observer ob){
        observables.get(key).addObserver(ob);
    }
    /**
     * Notify observer
     * @param key string variable
     */
    public void notifying(String key){
        observables.get(key).notifying();
    }
    /**
     * Get all keys in observer
     * @param ob observer variable
     */
    public void observeAll(Observer ob){
        for (String key : keys){
            observables.get(key).addObserver(ob);
        }
    }
}
