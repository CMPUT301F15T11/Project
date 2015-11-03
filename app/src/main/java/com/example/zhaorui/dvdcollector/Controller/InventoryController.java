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

    public ArrayList<DVD> getInventory(String category){
        return inventory.getCategoryInventories().get(category);
    }

    public void add(DVD dvd){
        inventory.append(dvd);
    }

    public void set(DVD dvd, DVD dvd2){
        inventory.edit(dvd, dvd2);
    }

    public void remove(DVD dvd){
        inventory.delete(dvd);
    }

    public DVD get(int index){ return inventory.get(index);}

    public int indexOf(DVD dvd){return inventory.indexOf(dvd);}

    public int indexOf(String name){
        for (DVD dvd : inventory){
            if (dvd.getName().equals(name)) {
                return inventory.indexOf(dvd);
            }
        }
        return -1;
    }

    public void addObserver(Observer o){
        inventory.getObs().addObserver(o);
    }

    public boolean find(String name){
        for (DVD dvd : inventory){
            if (dvd.getName().equals(name)) return true;
        }
        return false;
    }
}
