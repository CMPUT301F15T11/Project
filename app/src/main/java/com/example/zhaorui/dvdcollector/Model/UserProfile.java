package com.example.zhaorui.dvdcollector.Model;

import java.util.Observable;

/**
 * Created by dingkai on 15/11/1.
 */
public class UserProfile extends Observable{
    private String name;
    private String contact;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
