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
package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * The <code>Inventory</code> manages the inventory list for the current user
 * it provide functions to add, delete and edit dvds.
 * It also provide observers to make sure the inventory list is update on time in user's interface.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 9/10/15
 * @see java.util.ArrayList
 * @see java.util.HashMap
 * @see java.util.Map
 * @see java.util.Observable
 */
public class Inventory extends ArrayList<DVD>{
    /**
     * initialize categoryInventories,m a hash map which maps categories and dvd together,
     * @see java.util.HashMap
     */
    private Map<String,ArrayList<DVD>> categoryInventories;
    /**
     * get categoryInventories.
     * @return a hash map contains categories and inventories.
     * @see java.util.HashMap
     */
    public Map<String, ArrayList<DVD>> getCategoryInventories() {
        return categoryInventories;
    }

    /**
     * Create a new list of inventories, map dvds and categories together.
     */
    public Inventory(){
        categoryInventories = new HashMap<>();
        ArrayList<String> categories = DVD.getCategories();
        for( String category : categories){
            categoryInventories.put(category, new ArrayList<DVD>());
        }
    }
    /**
     * Append a new DVD to the hash map
     * Notify the Observable
     * @param dvd a DVD variable
     */

    public void append(DVD dvd){
        add(dvd);
        categoryInventories.get(dvd.getCategory()).add(dvd);
        ObserverManager.getInstance().notifying("Inventory");
    }
    /**
     * Delete a new DVD to the hash map
     * Notify the Observable
     * @param dvd a DVD variable
     */
    public void delete(DVD dvd){
        remove(dvd);
        categoryInventories.get(dvd.getCategory()).remove(dvd);
        ObserverManager.getInstance().notifying("Inventory");
    }
    /**
     * To edit DVDs to the hash map
     * @param index a index variable
     * @param info a string variable
     */
    public void edit(int index, ArrayList<String> info){
        DVD dvd = get(index);
        categoryInventories.get(dvd.getCategory()).remove(dvd);
        dvd.set(info);
        categoryInventories.get(dvd.getCategory()).add(dvd);
        ObserverManager.getInstance().notifying("Inventory");
    }
    /**
     * Update information about this DVD
     */
    public void fresh(){
        for (DVD dvd : this){
            categoryInventories.get(dvd.getCategory()).add(dvd);
        }
    }

    /**
     * get a dvd by name
     * @param name a string variable
     * @return dvd or null
     */
    public DVD getByName(String name){
        for (DVD dvd:this){
            if (dvd.getName().equals(name)){
                return dvd;
            }
        }
        return null;
    }
}
