package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.Model.User;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by dingkai on 15/11/1.
 */
public class InventoryController {
    private Inventory inventory;

    public InventoryController(){
        inventory = User.instance().getInventory();
    }

    public Inventory getInventory(){return inventory;}

    public Inventory getInventory(String category){
        Inventory categoryInventory = new Inventory();
        for (DVD dvd : inventory){
            if (dvd.getCategory().toLowerCase().equals(category.toLowerCase())){
                categoryInventory.add(dvd);
            }
        }
        inventory = categoryInventory;
        return inventory;
    }

    public void add(DVD dvd){
        inventory.add(dvd);
        notifyObservers(0);
    }

    public void set(int index, DVD dvd){
        inventory.set(index, dvd);
        notifyObservers(0);
    }

    public void remove(int index){
        DVD dvd = inventory.get(index);
        inventory.remove(index);
        inventory.notifyObservers(dvd);
    }

    public DVD get(int index){ return inventory.get(index);}

    public int indexOf(DVD dvd) {return inventory.indexOf(dvd);}

    public void notifyObservers(Object data){
        inventory.notifyObservers(data);
    }

    public void addObserver(Observer o){
        inventory.getObs().addObserver(o);
    }
}
