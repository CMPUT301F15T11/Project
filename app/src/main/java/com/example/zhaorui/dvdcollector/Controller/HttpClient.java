package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.google.gson.Gson;


/**
 * <p>
 * The <code>HttpClient</code> initialize and edit users' and friends' files and upload and download users' info, trade log and gallary to/from web server.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 22/11/15
 * @see org.apache.http.client
 */
public class HttpClient {
    private Friend friend;
    private String name;
    private TradeList tradeList;
    private Gallery gallery;
    private Gson gson = new Gson();
    private Boolean result;

    public HttpClient() {
    }

    /**
     *
     * @param friend a string variable
     */
    public HttpClient(Friend friend) {
        this.friend = friend;
    }

    /**
     *
     * @param name users' name
     * @param tradeList dvd trade list of the user
     */
    public HttpClient(String name, TradeList tradeList) {
        this.name = name;
        this.tradeList = tradeList;
    }

    /**
     *
     * @param gallery a set of photos of user
     * @param name user's profile name
     */
    public HttpClient(Gallery gallery, String name) {
        this.gallery = gallery;
        this.name = name;
    }

    /**
     *
     * @param name user's profile name
     */
    public HttpClient(String name) {
        this.name = name;
    }

    /**
     *
     * @param friend user's firend
     */
    public void setUser(Friend friend) {
        this.friend = friend;
    }

    /**
     *
     * @param tradeList dvd trade list of the user
     * @param userName user's profile name
     */
    public void setTradeList(TradeList tradeList, String userName) {
        this.name = userName;
        this.tradeList = tradeList;
    }

    /**
     *
     * @param gallery a set a photos of users
     * @param userName user's profile name
     */
    public void setGallery(Gallery gallery, String userName){
        this.name = userName;
        this.gallery = gallery;
    }

    /**
     * to upload user's friend to web server
     */
    public void runPushFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.friend);
        userHttpClient.runPush();
    }

    /**
     * to upload user's trade list to web server
     */
    public void runPushTradeList(){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.tradeList, this.name);
        tradeHttpClient.runPush();
    }

    /**
     * to upload user's gallery to web server
     */
    public void runPushGallery(){
        GalleryHttpClient galleryHttpClient = new GalleryHttpClient(this.gallery, this.name);
        galleryHttpClient.runPush();
    }

    /**
     * to download user's friend from web server
     * @return user's frined
     */
    public Friend runPullFriend(){
        UserHttpClient userHttpClient = new UserHttpClient(this.name);
        Friend friend = userHttpClient.runPull();
        this.friend = friend;
        return friend;
    }

    /**
     * to download user's trade list from web server
     * @return user's trade list
     */
    public TradeList runPullTradeList(){
        TradeHttpClient tradeHttpClient = new TradeHttpClient(this.name);
        TradeList tradeList = tradeHttpClient.runPull();
        this.tradeList = tradeList;
        return tradeList;
    }

    /**
     * to download user's gallery from web server
     * @return user's gallery
     */
    public Gallery runPullGallery(){
        GalleryHttpClient galleryHttpClient = new GalleryHttpClient(this.name);
        Gallery gallery = galleryHttpClient.runPull();
        this.gallery = gallery;
        return gallery;
    }

}
