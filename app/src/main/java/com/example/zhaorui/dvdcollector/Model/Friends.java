package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/10/9.
 */
public class Friends {

    private User user;
    private int numFriends;

    public Friends(User user) {
        this.user = user;
        numFriends = 0;
    }

    static public User track(String name){return null;}

    public void add(User friend){}

    public User getFriend(int index){return null;}

    public void remove(int index){}

    public User getUser() {
        return user;
    }


    public int getSize() {
        return numFriends;
    }

    public String viewProfile(int index){return null;}

}
