package com.example.zhaorui.dvdcollector.Controller;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Date;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.example.zhaorui.dvdcollector.Model.Photo;

/**
 * Created by zhaorui on 11/3/15.
 */
public class PhotoController {

    private Photo photo = null;


    public PhotoController(Photo photo){
        this.photo=photo;
    }

    public void addPhoto(Bitmap bm){

        String encoded =null;
        int quality=100;
        do{
            //http://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string Author: jeet
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            quality-=10;
        }while(encoded.length()>=65536);

        photo.setPhotoStr(encoded);
    }

    public void deletePhoto(){
        photo.setPhotoStr(null);
    }
}
