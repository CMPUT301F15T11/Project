package com.example.zhaorui.dvdcollector.Model;

/**
 * Created by dingkai on 15/11/2.
 */
public class Friend {
    private Inventory inventory;
    private UserProfile profile;

    public Friend(User user){
        inventory = user.getInventory();
        inventory.getObs().deleteObservers();
        profile = user.getProfile();
        profile.deleteObservers();
    }

    public Friend(Inventory inventory, UserProfile profile) {
        this.inventory = inventory;
        this.profile = profile;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public UserProfile getProfile() {
        return profile;
    }
}
