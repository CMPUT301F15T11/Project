package com.example.zhaorui.dvdcollector;

import java.util.List;

/**
 * Created by zhaorui on 10/7/15.
 */
public class Inventory {
    private List<DVD> mInventory;

    public Inventory() {
    }

    public void addDVD(DVD mDVD){
        this.mInventory.add(mDVD);
    }

    public void removeDVD(DVD mDVD){
        try {
            this.mInventory.remove(mDVD);
        }catch (RuntimeException e){
            //if no such DVD in this inventory
        }
    }

    //To be implemented...
}
