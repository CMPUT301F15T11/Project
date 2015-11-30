package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by zhaorui on 11/19/15.
 */
public class TradeHttpClient {
    /**
     * Initialize a new gson
     */
    private Gson gson = new Gson();
    /**
     * Initialize a string to store username
     */
    private String userName;
    /**
     * Initialize tradeList
     */
    private TradeList tradeList;
    /**
     * Initialize a boolean value to store result
     */
    private Boolean result;
    /**
     * Assign username and trade list
     * @param tradeList string variable of trade list
     * @param userName string variable of username
     */
    public TradeHttpClient(TradeList tradeList, String userName) {
        super();
        this.userName = userName;
        this.tradeList = tradeList;
    }

    /**
     * Trade HttpClient
     * @param userName a string variable
     */
    public TradeHttpClient(String userName) {
        this.userName = userName;
    }
    /**
     * Push trade list to webservice
     */
    private void pushTradeList() {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t11/trade/" + this.userName);

            StringEntity stringEntity = new StringEntity(gson.toJson(tradeList));
            Log.e("push trade list to web",this.userName+"     " +gson.toJson(tradeList));
            addRequest.setHeader("Accept", "application/json");

            addRequest.setEntity(stringEntity);
            HttpResponse response = null;
            try {
                response = httpClient.execute(addRequest);
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e.getMessage());
            }
            String status = response.getStatusLine().toString();
            Log.e("DVD", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Pull trade list from webservice
     * @param userName string list of username
     * @return trade list from webservice
     */
    private TradeList pullTradeList(String userName) {
        SearchHit<TradeList> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t11/trade/" + userName);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<TradeList>>() {}.getType();

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
    /**
     * Pull trade list from webservice
     */
    private class PullThread extends Thread {
        public PullThread() {
            result = null;
        }
        /**
         * Push user's trade list to webservice if it's first created
         */
        @Override
        public void run() {
            // push user's tradelist online if it's first created
            tradeList = pullTradeList(userName);
            result = true;
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Execute to pull trade list online
     * @return result trade list
     */
    public TradeList runPull(){
        Thread thread = new PullThread();
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        result = null;
        Log.e("Pull from web","PULLLLLLLLLLLLLLLLLLLLLLL"+this.userName);
        return tradeList;
    }

    /**
     * Retrieve trade list and username
     */
    private class PushThread extends Thread {
        public PushThread() {
        }

        @Override
        public void run() {

            // push user's tradelist online if it's first created
            pushTradeList();

            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Push trade list to webservice
     */
    public void runPush(){
        Thread thread = new PushThread();
        thread.start();
    }
}
