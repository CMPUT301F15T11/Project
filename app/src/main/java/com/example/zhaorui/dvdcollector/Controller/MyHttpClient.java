package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.google.gson.Gson;

/**
 * Created by teppie on 22/11/15.
 */
public class MyHttpClient {
    private Friend friend;
    private String name;
    private TradeList tradeList;
    private Gallery gallery;
    private Gson gson = new Gson();
    private Boolean result;

    public MyHttpClient() {
    }

    public MyHttpClient(Friend friend) {
        this.friend = friend;
    }

    public MyHttpClient(String name, TradeList tradeList) {
        this.name = name;
        this.tradeList = tradeList;
    }

    public MyHttpClient(Gallery gallery, String name) {
        this.gallery = gallery;
        this.name = name;
    }

    public MyHttpClient(String name) {
        this.name = name;
    }

    public void setUser(Friend friend) {
        this.friend = friend;
    }

    public void setTradeList(TradeList tradeList) {
        this.tradeList = tradeList;
    }

    public void setGallery(Gallery gallery){
        this.gallery = gallery;
    }

    public void runPushFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.friend);
        userHttpClient.runPush();
    }

    public void runPushTradeList(String mode){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.tradeList, this.name);
        tradeHttpClient.runPush(mode);
    }

    public void runPushGallery(){
        GalleryHttpClient galleryHttpClient = new GalleryHttpClient(this.gallery, this.name);
        galleryHttpClient.runPush();
    }

    public Friend runPullFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.name);
        Friend friend = userHttpClient.runPull();
        this.friend = friend;
        return friend;
    }

    public TradeList runPullTradeList(String mode){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.name);
        TradeList tradeList = tradeHttpClient.runPull(mode);
        this.tradeList = tradeList;
        return tradeList;
    }

    public Gallery runPullGallery(){
        GalleryHttpClient galleryHttpClient = new GalleryHttpClient(this.name);
        Gallery gallery = galleryHttpClient.runPull();
        this.gallery = gallery;
        return gallery;
    }

    public void setName(String name) {
        this.name = name;
    }
}
