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
 * @see java.util.ArrayList;
 */

public class Gallery {
    /**
     * Initialize a array list to store photos.
     */
    private ArrayList<String> gallery;

    public Gallery(){
        gallery = new ArrayList<>();
    }

    public Gallery(int size){
        gallery = new ArrayList<>();
        for (int i = 0; i < size; ++i){
            gallery.add("No Photo");
        }
    }
    public void add(String photoStr){
        gallery.add(photoStr);
        ObserverManager.getInstance().notifying("Gallery");
    }

    public void remove(int index){
        gallery.remove(index);
        ObserverManager.getInstance().notifying("Gallery");
    }

    public String get(int index){
        return gallery.get(index);
    }

    public void set(int index, String imageStr){
        gallery.set(index,imageStr);
    }
}
