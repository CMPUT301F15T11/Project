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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.R;

import java.util.Observable;
import java.util.Observer;
/**
 * <p>
 * The <code>FriendListActivity</code> class controls the user interface of friend's list.
 * This class contains functions, onCreate, onCreateOptionsMenu and onOptionsItemSelected and a observer
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class FriendListActivity extends BaseActivity implements Observer {
    Button btnMenuMyInvent;
    private FriendsController fc;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        fc = new FriendsController();
        fc.addObserver(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fc.getFriends());
        ListView listView = (ListView) findViewById(R.id.listViewFriendList);
        listView.setAdapter(adapter);

        // open the menu
        btnMenuMyInvent = (Button) findViewById(R.id.btn_title_my_friends);
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
                FragmentManager fm = getFragmentManager();
                FriendListDialog newDialog = new FriendListDialog();
                newDialog.setPosition(position);
                newDialog.show(fm, "abc");
            }
        });
    }

    public void showSearchDialog() {
        FragmentManager fm = getFragmentManager();
        SearchDialog newDialog = new SearchDialog();
        newDialog.setMode("friends");
        newDialog.show(fm, "abc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.friendlist_add){
            showSearchDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void update(Observable ob, Object o){
        adapter.notifyDataSetChanged();
    }
}

