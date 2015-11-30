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
 * <p>
 * The <code>GalleryListHttpClient</code> is class to upload Gallery list to server.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 */
public class GalleryListHttpClient {
    /**
     * Initialize a new gson
     */
    private Gson gson = new Gson();
    /**
     * Initialize a string to store username
     */
    private String userName;
    /**
     * Initialize gallery list
     */
    private GalleryList galleryList;
    /**
     * Initialize a boolean value to store result
     */
    private Boolean result;
    /**
     * Assign username and gallery list
     * @param galleryList a string variable of user's gallery list
     * @param userName a string variable of username
     */
    public GalleryListHttpClient(GalleryList galleryList, String userName) {
        super();
        this.userName = userName;
        this.galleryList = galleryList;
    }
    /**
     * Gallery list HttpClient
     */
    public GalleryListHttpClient(String userName) {
        this.userName = userName;
    }

    /**
     * push gallery to the webservice
     */
    public void pushGallery() {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost addRequest = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301f15t11/gallerylist/" + this.userName);

            StringEntity stringEntity = new StringEntity(gson.toJson(galleryList));
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
     * Pull Gallery list from webservice
     * @param userName string variable of username
     * @return gallery list source from webservice
     */
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
    /**
     * Pull gallery
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
    /**
     * Pull gallery webservice
     * @return gallery result
     */
    public GalleryList runPull(){
        Thread thread = new PullThread();
        thread.start();
        while (result==null){
            //do nothing but wait for the pull thread to finish}
        }
        return galleryList;
    }
    /**
     * Push trade log to webservice
     */
    private class PushThread extends Thread {
        public PushThread() {
        }
        /**
         * Push trade log to webservice if it's first created
         */
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
    /**
     * Push username and gallery to webservice
     */
    public void runPush(){
        Thread thread = new PushThread();
        thread.start();
    }
}
