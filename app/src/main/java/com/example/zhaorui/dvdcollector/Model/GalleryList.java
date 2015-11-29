package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;

/**
 * Created by teppie on 28/11/15.
 */
public class GalleryList {
    private ArrayList<Gallery> galleryArrayList;

    public GalleryList() {
        this.galleryArrayList = new ArrayList<Gallery>();
    }

    public GalleryList(int size) {
        this.galleryArrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            galleryArrayList.add(new Gallery());
        }
    }

    public Gallery get(int index){
        return galleryArrayList.get(index);
    }

    public int getSize(){
        return galleryArrayList.size();
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
