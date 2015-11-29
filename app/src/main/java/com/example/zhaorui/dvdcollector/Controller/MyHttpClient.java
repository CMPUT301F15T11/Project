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
    private Friend friend;
    private String name;
    private TradeList tradeList;
    private GalleryList galleryList;
    private Gson gson = new Gson();
    private Boolean result;

    public MyHttpClient() {
    }

    public MyHttpClient(String name, TradeList tradeList) {
        this.name = name;
        this.tradeList = tradeList;
    }

    public MyHttpClient(GalleryList galleryList, String name) {
        this.galleryList = galleryList;
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

    public void setGalleryList(GalleryList galleryList){
        this.galleryList = galleryList;
    }

    public void runPushFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.friend);
        userHttpClient.runPush();
    }

    public void runPushTradeList(){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.tradeList, this.name);
        tradeHttpClient.runPush();
    }

    public void runPushGalleryList(){
        GalleryListHttpClient galleryListHttpClient = new GalleryListHttpClient(this.galleryList, this.name);
        galleryListHttpClient.runPush();
    }

    public Friend runPullFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.name);
        Friend friend = userHttpClient.runPull();
        this.friend = friend;
        return friend;
    }

    public TradeList runPullTradeList(){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.name);
        TradeList tradeList = tradeHttpClient.runPull();
        Log.e("HttpClient","Run Pull TradeList");
        Log.e("HttpClient",String.valueOf(tradeList));
        Log.e("HttpCli trades is null",String.valueOf(tradeList==null));
        Log.e("HttpCli userName",name);
        this.tradeList = tradeList;
        return tradeList;
    }

    public GalleryList runPullGalleryList(){
        GalleryListHttpClient galleryListHttpClient = new GalleryListHttpClient(this.name);
        GalleryList gallery = galleryListHttpClient.runPull();
        Log.e("HttpClient","Run Pull GalleryList");
        this.galleryList = gallery;
        return gallery;
    }

    public void setName(String name) {
        this.name = name;
    }
}
