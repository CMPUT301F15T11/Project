package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/10/9.
 */
public class Configuration {
    private boolean confirm;
    public Configuration() {
    }

    public Configuration(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isConfirm() {
        return confirm;
    }
}
