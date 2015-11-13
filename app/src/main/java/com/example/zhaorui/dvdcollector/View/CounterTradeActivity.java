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

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;

/**
 * <p>
 * The <code>StartTradeActivity</code> class controls the user interface of start new trade.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class CounterTradeActivity extends BaseActivity {
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
    String[] ownerDvdNames;

    //选择的dvd的缓存,可以当做intent传给下一个activity
    ArrayList<DVD> ownerDvdBuffer = new ArrayList<>();
    ArrayList<DVD> borrowerDvdBuffer = new ArrayList<>();

    ArrayList<Integer> borrowerDvdSelectedBuffer = new ArrayList<>();
    ArrayList<Integer> ownerDvdSelectedBuffer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trade);

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
                ownerDvdNames = owner.getInventory().getAllNamesFriend();
                // in case choose another owner to trade with, clear all buffer
                ownerDvdSelectedBuffer.clear();
                ownerDvdBuffer.clear();
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
                Log.e("DVD numbers for borrow", String.valueOf(borrowerDvdBuffer.size()));
                Log.e("DVD numbers for owner", String.valueOf(ownerDvdBuffer.size()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_trade, menu);
        return true;
    }

    // open a multiple choice dialog for the borrower
    public void borrowerMultipleChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CounterTradeActivity.this);
        builder.setTitle("Select DVDs");

        // load checked items
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
                }else {// if un-check the item
                    borrowerDvdBuffer.remove(inventoryBorrowerController.getSharableInventory().get(which));
                    borrowerDvdSelectedBuffer.remove(new Integer(which));
                }

                // write the selected dvds to the textview
                textView1.setText("");
                for(int index : borrowerDvdSelectedBuffer)
                {
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

    // open a multiple choice dialog for the borrower
    public void ownerSingleChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CounterTradeActivity.this);
        builder.setTitle("Select DVDs");

        // load checked items
        boolean[] checked = new boolean[owner.getInventory().size()];
        for(int i=0;i<checked.length;i++) {
            if (ownerDvdSelectedBuffer.contains(i)) {
                checked[i] = true;
            } else {
                checked[i] = false;
            }
        }

        builder.setMultiChoiceItems(ownerDvdNames, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) { // if check the item
                    ownerDvdBuffer.add(owner.getInventory().get(which));
                    ownerDvdSelectedBuffer.add(which);
                }else {// if un-check the item
                    ownerDvdBuffer.remove(owner.getInventory().get(which));
                    ownerDvdSelectedBuffer.remove(new Integer(which));
                }

                // write the selected dvds to the textview
                textView2.setText("");
                for(int index : ownerDvdSelectedBuffer)
                {
                    textView2.setText(textView2.getText() + ownerDvdNames[index] + " ; ");
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
