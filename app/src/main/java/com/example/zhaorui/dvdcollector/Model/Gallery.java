/*
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

/**
 * <p>
 * The <code>Gallery</code> class manages photos that are used in this app.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 * @see ArrayList;
 */

public class Gallery {
    /**
     * Initialize a array list to store photos.
     */
    private ArrayList<String> photoStrs=null;

    /**
     * Get input photos.
     * @param photoStrs an array list variable contain photo strings.
     */
    public Gallery(ArrayList<String> photoStrs) {
        this.photoStrs = photoStrs;
    }

    /**
     * Initialize a gallery(set photoStrs to a new array list)
     */
    public Gallery() {
        this.photoStrs = new ArrayList<String>();
    }

    /**
     * This function is called when other functions need to use the photo string.
     * @return a array list of string.
     */
    public ArrayList<String> getPhotoStrs() {
        return photoStrs;
    }

    /**
     * This function is called when other functions need to set the photo string.
     * @param photoStrs an array list variable contain photo strings.
     */
    public void setPhotoStrs(ArrayList<String> photoStrs) {
        this.photoStrs = photoStrs;
    }

    /**
     * This function is called when other functions need to use the size of the array list.
     * @return a string value, the size of the array.
     */
    public int getSize() {
        if (this.photoStrs == null) {
            return 0;
        } else {
            return photoStrs.size();
        }
    }

    public void addPhotoStr(String photoStr){
        photoStrs.add(photoStr);
    }

    public void removePhotoStr(String photoStr){
        photoStrs.remove(photoStr);
    }
}
