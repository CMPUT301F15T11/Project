package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.DVDController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;

public class DVDInfoActivity extends BaseActivity {
    private int position;
    private InventoryController ic = new InventoryController();
    private DVDController dc = new DVDController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdinfo);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
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
        text.setText(info.get(4));
        text = (TextView) findViewById(R.id.tv_comment_view_dvdinfo);
        text.setText(info.get(5));
    }

}