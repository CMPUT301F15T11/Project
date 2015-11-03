package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/10/9.
 */
public class Search {
    private User user;

    public Search(User user) {
        this.user = user;
    }

    public Inventory searchInventory(int index){return null;}

    public Inventory searchByCategory(String category){return null;}

    public Inventory searchByText(String text){return null;}

    public Inventory searchShared(){return null;}
}