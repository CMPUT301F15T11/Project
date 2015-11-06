package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.DVDController;
import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;

public class DVDInfoActivity extends BaseActivity {
    private int position;
    private InventoryController ic;
    private DVDController dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdinfo);

        dc = new DVDController();
        ic = new InventoryController();
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        int friendPosition = intent.getIntExtra("friendPosition",-1);
        if (friendPosition != -1){
            FriendsController fc = new FriendsController();
            ic.setInventory(fc.get(friendPosition).getInventory());
        }
        ArrayList<String> info = dc.read(ic.get(position));
        TextView text;
        text = (TextView) findViewById(R.id.tv_category_dvdinfo);
        text.setText(info.get(0));
        text = (TextView) findViewById(R.id.tv_name_dvdinfo);
        text.setText(info.get(1));
        text = (TextView) findViewById(R.id.tv_quantity_dvdinfo);
        text.setText(info.get(2));
        text = (TextView) findViewById(R.id.tv_quality_dvdinfo);
        text.setText(info.get(3));

        text = (TextView) findViewById(R.id.tv_sharable_dvdinfo);
        text.setText(info.get(5));
        text = (TextView) findViewById(R.id.tv_comment_view_dvdinfo);
        text.setText(info.get(6));

    }

    public void startGallery(View view){
        Intent intent = new Intent(DVDInfoActivity.this, PhotoActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

}