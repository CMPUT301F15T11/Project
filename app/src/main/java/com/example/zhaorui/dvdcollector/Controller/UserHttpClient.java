package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.es.data.SearchHit;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by zhaorui on 11/19/15.
 */
public class UserHttpClient {
    private Gson gson = new Gson();
    private Friend friend;
    private String FILENAME;

    public UserHttpClient(Friend friend) {
        super();
        this.friend = friend;
        FILENAME = friend.getProfile().getName() + ".local";
    }

    public UserHttpClient() {
    }

    public void setUser(Friend friend) {
        this.friend = friend;
        this.FILENAME = friend.getProfile().getName() + ".local";
    }


    public void pushFriend() {
        HttpClient httpClient = new DefaultHttpClient();
        ///catch exception if not connected to the internet
        //////////////////////////////////////////////////////////
        try {
            HttpPost addRequest = new HttpPost(friend.getResourceUrl() + friend.getProfile().getName());

            StringEntity stringEntity = new StringEntity(gson.toJson(friend));
            Log.e("dvd","start pushing");
            Log.e("DVD Friend",friend.getResourceUrl() + friend.getProfile().getName());
            Log.e("DVD Friend Controller", gson.toJson(friend));
            addRequest.setHeader("Accept", "application/json");

            addRequest.setEntity(stringEntity);
            HttpResponse response = null;
            try {
                Log.e("dvd","connecting to the webservice...");
                response = httpClient.execute(addRequest);
                Log.e("dvd","Done executing!!");
            } catch (ClientProtocolException e) {
                Log.e("dvd","Failed connecting!");
                throw new RuntimeException(e.getMessage());
            }
            Log.e("dvd","Now finished");
            String status = response.getStatusLine().toString();
            Log.e("dvd","end pushing");
            Log.e("DVD Friend Controller", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // when Controller is not specified at the first
    // use this function to pull the user from the webservice
    // and then call setUser function to set the user
    public User pullFriend(String userName) {
        SearchHit<User> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(friend.getResourceUrl() + userName);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<User>>() {}.getType();

        try {
            sr = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchHitType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sr.getSource();
    }

    public void saveFriendLocal(){
        try {
            FileOutputStream out = ContextUtil.getInstance().openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            Gson gson = new Gson();
            gson.toJson(friend, writer);
            writer.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
