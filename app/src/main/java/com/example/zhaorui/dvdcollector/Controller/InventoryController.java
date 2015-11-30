
/*
 *
 *University of Alberta CMPUT 301 Group: CMPUT301F15T11
 *Copyright {2015} {Dingkai Liang, Zhaorui Chen, Jiaxuan Yue, Xi Zhang, Qingdai Du, Wei Song}
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
*/
package com.example.zhaorui.dvdcollector.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.GalleryList;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Observer;
/**
 * <p>
 * The <code>InventoryController</code> is a controller of <code>Inventory</code>, which controls inventory information.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 01/11/15
 * @see java.util.ArrayList
 */
public class InventoryController {
    private static String TAG = "InventoryController";
    /**
     * Initialize a Inventory to store the inventory information.
     */
    private Inventory inventory;
    /**
     * Get the user's inventories and restore them.
     */
    public InventoryController(){
        inventory = User.instance().getInventory();
    }
    /**
     * This function is called when other function need to know all inventories.
     * @return inventory
     */
    public Inventory getInventory(){return inventory;}
    /**
     * This function is called when other function need to know inventories by a selected category.
     * @param category , a string variable.
     * @return the inventoris in the selected category.
     */
    public ArrayList<DVD> getInventory(String category){
        return inventory.getCategoryInventories().get(category);
    }
    /**
     * This function is called when other function need to add a new dvd into the inventory.
     */
    public void add(ArrayList<String> info){
        inventory.append(new DVD(info));
        GalleryListController glc = new GalleryListController();
        glc.addGallery(new Gallery());
    }
    /**
     * This function is called when other function need to edit a dvd from the inventory.
     */
    public void set(int index, ArrayList<String> info){
        inventory.edit(index, info);
    }
    /**
     * This function is called when other function need to remove a dvd from the inventory.
     * @param dvd ,a DVD variable
     */
    public void remove(DVD dvd){
        GalleryListController glc = new GalleryListController();
        glc.removeGallery(indexOf(dvd));
        inventory.delete(dvd);
    }
    /**
     * This function is called when other function need to get a dvd by index.
     * @param index ,a int variable
     * @return the inventory in the selected index
     */
    public DVD get(int index){ return inventory.get(index);}

    public DVD get(String name){
        for (DVD dvd:inventory){
            if(dvd.getName().equals(name)){
                return dvd;
            }
        }
        return null;
    }

    /**
     * This function is called when other function need to get a dvd by index.
     * @param dvd , a DVD variable
     * @return index, an int variable.
     */
    public int indexOf(DVD dvd){return inventory.indexOf(dvd);}

    /**
     * This function is called when other function need to get a dvd's index, input a dvd's name
     * @param name , the target dvd's name, a string variable.
     * @return index, an int variable.
     */
    public int indexOf(String name){
        for (DVD dvd : inventory){
            if (dvd.getName().equals(name)) {
                return inventory.indexOf(dvd);
            }
        }
        return -1;
    }
    /**
     * Add observer to make sure the inventory update on time.
     * @param o , an observer
     */
    public void addObserver(Observer o){
        ObserverManager.getInstance().addObserver("Inventory",o);
    }
    /**
     * To test if a dvd is in inventory.
     * @return a boolean.
     */
    public boolean find(String name){
        for (DVD dvd : inventory){
            if (dvd.getName().equals(name)) return true;
        }
        return false;
    }

    //newly add by TeppieC
    // get the names of all dvds in this inventory, only for the device user
    // see getAllNamesFriend in inventory for another version for the friends
    public String[] getAllNames(){
        Inventory inventorySharable = getSharableInventory();
        String[] strings = new String[inventorySharable.size()];
        int i = 0;
        for (DVD dvd : inventorySharable){
            strings[i] = dvd.getName();
            i+=1;
        }
        return strings;
    }

    // only for retrieving names of all dvds in the inventory of a non-device-user, for example a friend
    public String[] getAllNamesFriend(){
        String[] strings = new String[inventory.size()];
        int i = 0;
        for (DVD dvd : inventory){
            strings[i] = dvd.getName();
            i+=1;
        }
        return strings;
    }

    // newly add by TeppieC
    // get the inventory with all sharable items
    public Inventory getSharableInventory(){
        Inventory inventorySharable = new Inventory();

        for (DVD dvd : getInventory()){
            if (dvd.isSharable()){
                inventorySharable.add(dvd);
            }
        }
        return inventorySharable;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ArrayList<String> getInfo(int index){ return get(index).read();}

}
