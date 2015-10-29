package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zhaorui.dvdcollector.R;

public class DVDInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdinfo);

        //sample for retrieving dvd information
        Intent intent = getIntent();
        String dvdDetail = intent.getStringExtra("dvdInfo");
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