package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/10/9.
 */
public class Inventory {
    private int size;

    public int getSize() {
        return size;
    }

    public Inventory() {}

    public void add(DVD dvd){}

    public DVD get(int index){return null;}

    public String seeDetails(int index){return null;}

    public void authority(int index,boolean shareable){}

    public void editModify(int index,String modify){}

    public void delete(int index){}

    public void category(int index, String type){}
}
