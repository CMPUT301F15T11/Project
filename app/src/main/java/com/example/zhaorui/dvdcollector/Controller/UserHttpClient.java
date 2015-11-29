package com.example.zhaorui.dvdcollector.Controller;

import android.content.Context;
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.es.data.SearchHit;
import com.example.zhaorui.dvdcollector.es.data.SearchResponse;
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


import com.example.zhaorui.dvdcollector.es.data.Hits;
import com.example.zhaorui.dvdcollector.es.data.SearchResponse.*;
import com.example.zhaorui.dvdcollector.es.data.SearchHit;
import com.example.zhaorui.dvdcollector.es.data.SimpleSearchCommand;
import com.example.zhaorui.dvdcollector.es.data.SimpleSearchCommand;

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
    private String name;
    private Boolean result;

    public UserHttpClient(Friend friend) {
        super();
        this.friend = friend;
    }

    public UserHttpClient() {
    }

    public UserHttpClient(String name) {
        this.name = name;
    }

    public void setUser(Friend friend) {
        this.friend = friend;
    }

    public void pushFriend() {
        HttpClient httpClient = new DefaultHttpClient();
        ///catch exception if not connected to the internet
        //////////////////////////////////////////////////////////
        try {
            HttpPost addRequest = new HttpPost(Friend.URL + friend.getProfile().getName());

            StringEntity stringEntity = new StringEntity(gson.toJson(friend));
            Log.e("DVD Friend",Friend.URL + friend.getProfile().getName());
            Log.e("DVD Friend Controller", gson.toJson(friend));

            addRequest.setEntity(stringEntity);
            HttpResponse response = null;
            try {
                response = httpClient.execute(addRequest);
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e.getMessage());
            }
            String status = response.getStatusLine().toString();
            Log.e("DVD Friend Controller", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // when Controller is not specified at the first
    // use this function to pull the user from the webservice
    // and then call setUser function to set the user
    public Friend pullFriend(String userName) {
        SearchHit<Friend> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Friend.URL + userName);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<Friend>>() {}.getType();

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

    public Boolean searchFriend(String searchString, String field) {
        Boolean resultSearch = false;

        /**
         * Creates a search request from a search string and a field
         */

        HttpPost searchRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t11/friend/_search");

        String[] fields = {field};

        SimpleSearchCommand command = new SimpleSearchCommand(searchString);

        String query = gson.toJson(command);

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

        /**
         * Parses the response of a search
         */
        Type searchResponseType = new TypeToken<SearchResponse<Friend>>() {
        }.getType();

        SearchResponse<Friend> esResponse;

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

        for (SearchHit<Friend> hit : esResponse.getHits().getHits()) {
            if (hit.getSource()!=null){
                Friend friend = hit.getSource();
                if(friend.getProfile().getName().equals(searchString)) {
                    resultSearch = true;
                    return resultSearch;
                }
            }else {resultSearch = false;}
        }
        return resultSearch;
    }

    private class PullThread extends Thread {
        public PullThread() {
            result = null;
        }

        @Override
        public void run() {
            // push user's tradelist online if it's first created
            friend = pullFriend(name);
            result = true;
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Friend runPull(){
        Thread thread = new PullThread();
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        return friend;
    }

    private class PushThread extends Thread {
        public PushThread() {
        }

        @Override
        public void run() {

            // push user's tradelist online if it's first created
            pushFriend();

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void runPush(){
        Thread thread = new PushThread();
        thread.start();
    }

}
