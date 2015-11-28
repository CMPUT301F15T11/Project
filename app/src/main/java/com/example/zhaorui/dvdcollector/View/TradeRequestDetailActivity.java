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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;

/**
 * <p>
 * The <code>TradeRequestDetailActivity</code> class controls the user interface of detail for incoming trade.
 * This class contains functions, onCreate, onCreateOptionsMenu and onOptionsItemSelected
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class TradeRequestDetailActivity extends BaseActivity {

    private TradeListController myTradeListController = new TradeListController(User.instance().getTradeList());
    private FriendsController fc = new FriendsController();

    private Button btnAccept;
    private Button btnDecline;
    private TextView tvBorrower;
    private TextView tvOwner;
    private TextView dvdBorrower;
    private TextView dvdOwner;
    private String ownerComments = "No comments";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_request_detail);
        btnAccept = (Button)findViewById(R.id.btn_accept_trade);
        btnDecline = (Button)findViewById(R.id.btn_decline_trade);
        tvBorrower = (TextView)findViewById(R.id.borrower_trade_detail_ongoing);
        tvOwner = (TextView)findViewById(R.id.owner_trade_detail_ongoing);
        dvdBorrower = (TextView)findViewById(R.id.dvds_borrower_trade_detail_ongoing);
        dvdOwner = (TextView)findViewById(R.id.dvds_owner_trade_detail_ongoing);

        Intent i = getIntent();
        position = i.getIntExtra("position",0);

        tvBorrower.setText(myTradeListController.getTradeRequests().get(position).getBorrower());
        tvOwner.setText(myTradeListController.getTradeRequests().get(position).getOwner());
        dvdOwner.setText(myTradeListController.getTradeRequests().get(position).getOwnerItem());
        dvdBorrower.setText(String.valueOf(myTradeListController.getTradeRequests().get(position).getBorrowerItemList()));

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTradeListController.declineTrade(position);
                Toast.makeText(TradeRequestDetailActivity.this, "Declined the trade!", Toast.LENGTH_SHORT);
                TradeRequestDetailActivity.this.finish();
            }
        });
    }

    private void showCommentDialog(){
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this)
            .setTitle("Any comments?")
                .setIcon( android.R.drawable.ic_dialog_info)
                .setView( et )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            ownerComments = et.getText().toString();
                            myTradeListController.acceptTrade(position);
                            Toast.makeText(TradeRequestDetailActivity.this, "Accept the trade!", Toast.LENGTH_SHORT);
                            TradeRequestDetailActivity.this.finish();
                        }
                })
        .show();
    }

    private void sendEmail(String emailAddress, String ownerComments, int position){
        Intent stats = new Intent(Intent.ACTION_SENDTO);
        stats.setData(Uri.parse("mailto:" + emailAddress));
        stats.putExtra(Intent.EXTRA_SUBJECT, "Trade Details");

        String content = "Trade Items from owner: "
                + String.valueOf(myTradeListController.getTradeRequests().get(position).getOwnerItem())
                + "\n Trade Items from borrower: "
                + String.valueOf(myTradeListController.getTradeRequests().get(position).getBorrowerItemList())
                + "\n Owner's comments: " + ownerComments
                + "\n Please check your Trade Center for details";

        stats.putExtra(Intent.EXTRA_TEXT, content);
        try {
            startActivity(stats);
        }catch (android.content.ActivityNotFoundException e) {
            // catch an exception when no email appliactions applicable in emulator
            // from http://stackoverflow.com/questions/14604349/activitynotfoundexception-while-sending-email-from-the-application
            Toast.makeText(TradeRequestDetailActivity.this,
                    "There are no email applications installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trade_request_detail, menu);
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
