package com.example.zhaorui.dvdcollector.Model;

import java.util.Observable;

/**
 * <p>
 * The <code>MyObservable</code> class observes
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/16/15
 * @see java.util.Observable;
 */
public class MyObservable extends Observable {
    public void notifying(){
        super.setChanged();
        super.notifyObservers();
    }
}
