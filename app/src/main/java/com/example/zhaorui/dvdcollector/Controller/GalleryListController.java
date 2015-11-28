package com.example.zhaorui.dvdcollector.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.GalleryList;
import com.example.zhaorui.dvdcollector.Model.ObserverManager;
import com.example.zhaorui.dvdcollector.Model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * <p>
 * The <code>GalleryListController</code> is a controller of <code>Gallery</code>, which controls photo information.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 * @see Bitmap
 */

public class GalleryListController {
    /**
     * Initialize a gallery.
     */
    private GalleryList galleryList;
    private String userName;

    public GalleryListController() {//only for user
        this.userName = User.instance().getProfile().getName();
        this.galleryList = User.instance().getGalleryList();
    }

    public GalleryListController(GalleryList galleryList) {
        this.galleryList = galleryList;
    }

    public GalleryListController(String userName) {
        // intended for non-device-users
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Gallery get(int index){
        return galleryList.get(index);
    }

    public GalleryList getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(GalleryList galleryList) {
        this.galleryList = galleryList;
    }

    public void addGallery(Gallery gallery){
        galleryList.appendGallery(gallery);
        ObserverManager.getInstance().notifying("Gallery");
    }

    public void removeGallery(int index){
        galleryList.removeGallery(index);
        ObserverManager.getInstance().notifying("Gallery");
    }

    public void addPhotoStr(int dvdIndex, String photoStr){
        galleryList.addPhoto(dvdIndex, photoStr);
        ObserverManager.getInstance().notifying("Gallery");
    }

    public void removePhotoStr(int dvdIndex, String photoStr){
        galleryList.removePhoto(dvdIndex, photoStr);
        ObserverManager.getInstance().notifying("Gallery");
    }

    /**
     * encode to store.
     * @param bitmap
     * @return a string
     */
    public String encodeFromBitmap(Bitmap bitmap){
        String encoded =null;
        int quality=100;
        do{
            //http://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string Author: jeet
            //modified based on https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptController.java
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            quality-=10;
        }while(encoded.length()>=65536);//ensure it's smaller than 65536 bytes
        return encoded;
    }

    /**
     * Decode to laod
     * @param string which returned by encode
     * @return the bitmap.
     */
    public Bitmap decodeFromString(String string){
        //modified based on https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptController.java
        byte[] byteArray = Base64.decode(string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    public void pullGalleryList(){
        MyHttpClient myHttpClient = new MyHttpClient(userName);
        galleryList = myHttpClient.runPullGalleryList();
    }

}
