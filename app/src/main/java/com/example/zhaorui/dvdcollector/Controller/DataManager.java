
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
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.MyObserver;
import com.example.zhaorui.dvdcollector.Model.SimulatedDatabase;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.View.NameInputDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * The <code>DataManager</code> class is a class that manage all save and load information
 * this class provides functions to save information,
 * to a local file and also load information from the local file.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 03/11/15
 * @see com.google.gson.Gson
 */
public class DataManager implements MyObserver{
    private static final String FILENAME = "DVDCollector.Local";
    private static DataManager instance;

    public static DataManager instance(){
        if (instance == null){
            instance = new DataManager();
            SimulatedDatabase.init();
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
        try {
            FileInputStream in = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Gson gson = new Gson();
            Type userType = new TypeToken<User>(){}.getType();
            User.setInstance((User) gson.fromJson(reader, userType));
            observing();
        } catch (FileNotFoundException e) {
            Activity activity = (Activity) context;
            FragmentManager fm = activity.getFragmentManager();
            NameInputDialog newDialog = new NameInputDialog();
            newDialog.setCancelable(false);
            newDialog.show(fm, "abc");
        }
    }

    /**
     * Initialize an output file
     * @param name , a string variable
     */
    public void initFile(String name){
        User.instance().getProfile().setName(name);
        saveLocal();
        observing();
    }

    private void observing(){
        User.instance().getProfile().deleteObservers();
        User.instance().getProfile().addObserver(this);
        User.instance().getInventory().getObs().deleteObservers();
        User.instance().getInventory().getObs().addObserver(this);
        User.instance().getFriends().getObs().deleteObservers();
        User.instance().getFriends().getObs().addObserver(this);
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

    public void update(Observable ob, Object o){
        System.out.println("111");
        saveLocal();
    }
}
