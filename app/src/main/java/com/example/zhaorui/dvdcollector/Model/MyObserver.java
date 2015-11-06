package com.example.zhaorui.dvdcollector.Model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by teppie on 05/11/15.
 */
public interface MyObserver extends Observer{
    void update(Observable o, Object arg);
}
