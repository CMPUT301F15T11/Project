package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by zhaorui on 11/3/15.
 */
public class Photo {
    protected String photoStr=null;
    private transient ArrayList<Observer> listeners;
    private transient ArrayList<Observer> modelListeners;


    public Photo(){
        listeners=new ArrayList<Observer>();
        modelListeners=new ArrayList<Observer>();
    }


    public Photo(Photo photo){
        listeners=new ArrayList<Observer>();
        modelListeners=new ArrayList<Observer>();
        photoStr=photo.getPhotoStr();
    }


    public String getPhotoStr(){
        return photoStr;
    }


    public boolean getMissValue() {
        return photoStr==null;
    }


    public void setPhotoStr(String photo){
    }
}
