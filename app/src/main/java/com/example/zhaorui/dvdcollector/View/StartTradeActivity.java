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
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Controller.TradeController;
import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.MyObserver;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;
import java.util.Observable;

/**
 * <p>
 * The <code>StartTradeActivity</code> class controls the user interface of start new trade.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class StartTradeActivity extends BaseActivity implements MyObserver{
    private LinearLayout ll1;
    private LinearLayout ll2;
    private Spinner spinner;
    private Button btnSendRequest;
    private TextView textView1;
    private TextView textView2;

    private FriendsController friendsController = new FriendsController();
    private Friends friendsList = friendsController.getFriends();
    private Friend owner = null;

    private InventoryController inventoryBorrowerController = new InventoryController(); //需要修改inventory controller
    String[] borrowerDvdNames = inventoryBorrowerController.getAllNames();

    private InventoryController inventoryOwnerController = new InventoryController();
    String[] ownerDvdNames;

    //选择的dvd的缓存,可以当做intent传给下一个activity
    ArrayList<DVD> borrowerDvdBuffer = new ArrayList<>();
    DVD ownerDvd = null;

    ArrayList<Integer> borrowerDvdSelectedBuffer = new ArrayList<>();

    private Trade trade = new Trade();
    private TradeList tradeList = User.instance().getTradeList();
    private TradeController tradeController = new TradeController(trade);
    private TradeListController tradeListController = new TradeListController(tradeList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trade);

        tradeListController.addObserver(this);
        inventoryOwnerController.addObserver(this);
        inventoryBorrowerController.addObserver(this);

        textView1 = (TextView)findViewById(R.id.tv_listing_names_borrower_dvd);
        textView2 = (TextView)findViewById(R.id.tv_listing_names_onwer_dvd);

        ll1 = (LinearLayout)findViewById(R.id.ll_add_borrower_dvd_new_trade);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowerMultipleChoiceDialog();
            }
        });

        ll2 = (LinearLayout)findViewById(R.id.ll_add_owner_dvd_new_trade);

        // set the spinner showing all friends
        spinner = (Spinner)findViewById(R.id.spinner_choose_owner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, friendsList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 这里需要初始化owner为一个friend///////////////////////////////////////////////////////此段仅在没有实现es的情况下适用
                owner = friendsController.getByName(spinner.getSelectedItem().toString());
                inventoryOwnerController.setInventory(owner.getInventory());
                ownerDvdNames = inventoryOwnerController.getAllNamesFriend();
                // in case choose another owner to trade with, clear all buffer
                ownerDvd = null;
                textView2.setText("");

                ll2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (owner != null) { // if have selected a friend to trade with
                            ownerSingleChoiceDialog();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSendRequest = (Button)findViewById(R.id.btn_send_trade_request);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownerDvd!=null) {
                    Log.e("DVD numbers for borrow", String.valueOf(borrowerDvdBuffer.size()));

                    //add info to trade
                    tradeController.changeBorrower(User.instance().getProfile().getName());
                    tradeController.changeOwner(owner.getProfile().getName());
                    tradeController.addBorrowerItem(borrowerDvdBuffer);
                    tradeController.addOwnerItem(ownerDvd);
                    tradeController.changeType("Current Outgoing");
                    tradeController.changeStatus("Pending");
                    tradeController.setName();
                    // add trade to trademanager
                    tradeListController.addTrade(trade);
                    //必须要求每个人都输入自己的邮箱
                    //sendEmail();

                    Toast.makeText(StartTradeActivity.this, "Trade has been sent to the owner", Toast.LENGTH_SHORT).show();
                    StartTradeActivity.this.finish();
                }else{
                    showPromptDialog();
                }
            }
        });
    }

    private void sendEmail(){
        String ownerEmailAddress = owner.getProfile().getContact();
        Intent stats = new Intent(Intent.ACTION_SENDTO);
        stats.setData(Uri.parse("mailto:" + ownerEmailAddress));
        stats.putExtra(Intent.EXTRA_SUBJECT, "A Trade Request");

        String content = "You have a trade request from: "+User.instance().getProfile().getName()
                                + "\n Please check your Trade Center for details";

        stats.putExtra(Intent.EXTRA_TEXT, content);
        try {
            startActivity(stats);
        }catch (android.content.ActivityNotFoundException e) {
            // catch an exception when no email appliactions applicable in emulator
            // from http://stackoverflow.com/questions/14604349/activitynotfoundexception-while-sending-email-from-the-application
            Toast.makeText(StartTradeActivity.this,
                    "There are no email applications installed.", Toast.LENGTH_SHORT).show();
        }
    }

    // A alert will be prompted if user haven't chosen a dvd from the owner
    private void showPromptDialog() {
        FragmentManager fm = getFragmentManager();
        TradeRequestInvalidDialog newDialog = new TradeRequestInvalidDialog();
        newDialog.show(fm, "abc");
    }

    // open a multiple choice dialog for the borrower
    // borrower is always the device user in this activity
    public void borrowerMultipleChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartTradeActivity.this);
        builder.setTitle("Select DVDs");

        // load already checked items
        boolean[] checked = new boolean[inventoryBorrowerController.getSharableInventory().size()];
        for(int i=0;i<checked.length;i++) {
            if (borrowerDvdSelectedBuffer.contains(i)) {
                checked[i] = true;
            } else {
                checked[i] = false;
            }
        }

        builder.setMultiChoiceItems(borrowerDvdNames, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) { // if check the item
                    borrowerDvdBuffer.add(inventoryBorrowerController.getSharableInventory().get(which));
                    borrowerDvdSelectedBuffer.add(which);
                } else {// if un-check the item
                    borrowerDvdBuffer.remove(inventoryBorrowerController.getSharableInventory().get(which));
                    borrowerDvdSelectedBuffer.remove(new Integer(which));
                }

                // write the selected dvds to the textview
                textView1.setText("");
                for (int index : borrowerDvdSelectedBuffer) {
                    textView1.setText(textView1.getText() + borrowerDvdNames[index] + " ; ");
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });


        builder.show();
    }

    // open a single choice dialog for the owner
    public void ownerSingleChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartTradeActivity.this);
        builder.setTitle("Select one DVD");

        int checked = 0;
        if (ownerDvd!=null){ // if have selected a dvd before
            for (int i=0;i<ownerDvdNames.length;i++){
                if(ownerDvdNames[i].equals(ownerDvd.getName())){
                    checked = i;
                }
            }
        }else{ // if it's the first time selecting owner's dvd
            ownerDvd = inventoryOwnerController.getInventory().get(checked);
        }

        builder.setSingleChoiceItems(ownerDvdNames, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ownerDvd = inventoryOwnerController.getInventory().get(which);
            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // write the selected dvd to the textview
                textView2.setText(ownerDvd.getName());

            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_trade, menu);
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

    public void update(Observable ob,Object o){
    }
}
