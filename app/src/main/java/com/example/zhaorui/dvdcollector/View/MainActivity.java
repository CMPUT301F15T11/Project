package com.example.zhaorui.dvdcollector.View;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.R;

public class MainActivity extends BaseActivity {
    Button btnInvent;
    Button btnTrade;
    Button btnFriends;
    Button btnConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInvent = (Button)findViewById(R.id.btnInventMain);
        btnTrade = (Button)findViewById(R.id.btnTradeMain);
        btnFriends = (Button)findViewById(R.id.btnFriendsMain);
        btnConfig = (Button)findViewById(R.id.btnConfigMain);
        DataManager.instance().loadFromFile(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startMyInvent(View view){
        Intent i = new Intent(MainActivity.this, MyInventoryActivity.class);
        startActivity(i);
    }

    public void startFriendList(View view){
        Intent i = new Intent(MainActivity.this, FriendListActivity.class);
        startActivity(i);
    }

    public void startConfig(View view){
        Intent i = new Intent(MainActivity.this, ConfigActivity.class);
        startActivity(i);
    }

    public void startTradeCenter(View view){
        Intent i = new Intent(MainActivity.this, TradeCenterActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
