package com.example.zhaorui.dvdcollector;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TradeCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_center);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_center, menu);
        return true;
    }


    public void startNewTrade(View view){
        Intent i = new Intent(TradeCenterActivity.this, StartTradeActivity.class);
        startActivity(i);
    }

    public void startIncomingTrade(View view){
        Intent i = new Intent(TradeCenterActivity.this, IncomeTradeActivity.class);
        startActivity(i);
    }

    public void startAllTrade(View view){
        Intent i = new Intent(TradeCenterActivity.this, AllTradesActivity.class);
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
