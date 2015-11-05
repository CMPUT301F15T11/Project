package com.example.zhaorui.dvdcollector.View;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

public class FriendInventoryActivity extends BaseActivity {
    // sample data

    //http://stackoverflow.com/questions/18913635/how-to-trigger-a-menu-button-click-event-through-code-in-android
    private Button btnMenuFriendInvent;
    private ListView listView;
    private FriendsController fc;
    private InventoryController ic;
    private ArrayAdapter<?> adapter;
    private int friendPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_inventory);
        fc = new FriendsController();
        ic = new InventoryController();
        friendPostion = getIntent().getIntExtra("position", -1);
        ic.setInventory(fc.get(friendPostion).getInventory());

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ic.getInventory());
        listView = (ListView) findViewById(R.id.listViewFriendInventory);
        listView.setAdapter(adapter);

        // open the menu
        btnMenuFriendInvent = (Button)findViewById(R.id.btn_title_friend_inventory);
        btnMenuFriendInvent.setOnClickListener(new View.OnClickListener() {
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
                FragmentManager fm = getFragmentManager();
                FriendInventoryDialog newDialog = new FriendInventoryDialog();
                newDialog.setPosition(position);
                newDialog.setFriendPosition(friendPostion);
                newDialog.show(fm, "abc");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_inventory, menu);
        return true;
    }

    private void showSearchDialog() {
        FragmentManager fm = getFragmentManager();
        SearchDialog newDialog = new SearchDialog();
        newDialog.setMode("inventory");
        newDialog.setIc(ic);
        newDialog.show(fm, "abc");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.browse_friend_inventory) {
            Intent i = new Intent(FriendInventoryActivity.this, BrowseInventActivity.class);
            i.putExtra("friendPosition",friendPostion);
            startActivity(i);
        }

        if (id == R.id.search_friend_inventory){
            showSearchDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}