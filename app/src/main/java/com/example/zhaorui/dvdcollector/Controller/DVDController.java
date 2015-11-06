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
import android.util.Base64;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Gallery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * <p>
 * The <code>DVDController</code> controls information collected for  <code>DVD</code>
 * by providing function to create new DVD, load information from existing DVD.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 02/11/15
 * @see java.util.ArrayList
 */
public class DVDController {
    /**
     * This function creates a new DVD by inputting an info array
     * which contains all information about the DVD
     * @return DVD with all informations.
     */
    public DVD create(ArrayList<String> info,boolean sharable, Gallery gallery){
        DVD dvd = new DVD();
        dvd.setCategory(info.get(0));
        dvd.setName(info.get(1));
        dvd.setQuantity(info.get(2));
        dvd.setQuality(info.get(3));
        dvd.setHasPhoto(info.get(4)=="Yes");
        dvd.setComments(info.get(5));
        dvd.setSharable(sharable);
        dvd.setGallery(gallery);
        return dvd;
    }
    /**
     * This function gets all information form a existing DVD and
     * save information to am array list info.
     * @return array list info with all information about the input DVD.
     */
    public ArrayList<String> read(DVD dvd){
        ArrayList<String> info = new ArrayList<String>();
        info.add(dvd.getCategory());
        info.add(dvd.getName());
        info.add(dvd.getQuantity());
        info.add(dvd.getQuality());
        if (dvd.isHasPhoto()){
            info.add("Yes");
        }else{
            info.add("No");
        }
        if (dvd.isSharable()){
            info.add("Yes");
        } else{
            info.add("No");
        }
        info.add(dvd.getComments());
        return info;
    }
    /**
     * This function records categories from DVD and store to an array list.
     * @return array list of different categories.
     */
    public Gallery readPhoto(DVD dvd){
        return dvd.getGallery();
    }

    public void changeGallery(DVD dvd, Gallery gallery){
        dvd.setGallery(gallery);
        if (gallery.getSize()!=0){
            dvd.setHasPhoto(true);
        }
    }

    public ArrayList<String> categories(){
        ArrayList<String> categories = new ArrayList<String>();
        for (String category : DVD.getCategories()){
            categories.add(category);
        }
        return categories;
    }

}
