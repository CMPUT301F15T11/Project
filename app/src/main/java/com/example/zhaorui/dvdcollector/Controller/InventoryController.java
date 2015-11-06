
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

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.Model.User;

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
     * @param dvd
     */
    public void add(DVD dvd){
        inventory.append(dvd);
    }
    /**
     * This function is called when other function need to edit a dvd from the inventory.
     * @param dvd ,a DVD variable
     * @param dvd2 ,a DVD variable
     */
    public void set(DVD dvd, DVD dvd2){
        inventory.edit(dvd, dvd2);
    }
    /**
     * This function is called when other function need to remove a dvd from the inventory.
     * @param dvd ,a DVD variable
     */
    public void remove(DVD dvd){
        inventory.delete(dvd);
    }
    /**
     * This function is called when other function need to get a dvd by index.
     * @param index ,a int variable
     * @return the inventory in the selected index
     */
    public DVD get(int index){ return inventory.get(index);}
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
        inventory.getObs().addObserver(o);
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

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
