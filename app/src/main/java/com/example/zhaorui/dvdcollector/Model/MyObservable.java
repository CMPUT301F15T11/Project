package com.example.zhaorui.dvdcollector.Model;

import java.util.Observable;


/**
 * <p>
 * The <code>MyObservable</code> class is observer to be able to update list after user's modification.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 */
public class MyObservable extends Observable {
    public void notifying(){
        super.setChanged();
        super.notifyObservers();
    }
}
