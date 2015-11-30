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

import android.net.Uri;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * The <code>DVD</code> class manages information for DVD
 * it provide function to set information for a DVD and get information
 * from a DVD.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 02/11/15
 * */
public class DVD {
    public String getQuantity() {
        return quantity;
    }

    public String getQuality() {
        return quality;
    }

    public String getComments() {
        return comments;
    }

    public static String[] getCategoriesName() {
        return categoriesName;
    }

    /**
     * Initialize a string to store category of the dvd.
     */
    private String category;
    /**
     * Initialize a string to store name of the dvd.
     */
    private String name;
    /**
     * Initialize a string to store quantity of the dvd.
     */
    private String quantity;
    /**
     * Initialize a string to store quality of the dvd.
     */
    private String quality;
    /**
     * Initialize a string to store comments of the dvd.
     */
    private String comments;
    /**
     * Initialize a boolean to store sharable of the dvd.
     */
    private boolean sharable;
    private final static String[] categoriesName = {"Games","Romance","Documentary","Sci-Fi","Horror",
            "Action","Comedy","Edu","Story","Fantasy"};

    private static ArrayList<String> categories;
    /**
     * General constructor.
     */
    public DVD(ArrayList<String> info){
        set(info);
    }
    /**
     * This function is called when other function need to know the current dvd's category.
     */
    public void set(ArrayList<String> info){
        category = info.get(0);
        name = info.get(1);
        quantity = info.get(2);
        quality = info.get(3);
        sharable = info.get(4).equals("Yes");
        comments = info.get(5);
    }

    /**
     * This function is called when need to know the current dvd's category.
     * @return a string variable category
     */
    public ArrayList<String> read(){
        ArrayList<String> info = new ArrayList<>();
        info.add(category);
        info.add(name);
        info.add(quantity);
        info.add(quality);
        if (sharable) info.add("Yes");
        else info.add("No");
        info.add(comments);
        return info;
    }
    /**
     * get dvd's category.
     * @return a string variable category.
     */
    public String getCategory() {
        return category;
    }
    /**
     * This function is called when other function need to get the current dvd's category.
     * @return a string variable category.
     */
    public static ArrayList<String> getCategories() {
        if (categories == null){
            categories = new ArrayList<>();
            for (String category : categoriesName){
                categories.add(category);
            }
        }
        return categories;
    }
    /**
     * This function is called when other function need to know the current dvd's name
     * @return a string variable name.
     */
    public String getName() {
        return name;
    }
    /**
     * This function is called when other function need to know if the dvd is sharable or not.
     * @return True or False.
     */
    public boolean isSharable() {
        return sharable;
    }

    /**
     * to get name of dvd
     * @return name
     */
    @Override
    public String toString(){ return name;}
    /**
     * This function is called when other function need to set if the dvd is sharable or not.
     * @param sharable a boolean variable.
     */
    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }
}
