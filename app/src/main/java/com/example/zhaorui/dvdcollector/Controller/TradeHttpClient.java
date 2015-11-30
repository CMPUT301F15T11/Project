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
    private Gson gson = new Gson();
    private String userName;
    private TradeList tradeList;
    private Boolean result;

    public TradeHttpClient(TradeList tradeList, String userName) {
        super();
        this.userName = userName;
        this.tradeList = tradeList;
    }

    public TradeHttpClient(String userName) {
        this.userName = userName;
    }

    private void pushTradeList() {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t11/trade/" + this.userName);

            StringEntity stringEntity = new StringEntity(gson.toJson(tradeList));
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

    private class PullThread extends Thread {
        public PullThread() {
            result = null;
        }

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

    public TradeList runPull(){
        Thread thread = new PullThread();
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        return tradeList;
    }

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

    public void runPush(){
        Thread thread = new PushThread();
        thread.start();
    }
}
