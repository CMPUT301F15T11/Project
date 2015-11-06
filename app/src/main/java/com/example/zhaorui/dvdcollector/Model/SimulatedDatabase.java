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

import com.google.gson.Gson;

import java.util.HashMap;
/**
 * <p>
 * The <code>Cache</code> is a local database to store friend information.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 09/10/15
 */
public class SimulatedDatabase{
    private static HashMap<String,String> database;
    private static final String[] DVDList = { "Titanic", "Star war", "The Shawshank Redemption",
            "The Godfather", "The Dark Knight", "12 Angry Man", "Schindler's List", "Pulp Fiction",
            "The Lord of Rings", "Forrest Gump", "Inception", "The matrix"};
    private static final String[] nameList = { "Jack", "Lucy", "Calvin", "Frank", "Mike", "Wei",
            "Dingkai", "Dylan", "Zhaorui", "Jiaxuan", "Qingdai", "Xi", "Jonathan", "James", "John",
            "Bob", "Adam", "Liam", "Mars", "Andrew", "Anderson", "Leon"};

    private SimulatedDatabase(){}

    public static void init(){
        Inventory sampleInventory = new Inventory();
        DVD sampleDVD;
        for (String DVDName : DVDList){
            sampleDVD = new DVD();
            sampleDVD.setName(DVDName);
            sampleDVD.setQuantity("1");
            sampleDVD.setQuality("5 Stars");
            sampleDVD.setSharable(true);
            sampleDVD.setCategory("Action");
            sampleDVD.setComments("Great movie!");
            sampleDVD.setHasPhoto(false);
            sampleInventory.add(sampleDVD);
        }
        UserProfile sampleProfile = new UserProfile();
        sampleProfile.setCity("Edmonton");
        Friend sampleFriend;
        String jsonValue;
        Gson gson = new Gson();
        database = new HashMap<>();
        for (String name : nameList){
            sampleProfile.setName(name);
            sampleFriend = new Friend(sampleInventory,sampleProfile);
            jsonValue = gson.toJson(sampleFriend);
            database.put(name,jsonValue);
        }
    }

    public static boolean nameExist(String name){
        return database.containsKey(name);
    }

    public static String get(String name){return database.get(name);}
}
