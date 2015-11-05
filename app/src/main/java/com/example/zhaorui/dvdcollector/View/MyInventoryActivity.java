/*
 *
 *University of Alberta CMPUT 301 Group: CMPUT301F15T11
 *Copyright {2015} {Dingkai Liang, Zhaorui Chen, Jiaxuan Yue, Xi Zhang, Qingdai Du, Wei Song}
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
*/
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
import com.example.zhaorui.dvdcollector.R;

import java.util.Observable;
import java.util.Observer;
/**
 * <p>
 * The <code>MyInventoryActivity</code> class controls the user interface of my inventory
 * This class contains functions, onCreate, onCreateOptionsMenu and onOptionsItemSelected
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class MyInventoryActivity extends BaseActivity implements Observer {
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
        controller.addObserver(this);

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
                // open up a dialog
                FragmentManager fm = getFragmentManager();
                MyInventoryDialog newDialog = new MyInventoryDialog();
                newDialog.setPosition(position);
                newDialog.show(fm, "abc");
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
        SearchDialog newDialog = new SearchDialog();
        newDialog.setMode("inventory");
        newDialog.show(fm, "abc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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

    public void update(Observable ob,Object o){
        adapter.notifyDataSetChanged();
    }
}

