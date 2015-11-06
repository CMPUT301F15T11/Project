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

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.Model.MyObserver;
import com.example.zhaorui.dvdcollector.R;

import java.util.Observable;
import java.util.Observer;
/**
 * <p>
 * The <code>CategoryActivity</code> class controls the user interface of Categories.
 * This class contains functions, onCreate, onCreateOptionsMenu and onOptionsItemSelected
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class CategoryActivity extends BaseActivity implements MyObserver{
    private InventoryController ic;
    private ArrayAdapter<?> adapter;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        final int friendPosition = getIntent().getIntExtra("friendPosition",-1);
        if (friendPosition == -1) {
            ic = new InventoryController();
            ic.addObserver(this);
        } else {
            FriendsController fc = new FriendsController();
            ic.setInventory(fc.get(friendPosition).getInventory());
        }
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                ic.getInventory(category));
        ListView listView = (ListView) findViewById(R.id.listViewCategory);
        listView.setAdapter(adapter);

        // click on a item from the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                FragmentManager fm = getFragmentManager();
                if (friendPosition == -1) {
                    MyInventoryDialog newDialog = new MyInventoryDialog();
                    newDialog.setPosition(ic.indexOf(ic.get(position)));
                    newDialog.show(fm, "abc");
                } else {
                    FriendInventoryDialog newDialog = new FriendInventoryDialog();
                    newDialog.setFriendPosition(friendPosition);
                    newDialog.setPosition(ic.indexOf(ic.get(position)));
                    newDialog.show(fm, "abc");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_romantic_dvd, menu);
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

    public void update(Observable ob, Object o){
        adapter.notifyDataSetChanged();
    }
}
