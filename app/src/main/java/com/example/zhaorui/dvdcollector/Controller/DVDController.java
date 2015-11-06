package com.example.zhaorui.dvdcollector.Controller;

import android.graphics.Bitmap;
import android.util.Base64;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Gallery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by dingkai on 15/11/2.
 */
public class DVDController {
    public DVD create(ArrayList<String> info,boolean sharable, Gallery gallery){
        DVD dvd = new DVD();
        dvd.setCategory(info.get(0));
        dvd.setName(info.get(1));
        dvd.setQuantity(info.get(2));
        dvd.setQuality(info.get(3));
        dvd.setHasPhoto(info.get(4)=="Yes");
        dvd.setComments(info.get(5));
        dvd.setSharable(sharable);
        dvd.setGallery(gallery);
        return dvd;
    }

    public ArrayList<String> read(DVD dvd){
        ArrayList<String> info = new ArrayList<String>();
        info.add(dvd.getCategory());
        info.add(dvd.getName());
        info.add(dvd.getQuantity());
        info.add(dvd.getQuality());
        if (dvd.isHasPhoto()){
            info.add("Yes");
        }else{
            info.add("No");
        }
        if (dvd.isSharable()){
            info.add("Yes");
        } else{
            info.add("No");
        }
        info.add(dvd.getComments());
        return info;
    }

    public Gallery readPhoto(DVD dvd){
        return dvd.getGallery();
    }

    public void changeGallery(DVD dvd, Gallery gallery){
        dvd.setGallery(gallery);
        if (gallery.getSize()!=0){
            dvd.setHasPhoto(true);
        }
    }

    public ArrayList<String> categories(){
        ArrayList<String> categories = new ArrayList<String>();
        for (String category : DVD.getCategories()){
            categories.add(category);
        }
        return categories;
    }

}
