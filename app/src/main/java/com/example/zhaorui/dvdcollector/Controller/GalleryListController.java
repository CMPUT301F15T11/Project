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

/**
 * list of user's gallery
 */
public class GalleryListController {
    /**
     * Initialize a gallery.
     */
    private GalleryList galleryList;
    /**
     * user's name
     */
    private String userName;
    /**
     * Get the input list of gallery.
     */
    public GalleryListController() {//only for user
        this.userName = User.instance().getProfile().getName();
        this.galleryList = User.instance().getGalleryList();
    }

    /**
     * get the list of user's name
     * @param userName a string variable, the name of the user
     */
    public GalleryListController(String userName) {
        // intended for non-device-users
        this.userName = userName;
    }

    /**
     * get gallery list
     * @param index a index variable
     * @return a list of gallery
     */
    public Gallery get(int index){
        return galleryList.get(index);
    }

    /**
     * add a gallery to the list
     * @param gallery array list of photo strings
     */
    public void addGallery(Gallery gallery){
        galleryList.appendGallery(gallery);
        ObserverManager.getInstance().notifying("Gallery");
    }

    /**
     * to delete a index from gallery
     * @param index a index variable
     */
    public void removeGallery(int index){
        galleryList.removeGallery(index);
        ObserverManager.getInstance().notifying("Gallery");
    }

    /**
     * add photo and dvd index to the gallery
     * @param dvdIndex a index variable and is dvd's index
     * @param photoStr a string variable
     */
    public void addPhotoStr(int dvdIndex, String photoStr){
        galleryList.addPhoto(dvdIndex, photoStr);
        ObserverManager.getInstance().notifying("Gallery");
    }

    /**
     * remove photo and dvd index from the gallery
     * @param dvdIndex a index variable and is dvd's index
     * @param photoStr a string variable
     */
    public void removePhotoStr(int dvdIndex, String photoStr) {
        galleryList.removePhoto(dvdIndex, photoStr);
        ObserverManager.getInstance().notifying("Gallery");
    }

    /**
     * encode to store.
     * @param bitmap a string variable
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

    /**
     * pull the list of the gallery
     */
    public void pullGalleryList(){
        MyHttpClient myHttpClient = new MyHttpClient(userName);
        galleryList = myHttpClient.runPullGalleryList();
    }

}
