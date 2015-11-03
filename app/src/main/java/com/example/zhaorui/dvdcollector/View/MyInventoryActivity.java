package com.example.zhaorui.dvdcollector.View;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.R;

public class MyInventoryActivity extends BaseActivity {
    private InventoryController controller;
    ArrayAdapter<DVD> adapter;

    //http://stackoverflow.com/questions/18913635/how-to-trigger-a-menu-button-click-event-through-code-in-android
    Button btnMenuMyInvent;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);
        listView = (ListView) findViewById(R.id.listViewMyInventory);
        controller = new InventoryController();

        // open the menu
        btnMenuMyInvent = (Button)findViewById(R.id.btn_title_my_inventory);
        btnMenuMyInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });

        // click on a item from the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // open up a dialog (should with a parameter )
                FragmentManager fm = getFragmentManager();
                MyInventoryDialog newDialog = new MyInventoryDialog();
                newDialog.setPosition(position);
                newDialog.setAdapter(adapter);
                newDialog.show(fm, "abc");
                //now check user choose which action
                //see MyInventoryDialog.java for implementation
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter = new ArrayAdapter<DVD>(MyInventoryActivity.this, android.R.layout.simple_list_item_1, controller.getInventory());
        listView.setAdapter(adapter);
    }

    private void showSearchDialog() {
        FragmentManager fm = getFragmentManager();
        SearchDialog newDialog = (SearchDialog) new SearchDialog();
        newDialog.show(fm, "abc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_to_my_inventory) {
            Intent i = new Intent(MyInventoryActivity.this, DVDAddActivity.class);
            startActivity(i);
        }

        if (id == R.id.browse_my_inventory) {
            Intent i = new Intent(MyInventoryActivity.this, BrowseInventActivity.class);
            startActivity(i);
        }

        if (id == R.id.search_my_inventory){
            showSearchDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}

