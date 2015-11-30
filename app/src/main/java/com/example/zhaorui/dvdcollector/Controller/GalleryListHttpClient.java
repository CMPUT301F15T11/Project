package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.GalleryList;
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
public class GalleryListHttpClient {
    private Gson gson = new Gson();
    private String userName;
    private GalleryList galleryList;
    private Boolean result;

    public GalleryListHttpClient(GalleryList galleryList, String userName) {
        super();
        this.userName = userName;
        this.galleryList = galleryList;
    }

    public GalleryListHttpClient() {
    }

    public GalleryListHttpClient(String userName) {
        this.userName = userName;
    }

    public void setGalleryList(GalleryList galleryList) {
        this.galleryList = galleryList;
    }

    public void pushGallery() {
        HttpClient httpClient = new DefaultHttpClient();
        ///catch exception if not connected to the internet
        //////////////////////////////////////////////////////////
        try {
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t11/gallerylist/" + this.userName);

            StringEntity stringEntity = new StringEntity(gson.toJson(galleryList));
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

    public GalleryList pullGallery(String userName) {
        SearchHit<GalleryList> sr = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301f15t11/gallerylist/" + userName);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
        } catch (ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        Type searchHitType = new TypeToken<SearchHit<GalleryList>>() {}.getType();

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
            galleryList = pullGallery(userName);
            result = true;
            // Give some time to get updated info
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public GalleryList runPull(){
        Thread thread = new PullThread();
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        return galleryList;
    }

    private class PushThread extends Thread {
        public PushThread() {
        }

        @Override
        public void run() {

            // push user's tradelist online if it's first created
            pushGallery();

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
