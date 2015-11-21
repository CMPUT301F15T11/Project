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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;

/**
 * <p>
 * The <code>TradeRequestsActivity</code> class controls the user interface of incoming trade.
 * This class contains functions, onCreate, onCreateOptionsMenu and onOptionsItemSelected
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class TradeRequestsActivity extends BaseActivity {
    private ArrayList<String> tradeRequestNames;
    private TradeList myTradeList = User.instance().getTradeList();
    private TradeListController myTradeListController = new TradeListController(myTradeList);
    private ArrayList<Trade> myTradeRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_requests);
    }

    @Override
    protected void onResume(){
        super.onResume();
        myTradeRequests = myTradeListController.getTradeRequests();
        tradeRequestNames = myTradeListController.getNames(myTradeRequests);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(TradeRequestsActivity.this, android.R.layout.simple_list_item_1, tradeRequestNames);
        ListView listView = (ListView) findViewById(R.id.listView_trade_requests);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // if click on the listview item, show the image on a new activity
                Intent i = new Intent(TradeRequestsActivity.this, TradeRequestDetailActivity.class);
                ///
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_requests, menu);
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