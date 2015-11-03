package com.example.zhaorui.dvdcollector.Model;

import android.test.ActivityInstrumentationTestCase2;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;

/**
 * Created by dingkai on 15/10/9.
 */
public class testInventory extends ActivityInstrumentationTestCase2 {
    public testInventory(Class activityClass) {
        super(activityClass);
    }

    public void testAdd() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        assertTrue(inventory.get(1) == dvd);
    }

    public void testSeeDetails() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        assertTrue(inventory.seeDetails(1).equals(dvd.getDetail()));
    }

    public void testAuthority() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.authority(1, true);
        assertTrue(dvd.isShareable());
    }

    public void testEditModify() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.editModify(1,"got changes");
        assertTrue(dvd.getDetail().equals("got changes"));
    }

    public void testDelete() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.delete(1);
        assertTrue(inventory.getSize() == 0);
    }

    public void testCategory() throws Exception{
        DVD dvd = new DVD();
        Inventory inventory = new Inventory();
        inventory.add(dvd);
        inventory.category(1,"rock!");
        assertTrue(dvd.getCategory().equals( "rock!"));
    }
}
