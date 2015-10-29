package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/10/9.
 */
public class DVD {
    private  String detail;
    private String category;
    private boolean shareable;
    private String image;
    public boolean hasImage = false;

    public String getCategory() {
        return category;
    }

    public boolean isShareable() {
        return shareable;
    }

    public DVD(String detail) {
        this.detail = detail;
    }

    public String getDetail(){return detail;}

    public void attachPhoto(String imagePath){}

    public boolean viewPhoto(){
        String str = "";
        return (str == "photo has viewed successful");
    }

    public void deletePhote(){}

    public Boolean downloadPhoto(Configuration confirm){
        String str = "";
        return (str == "photo has downloaded successful");
    }
}
