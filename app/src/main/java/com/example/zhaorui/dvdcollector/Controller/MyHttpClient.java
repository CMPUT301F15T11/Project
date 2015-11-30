package com.example.zhaorui.dvdcollector.Controller;

import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.GalleryList;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.google.gson.Gson;

/**
 * Created by teppie on 22/11/15.
 */
public class MyHttpClient {
    /**
     * Initialize friend
     */
    private Friend friend;
    /**
     * Initialize a string to store name
     */
    private String name;
    /**
     * Initialize tradelist
     */
    private TradeList tradeList;
    /**
     * Initialize gallery list
     */
    private GalleryList galleryList;
    /**
     * Initialize usernames and trade list in my httpclient
     * @param name string variable of username
     * @param tradeList string variable of trade list
     */
    public MyHttpClient(String name, TradeList tradeList) {
        this.name = name;
        this.tradeList = tradeList;
    }
    /**
     * Load gallery list and username in my httpclient
     * @param galleryList string variable of gallery
     * @param name string variable of username
     */
    public MyHttpClient(GalleryList galleryList, String name) {
        this.galleryList = galleryList;
        this.name = name;
    }
    /**
     * Load username in my httpclient
     * @param name string variable of username
     */
    public MyHttpClient(String name) {
        this.name = name;
    }
    /**
     * Initialize user's friend
     * @param friend string variable of user's friend
     */
    public void setUser(Friend friend) {
        this.friend = friend;
    }
    /**
     * Initialize trade list and username
     * @param tradeList string variable of trade list
     */
    public void setTradeList(TradeList tradeList) {
        this.tradeList = tradeList;
    }
    /**
     * Initialize gallery list
     * @param galleryList string variable of gallery
     */
    public void setGalleryList(GalleryList galleryList){
        this.galleryList = galleryList;
    }
    /**
     * Push user's friend to webservice
     */
    public void runPushFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.friend);
        userHttpClient.runPush();
    }
    /**
     * Push trade list to webservice
     */
    public void runPushTradeList(){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.tradeList, this.name);
        tradeHttpClient.runPush();
    }
    /**
     * Push gallery list to webservice
     */
    public void runPushGalleryList(){
        GalleryListHttpClient galleryListHttpClient = new GalleryListHttpClient(this.galleryList, this.name);
        galleryListHttpClient.runPush();
    }

    /**
     * Pull friends from webservice
     * @return friends data from webservice
     */
    public Friend runPullFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.name);
        Friend friend = userHttpClient.runPull();
        this.friend = friend;
        return friend;
    }
    /**
     * Pull trade list from webservice
     * @return trade list form webservice
     */
    public TradeList runPullTradeList(){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.name);
        TradeList tradeList = tradeHttpClient.runPull();
        this.tradeList = tradeList;
        return tradeList;
    }
    /**
     * Pull gallery from webservice
     * @return gallery data from webservice
     */
    public GalleryList runPullGalleryList(){
        GalleryListHttpClient galleryListHttpClient = new GalleryListHttpClient(this.name);
        GalleryList gallery = galleryListHttpClient.runPull();
        this.galleryList = gallery;
        return gallery;
    }

    /**
     * set a name
     * @param name a string variable
     */
    public void setName(String name) {
        this.name = name;
    }
}
