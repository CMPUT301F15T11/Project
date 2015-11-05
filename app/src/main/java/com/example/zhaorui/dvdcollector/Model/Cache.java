package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dingkai on 15/11/4.
 */
public class Cache extends HashMap<String,Friend> {
    private static Cache instance;

    private Cache(){}

    public static Cache getInstance() {
        if (instance == null) instance = new Cache();
        return instance;
    }
}
