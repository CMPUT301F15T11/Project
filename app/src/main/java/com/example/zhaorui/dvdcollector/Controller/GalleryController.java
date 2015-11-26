package com.example.zhaorui.dvdcollector.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Gallery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * <p>
 * The <code>GalleryController</code> is a controller of <code>Gallery</code>, which controls photo information.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 * @see android.graphics.Bitmap
 */

/**
 * User's gallery
 */
public class GalleryController {
    /**
     * Initialize a gallery.
     */
    private Gallery gallery;

    /**
     * Get the input gallery.
     * @param gallery
     */
    public GalleryController(Gallery gallery) {
        this.gallery = gallery;
    }

    /**
     * This function is called when other functions need to use the gallery
     * @return gallery
     */
    public Gallery getGallery() {
        return gallery;
    }

    /**
     * This function is called when other functions need to set the gallery
     * @param gallery array list of photo strings.
     */
    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    /**
     * encode to store.
     * @param bitmap array list of bitmap string
     * @return encoded bitmap
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
        }while(encoded.length()>=65536);
        return encoded;
    }

    /**
     * Decode to load
     * @param string which returned by encode
     * @return  bitmap
     */
    public Bitmap decodeFromString(String string){
        //modified based on https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptController.java
        byte[] byteArray = Base64.decode(string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    /**
     * To add a new photo to the photo string
     * @param photoStr a string variable
     */
    public void addToGallery(String photoStr){
        ArrayList<String> photoStrs = gallery.getPhotoStrs();
        photoStrs.add(photoStr);
        gallery.setPhotoStrs(photoStrs);
    }

    /**
     * To delete a photo from the gallery
     * @param photoStr a string variable
     */
    public void removeFromGallery(String photoStr){
        ArrayList<String> photoStrs = gallery.getPhotoStrs();
        photoStrs.remove(photoStr);
        gallery.setPhotoStrs(photoStrs);
    }

    /**
     * To delete everything from the gallery.
     */
    public void clearGallery(){
        this.gallery = new Gallery();
    }
}
