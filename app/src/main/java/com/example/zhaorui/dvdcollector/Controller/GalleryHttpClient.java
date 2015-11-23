package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.TradeList;
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by zhaorui on 11/19/15.
 */
public class GalleryHttpClient {
    private Gson gson = new Gson();
    private String userName;
    private Gallery gallery;
    private Boolean result;

    public GalleryHttpClient(Gallery gallery, String userName) {
        super();
        this.userName = userName;
        this.gallery = gallery;
    }

    public GalleryHttpClient() {
    }

    public GalleryHttpClient(String userName) {
        this.userName = userName;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public void pushTradeList() {
        HttpClient httpClient = new DefaultHttpClient();
        ///catch exception if not connected to the internet
        //////////////////////////////////////////////////////////
        try {
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t11/photolist/" + this.userName);

            StringEntity stringEntity = new StringEntity(gson.toJson(gallery));
            Log.e("dvd","start pushing");
            Log.e("DVD TradeList", "http://cmput301.softwareprocess.es:8080/cmput301f15t11/photollist/" + userName);
            Log.e("DVD", gson.toJson(gallery));
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
            Log.e("DVD", status);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Gallery pullGallery(String userName) {
        SearchHit<Gallery> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t11/photolist/" + userName);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<Gallery>>() {}.getType();

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
        }

        @Override
        public void run() {
            // push user's tradelist online if it's first created
            gallery = pullGallery(userName);
            result = true;
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Gallery runPull(){
        Thread thread = new PullThread();
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        return gallery;
    }

    private class RetrieveThread extends Thread {
        private String userName;

        public RetrieveThread(String userName) {
            this.userName = userName;
        }

        @Override
        public void run() {
            // push user's tradelist online if it's first created
            gallery = pullGallery(userName);
            result = true;
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Gallery runRetrieve(String userName){
        Thread thread = new RetrieveThread(userName);
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        return gallery;
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
