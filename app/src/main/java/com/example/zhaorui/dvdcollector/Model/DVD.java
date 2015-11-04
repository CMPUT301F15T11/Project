package com.example.zhaorui.dvdcollector.Model;

import android.net.Uri;
import java.util.ArrayList;

/**
 * Created by dingkai on 15/10/9.
 */
public class DVD {
    private String category;
    private String name;
    private String quantity;
    private String quality;
    private String comments;
    private String photoStr = null;


    private boolean sharable;
    private final static String[] categories = {"Games","Romance","Documentary","Sci-Fi","Horror",
            "Action","Comedy","Edu","Story","Fantasy"};

    public DVD(){}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isSharable() {
        return sharable;
    }

    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }

    public static String[] getCategories() {
        return categories;
    }

    public String getPhotoStr() {
        return photoStr;
    }

    public void setPhotoStr(String photoStr) {
        this.photoStr = photoStr;
    }


    @Override
    public String toString(){ return name;}
}
