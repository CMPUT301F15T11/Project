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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeManager;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;

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
    private TradeManager tradeManager = User.instance().getTradeManager();
    private TextView textViewBorrower;
    private TextView textViewBorrowerDvd;
    private TextView textViewOwner;
    private TextView textViewOwnerDvd;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_details);

        textViewBorrower = (TextView) findViewById(R.id.borrower_trade_detail_completed);
        textViewOwner = (TextView) findViewById(R.id.owner_trade_detail_completed);
        textViewBorrowerDvd = (TextView) findViewById(R.id.dvds_borrower_trade_detail_completed);
        textViewOwnerDvd = (TextView) findViewById(R.id.dvds_owner_trade_detail_completed);
        textViewStatus = (TextView) findViewById(R.id.status_trade_details_completed);

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        int pos = i.getIntExtra("position", 0);

        Trade tradeToShow = tradeManager.getTradesOfType(type).get(pos);

        textViewBorrower.setText(tradeToShow.getBorrower());
        textViewOwner.setText(tradeToShow.getOwner());
        if(tradeToShow.borrowerItemList.size()!=0){
            textViewBorrowerDvd.setText(tradeToShow.getBorrowerItemList().toString());
        }else{
            textViewBorrowerDvd.setText("None");
        }
        textViewOwnerDvd.setText(tradeToShow.getOwnerItem().getName());
        textViewStatus.setText(tradeToShow.getStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_completed_trade_detail, menu);
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
