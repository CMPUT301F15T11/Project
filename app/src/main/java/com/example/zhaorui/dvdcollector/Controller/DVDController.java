package com.example.zhaorui.dvdcollector.Controller;

import com.example.zhaorui.dvdcollector.Model.DVD;

import java.util.ArrayList;

/**
 * Created by dingkai on 15/11/2.
 */
public class DVDController {
    public DVD create(ArrayList<String> info,boolean sharable){
        assert(info.size() == 5);
        DVD dvd = new DVD();
        dvd.setCategory(info.get(0));
        dvd.setName(info.get(1));
        dvd.setQuantity(info.get(2));
        dvd .setQuality(info.get(3));
        dvd.setComments(info.get(4));
        dvd.setSharable(sharable);
        return dvd;
    }

    public ArrayList<String> read(DVD dvd){
        ArrayList<String> info = new ArrayList<String>();
        info.add(dvd.getCategory());
        info.add(dvd.getName());
        info.add(dvd.getQuantity());
        info.add(dvd.getQuality());
        if (dvd.isSharable()){
            info.add("Yes");
        } else{
            info.add("No");
        }
        info.add(dvd.getComments());
        return info;
    }

    public ArrayList<String> categories(){
        ArrayList<String> categories = new ArrayList<String>();
        for (String category : DVD.getCategories()){
            categories.add(category);
        }
        return categories;
    }


}
