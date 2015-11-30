package com.example.zhaorui.dvdcollector.Model;

import java.util.ArrayList;
/**
 * <p>
 * The <code>GalleryList</code> class manages photos list that are used in this app.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 04/11/15
 * @see ArrayList;
 */
public class GalleryList {
    /**
     * Initialize a array list to store photos.
     */
    private ArrayList<Gallery> galleryArrayList;

    /**
     * store the list of gallery
     */
    public GalleryList() {
        this.galleryArrayList = new ArrayList<Gallery>();
    }

    /**
     * get a new gallery list by the size
     * @param size a index variable
     */
    public GalleryList(int size) {
        this.galleryArrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            galleryArrayList.add(new Gallery());
        }
    }

    /**
     * get the index of the photo
     * @param index a index variable
     * @return index
     */
    public Gallery get(int index){
        return galleryArrayList.get(index);
    }

    /**
     * get the size of the index
     * @return the size of the gallery list
     */
    public int getSize(){
        return galleryArrayList.size();
    }

    /**
     * append the gallery
     * @param gallery new gallery
     */
    public void appendGallery(Gallery gallery){
        galleryArrayList.add(gallery);
    }

    /**
     * add photo to the gallery
     * @param index a index variable
     * @param photoStr a string variable
     */
    public void addPhoto(int index, String photoStr){
        galleryArrayList.get(index).addPhotoStr(photoStr);
    }

    /**
     * remove the index of photo from the gallery
     * @param index a index variable
     */
    public void removeGallery(int index){
        galleryArrayList.remove(index);
    }

    /**
     * remove photo from the gallery
     * @param index a index variable
     * @param photoStr a string variable
     */
    public void removePhoto(int index, String photoStr){
        galleryArrayList.get(index).removePhotoStr(photoStr);
    }


}
