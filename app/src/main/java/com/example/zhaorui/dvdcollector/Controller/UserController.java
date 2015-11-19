package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.es.data.SearchHit;
import com.example.zhaorui.dvdcollector.es.data.SearchResponse;
import com.example.zhaorui.dvdcollector.es.data.SimpleSearchCommand;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
import java.util.ArrayList;

/**
 * Created by zhaorui on 11/18/15.
 */
public class UserController {
    private Gson gson = new Gson();
    private User user;
    private static final String TAG = "UserController";
    private String FILENAME;


    public UserController(User user) {
        super();
        this.user = user;
        FILENAME = user.getProfile().getName() + ".local";
    }

    public UserController() {
    }

    public void setUser(User user) {
        this.user = user;
        this.FILENAME = user.getProfile().getName() + ".local";
    }

    /**
     * Adds a new movie
     */
    public void pushUser() {
        HttpClient httpClient = new DefaultHttpClient();
        Log.e("DVD"+TAG, "Pushing to "+user.getResourceUrl() + user.getProfile().getName());
    ///catch exception if not connected to the internet
        //////////////////////////////////////////////////////////
        try {
            HttpPost addRequest = new HttpPost(user.getResourceUrl() + user.getProfile().getName());

            StringEntity stringEntity = new StringEntity(gson.toJson(user));
            addRequest.setEntity(stringEntity);
            addRequest.setHeader("Accept", "application/json");

            HttpResponse response = httpClient.execute(addRequest);
            String status = response.getStatusLine().toString();
            Log.i(TAG, status);
            Log.e("DVD"+TAG, status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public User searchUser(String searchString, String field) {
        User result = new User();


        HttpPost searchRequest = new HttpPost(user.getSearchUrl());

        String[] fields = null;
        if (field != null) {
            throw new UnsupportedOperationException("Not implemented!");
        }

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);
        Log.i(TAG, "Json command: " + query);

        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(query);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        searchRequest.setHeader("Accept", "application/json");
        searchRequest.setEntity(stringEntity);

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = httpClient.execute(searchRequest);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Type searchResponseType = new TypeToken<SearchResponse<User>>() {
        }.getType();

        SearchResponse<User> esResponse;

        try {
            esResponse = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    searchResponseType);
        } catch (JsonIOException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (SearchHit<User> hit: esResponse.getHits().getHits()){
            movies.add(hit.getSource());
        }

        movies.notifyObservers();

        return movies;
    }
*/
    // when Controller is not specified at the first
    // use this function to pull the user from the webservice
    // and then call setUser function to set the user
    public User pullUser(String userName) {
        SearchHit<User> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(user.getResourceUrl() + userName);

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

    public void saveUserLocal(){
        try {
            FileOutputStream out = ContextUtil.getInstance().openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            Gson gson = new Gson();
            gson.toJson(user, writer);
            writer.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
