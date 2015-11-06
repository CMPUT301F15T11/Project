package com.example.zhaorui.dvdcollector.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Gallery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/*
 * Created by Zhaorui Chen
 *
*/

public class GalleryController {
    private Gallery gallery;

    public GalleryController(Gallery gallery) {
        this.gallery = gallery;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

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

    public Bitmap decodeFromString(String string){
        //modified based on https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptController.java
        byte[] byteArray = Base64.decode(string, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    public void addToGallery(String photoStr){
        ArrayList<String> photoStrs = gallery.getPhotoStrs();
        photoStrs.add(photoStr);
        gallery.setPhotoStrs(photoStrs);
    }

    public void removeFromGallery(String photoStr){
        ArrayList<String> photoStrs = gallery.getPhotoStrs();
        photoStrs.remove(photoStr);
        gallery.setPhotoStrs(photoStrs);
    }

    public void clearGallery(){
        this.gallery = new Gallery();
    }
}
