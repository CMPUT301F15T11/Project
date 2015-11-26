package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.Observer;

/**
 * <p>
 * The <code>Temp</code> class is a temperate  that allowed other functions to use without read and load to local file.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/16/15
 */
public class Temp {
    /**
     * Initialize a static temp variable
     */
    static public Temp instance;
    /**
     * Initialize an arraylist Observer
     */
    public ArrayList<Observer> backUpObservers;

    /**
     * Initialize a temp
     */
    private Temp(){

    }

    /**
     * Get value from instance
     * @return instance
     */
    public static Temp getInstance() {
        if (instance == null){
            instance = new Temp();
        }
        return instance;
    }
}
