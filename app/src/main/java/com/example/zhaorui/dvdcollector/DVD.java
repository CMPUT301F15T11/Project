package com.example.zhaorui.dvdcollector;

/**
 * Created by zhaorui on 10/7/15.
 */
public class DVD {
    private String name;
    private boolean sharable;
    private String comments;
    private String quality;

    public DVD( String name, boolean sharable,String quality) {
        this.name = name;
        this.sharable = sharable;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSharable() {
        return sharable;
    }

    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        if (this.sharable) {
            this.comments = comments;
        }
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}