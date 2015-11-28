package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;

/**
 * Created by teppie on 28/11/15.
 */
public class GalleryList {
    private ArrayList<Gallery> galleryArrayList;

    public GalleryList(ArrayList<Gallery> galleryArrayList) {
        this.galleryArrayList = galleryArrayList;
    }

    public GalleryList() {
        this.galleryArrayList = new ArrayList<Gallery>();
    }

    public Gallery get(int index){
        return galleryArrayList.get(index);
    }

    public ArrayList<Gallery> getGalleryArrayList() {
        return galleryArrayList;
    }

    public int getSize(){
        return galleryArrayList.size();
    }

    public void setGalleryArrayList(ArrayList<Gallery> galleryArrayList) {
        this.galleryArrayList = galleryArrayList;
    }

    public void appendGallery(Gallery gallery){
        galleryArrayList.add(gallery);
    }

    public void addPhoto(int index, String photoStr){
        galleryArrayList.get(index).addPhotoStr(photoStr);
    }

    public void removeGallery(int index){
        galleryArrayList.remove(index);
    }

    public void removePhoto(int index, String photoStr){
        galleryArrayList.get(index).removePhotoStr(photoStr);
    }


}
