package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.google.gson.Gson;

/**
 * Created by teppie on 22/11/15.
 */

/**
 *Push and pull friend's name and trade list and gallery online
 */
public class MyHttpClient {
    private Friend friend;
    private String name;
    private TradeList tradeList;
    private Gallery gallery;
    private Gson gson = new Gson();
    private Boolean result;

    /**
     * User's HttpClient
     */
    public MyHttpClient() {
    }

    /**
     * Assign friends
     * @param friend string variable of user's friend
     */
    public MyHttpClient(Friend friend) {
        this.friend = friend;
    }

    /**
     * Assign username and trade list
     * @param name string variable of username
     * @param tradeList string variable of trade list
     */
    public MyHttpClient(String name, TradeList tradeList) {
        this.name = name;
        this.tradeList = tradeList;
    }

    /**
     * Assign gallery and username
     * @param gallery string variable of gallery
     * @param name string variable of username
     */
    public MyHttpClient(Gallery gallery, String name) {
        this.gallery = gallery;
        this.name = name;
    }

    /**
     * Assign username
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
     * @param userName string variable of username
     */
    public void setTradeList(TradeList tradeList, String userName) {
        this.name = userName;
        this.tradeList = tradeList;
    }

    /**
     * Initialize gallery
     * @param gallery string variable of gallery
     * @param userName string variable of username
     */
    public void setGallery(Gallery gallery, String userName){
        this.name = userName;
        this.gallery = gallery;
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
     * Push gallery to webservice
     */
    public void runPushGallery(){
        GalleryHttpClient galleryHttpClient = new GalleryHttpClient(this.gallery, this.name);
        galleryHttpClient.runPush();
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
    public Gallery runPullGallery(){
        GalleryHttpClient galleryHttpClient = new GalleryHttpClient(this.name);
        Gallery gallery = galleryHttpClient.runPull();
        this.gallery = gallery;
        return gallery;
    }

}
