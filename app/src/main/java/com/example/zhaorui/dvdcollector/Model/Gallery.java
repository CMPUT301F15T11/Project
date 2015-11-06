package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;

/**
 * Created by Zhaorui Chen on 04/11/15.
 */
public class Gallery {
    private ArrayList<String> photoStrs=null;

    public Gallery(ArrayList<String> photoStrs) {
        this.photoStrs = photoStrs;
    }

    public Gallery() {
        this.photoStrs = new ArrayList<String>();
    }

    public ArrayList<String> getPhotoStrs() {
        return photoStrs;
    }

    public void setPhotoStrs(ArrayList<String> photoStrs) {
        this.photoStrs = photoStrs;
    }

    public int getSize() {
        if (this.photoStrs == null) {
            return 0;
        } else {
            return photoStrs.size();
        }
    }
}
