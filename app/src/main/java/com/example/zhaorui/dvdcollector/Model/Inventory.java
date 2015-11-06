package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dingkai on 15/10/9.
 */
public class Inventory extends ArrayList<DVD>{
    private Obs obs;

    private Map<String,ArrayList<DVD>> categoryInventories;

    public Map<String, ArrayList<DVD>> getCategoryInventories() {
        return categoryInventories;
    }

    public Inventory(){
        obs = new Obs();
        categoryInventories = new HashMap<>();
        String[] categories = DVD.getCategories();
        for( String category : categories){
            categoryInventories.put(category,new ArrayList<DVD>());
        }
    }

    public void append(DVD dvd){
        add(dvd);
        categoryInventories.get(dvd.getCategory()).add(dvd);
        obs.notifying();
    }

    public void delete(DVD dvd){
        remove(dvd);
        categoryInventories.get(dvd.getCategory()).remove(dvd);
        obs.notifying();
    }

    public void edit(DVD dvd,DVD dvd2){
        categoryInventories.get(dvd.getCategory()).remove(dvd);
        set(indexOf(dvd), dvd2);
        categoryInventories.get(dvd2.getCategory()).add(dvd2);
        obs.notifying();
    }

    private class Obs extends Observable{
        public void notifying(){
            super.setChanged();
            super.notifyObservers();
        }
    }

    public Observable getObs() {
        return obs;
    }
}
