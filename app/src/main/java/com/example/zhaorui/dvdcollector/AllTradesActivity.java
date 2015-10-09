package com.example.zhaorui.dvdcollector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AllTradesActivity extends ActionBarActivity {

    private String[] data = { "[Current] From Jack","[Current] From Lucy","[Current] From Jack","[Declined] From Lucy",
            "[Accepted] From Lucy","[Declined] From Lucy","[Accepted] From Jack","[Accepted] From Jack","[Declined] From Jack",
            "[Declined] From Jack","[Accepted] From Jack","[Declined] From Jack","[Declined] From Lucy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trades);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllTradesActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.listViewAllTrades);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_trades, menu);
        return true;
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
