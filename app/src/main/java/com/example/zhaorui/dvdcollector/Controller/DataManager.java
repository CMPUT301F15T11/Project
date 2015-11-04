package com.example.zhaorui.dvdcollector.Controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import com.example.zhaorui.dvdcollector.Model.ContextUtil;
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
 * Created by dingkai on 15/11/3.
 */
public class DataManager implements Observer{
    private static final String FILENAME = "DVDCollector.Local";
    private static DataManager instance;

    public static DataManager instance(){
        if (instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager(){}

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
    }

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
        saveLocal();
    }
}
