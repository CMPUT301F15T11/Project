
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
*/package com.example.zhaorui.dvdcollector.Controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.View.NameInputDialog;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * The <code>DataManager</code> class is a class that manage all save and load information
 * this class provides functions to save information,
 * to a local file and also load information from the local file.
 * <p>
 *
 * @author  Dingkai Liang; Jiaxuan Yue
 * @version 03/11/15
 * @see com.google.gson.Gson
 */
public class DataManager implements Observer {
    private static final String FILENAME = "DVDCollector.Local";
    private static DataManager instance;
    private Uri imgUri;

    public static DataManager instance(){
        if (instance == null){
            instance = new DataManager();
        }
        return instance;
    }
    /**
     * General constructor
     */
    private DataManager(){}
    /**
     * This function loads information from file.
     */
    public void loadFromFile(Context context){
        try {//set the device user instance from the local file
            FileInputStream in = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Gson gson = new Gson();
            User.setInstance(gson.fromJson(reader, User.class));
            User.instance().getInventory().fresh();
            ObserverManager.getInstance().observeAll(this);
        } catch (FileNotFoundException e) {
            Log.e("DVD", "No local file found");
            Activity activity = (Activity) context;
            FragmentManager fm = activity.getFragmentManager();
            NameInputDialog newDialog = new NameInputDialog();
            newDialog.setCancelable(false);
            newDialog.show(fm, "abc");
        }
    }

    /**
     * Initialize an new user instance if the application is run for the first time
     * @param name , a string variable
     * @param email email in user profile
     */
    public void initFile(String name, String email){
        User.instance().getProfile().setName(name);
        User.instance().getProfile().setContact(email);
        ObserverManager.getInstance().observeAll(this);
        saveLocal();
        saveSever();
    }

    /**
     * This function adds observers to user's profile and user's inventory.
     * @see java.util.Observer;
     */
    private void saveLocal(){
        try {
            FileOutputStream out = ContextUtil.getInstance().openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            Gson gson = new Gson();
            gson.toJson(User.instance(), writer);
            writer.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Update date from webservice
     * @param ob observable variable
     * @param o object variable
     */
    public void update(Observable ob, Object o){
        saveLocal();
        saveSever();
        TradeListController tradeListController = new TradeListController(User.instance().getTradeList());
        tradeListController.updateTradeList();
    }

    /**
     * save serve
     */
    public void saveSever(){
        if (!ContextUtil.getInstance().isConnected()) {
            Log.e("Internet","Not connected");
            return;
        }
        MyHttpClient myHttpClient = new MyHttpClient(User.instance().getProfile().getName());
        myHttpClient.setUser(new Friend(User.instance()));
        myHttpClient.setGalleryList(User.instance().getGalleryList());
        myHttpClient.setTradeList(User.instance().getTradeList());
        myHttpClient.runPushFriend();
        myHttpClient.runPushGalleryList();
        myHttpClient.runPushTradeList();
    }

    /**
     * General constructor
     */
    public Uri getImgUri() {
        return imgUri;
    }

    /**
     * set image uri
     * @param imgUri Uri variable
     */
    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }
}
