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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dvdinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.dvd_info_edit) {
            Intent i = new Intent(DVDInfoActivity.this, DVDInfoEditActivity.class);
            startActivity(i);
        }

        if (id == R.id.dvd_info_remove) {
            /////////////////////////////////////
        }

        return super.onOptionsItemSelected(item);
    }
}