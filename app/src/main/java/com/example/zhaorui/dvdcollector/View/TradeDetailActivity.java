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
*/package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;
import com.google.gson.Gson;

/**
 * <p>
 * The <code>TradeDetailActivity</code> class controls the user interface of trade detail.
 * This class contains functions, onCreate, onCreateOptionsMenu and onOptionsItemSelected
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class TradeDetailActivity extends BaseActivity {
    private TradeList tradeList = User.instance().getTradeList();
    private TradeListController tradeListController = new TradeListController(tradeList);
    private Trade tradeToShow;
    private String nameOfTrade;
    private String idOfTrade;

    private TextView textViewBorrower;
    private TextView textViewBorrowerDvd;
    private TextView textViewOwner;
    private TextView textViewOwnerDvd;
    private TextView textViewStatus;
    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_details);

        textViewBorrower = (TextView) findViewById(R.id.borrower_trade_detail_completed);
        textViewOwner = (TextView) findViewById(R.id.owner_trade_detail_completed);
        textViewBorrowerDvd = (TextView) findViewById(R.id.dvds_borrower_trade_detail_completed);
        textViewOwnerDvd = (TextView) findViewById(R.id.dvds_owner_trade_detail_completed);
        textViewStatus = (TextView) findViewById(R.id.status_trade_details_completed);
        btnMenu = (Button)findViewById(R.id.btn_title_trade_detail);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });

        Intent i = getIntent();

        if(i.hasExtra("type")) {
            String type = i.getStringExtra("type");
            int pos = i.getIntExtra("position", 0);

            tradeToShow = tradeListController.getTradesOfType(type).get(pos);
        }
        else if(i.hasExtra("status")){
            String status = i.getStringExtra("status");
            int pos = i.getIntExtra("position", 0);

            tradeToShow = tradeListController.getTradeOfStatus(status).get(pos);
        }
        idOfTrade = i.getStringExtra("id");

        Log.e("DVD", tradeToShow.getStatus());
        Log.e("DVD", tradeToShow.getType());

        textViewBorrower.setText(tradeToShow.getBorrower());
        textViewOwner.setText(tradeToShow.getOwner());
        if(tradeToShow.getBorrowerItemList().size()!=0){
            textViewBorrowerDvd.setText(String.valueOf(tradeToShow.getBorrowerItemList()));
        }else{
            textViewBorrowerDvd.setText("None");
        }
        textViewOwnerDvd.setText(tradeToShow.getOwnerItem());
        textViewStatus.setText(tradeToShow.getStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close_menu_trade_details) {
            return true;
        }

        if(id==R.id.set_to_complete){
            tradeListController.setTradeComplete(idOfTrade);
            textViewStatus.setText(tradeListController.getTradeById(idOfTrade).getStatus());
            Log.e("dvd",tradeListController.getTradeById(idOfTrade).getStatus());
        }

        if(id == R.id.start_counter_trade){
            Intent i = new Intent(TradeDetailActivity.this, CounterTradeActivity.class);
            Gson gson = new Gson();
            i.putExtra("trade",gson.toJson(tradeToShow));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
