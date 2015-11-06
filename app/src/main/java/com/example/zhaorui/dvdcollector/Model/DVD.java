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
     * Initialize a string to store photo of the dvd.
     */

    private boolean hasPhoto;
    private Gallery gallery;

    /**
     * Initialize a boolean to store sharable of the dvd.
     */
    private boolean sharable;
    private final static String[] categories = {"Games","Romance","Documentary","Sci-Fi","Horror",
            "Action","Comedy","Edu","Story","Fantasy"};
    /**
     * General constructor.
     */
    public DVD(){}
    /**
     * This function is called when other function need to know the current dvd's category.
     * @return a string variable category.
     */
    public String getCategory() {
        return category;
    }
    /**
     * This function is called when other function need to set the current dvd's category.
     * @param category , a string variable.
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * This function is called when other function need to know the current dvd's name
     * @return a string variable name.
     */
    public String getName() {
        return name;
    }
    /**
     * This function is called when other function need to know the current dvd's quantity.
     * @return a string variable quantity.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * This function is called when other function need to know the current dvd's quantity.
     * @return a string variable quantity.
     */
    public String getQuantity() {
        return quantity;
    }
    /**
     * This function is called when other function need to set the current dvd's quantity.
     * @param quantity , a string variable.
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    /**
     * This function is called when other function need to know the current dvd's quality.
     * @return a string variable quality.
     */
    public String getQuality() {
        return quality;
    }
    /**
     * This function is called when other function need to set the current dvd's quality.
     * @param quality , a string variable.
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }
    /**
     * This function is called when other function need to display the user's comments about the current dvd.
     * @return a string variable comments.
     */
    public String getComments() {
        return comments;
    }
    /**
     * This function is called when other function need to set the user's comments about the current dvd.
     * @param  comments , a string variable.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * This function is called when other function need to know if the dvd is sharable or not.
     * @return True or False.
     */
    public boolean isSharable() {
        return sharable;
    }
    /**
     * This function is called when other function need to set if the dvd is sharable or not.
     * @param sharable, a boolean variable.
     */
    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }
    /**
     * This function is called when other function need to know the categories.
     * @return a string list categories.
     */
    public static String[] getCategories() {
        return categories;
    }
    /**
     * This function is called when other function need to display the photo of dvd.
     * @return a string photoStr.
     */
    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    @Override
    public String toString(){ return name;}
}
