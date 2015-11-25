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
import com.example.zhaorui.dvdcollector.Controller.MyHttpClient;
import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.Trade;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;
import com.google.gson.Gson;

import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * The <code>StartTradeActivity</code> class controls the user interface of start new trade.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class CounterTradeActivity extends BaseActivity implements Observer {
    private TextView textView1;
    private TextView textView2;

    private FriendsController fc = new FriendsController();

    //Inventory controller for borrower, in this case-->Device user
    private InventoryController inventoryOwnerController;
    String[] ownerDvdNames;

    //Inventory controller for owner, in this case-->One friend(This friend is fixed)
    private InventoryController inventoryBorrowerController;
    String[] borrowerDvdNames;

    //Buffer for selecting dvds in the checkbox and radiobutton
    ArrayList<String> borrowerDvdNameBuffer = new ArrayList<>();
    String ownerDvdNameBuffer = null;
    ArrayList<Integer> borrowerDvdIndexBuffer = new ArrayList<>();

    //TradeList Controller for user(owner)
    private TradeListController tradeListController = new TradeListController(User.instance().getTradeList());

    private static String TAG = "CounterTradeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_trade);
        TextView textView = (TextView)findViewById(R.id.tv_counter_trade_borrower);
        textView1 = (TextView)findViewById(R.id.tv_listing_names_borrower_dvd);
        textView2 = (TextView)findViewById(R.id.tv_listing_names_onwer_dvd);
        LinearLayout ll1 = (LinearLayout)findViewById(R.id.ll_add_borrower_dvd_new_trade);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.ll_add_owner_dvd_new_trade);

        Intent i = getIntent();
        String tradeStr = i.getStringExtra("trade");
        Gson gson = new Gson();
        // TODO: Need to avoid using model instance?
        Trade trade = gson.fromJson(tradeStr,Trade.class);
        final String friendName = trade.getBorrower();

        inventoryBorrowerController = new InventoryController();
        inventoryBorrowerController.setInventory(fc.getByName(friendName).getInventory());
        borrowerDvdNames = inventoryBorrowerController.getAllNames();

        //get owner's information
        //ownerHttpClient = new MyHttpClient(trade.getOwner());
        inventoryOwnerController = new InventoryController();
        ownerDvdNames = inventoryOwnerController.getAllNames();

        // set default info with the declined trade
        textView.setText(trade.getBorrower());
        textView2.setText(trade.getOwnerItem());
        for (String dvd:trade.getBorrowerItemList()){
            textView1.setText(textView1.getText()+"; "+dvd);
            borrowerDvdIndexBuffer.add(inventoryBorrowerController.indexOf(dvd));
        }
        textView1.setText(String.valueOf(trade.getBorrowerItemList()));
        ownerDvdNameBuffer = trade.getOwnerItem();
        borrowerDvdNameBuffer = trade.getBorrowerItemList();

        // click to add dvds from borrower's inventory
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowerMultipleChoiceDialog();
            }
        });

        Button btnSendRequest = (Button)findViewById(R.id.btn_send_trade_request);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownerDvdNameBuffer !=null) {
                    borrowerDvdNameBuffer.clear();
                    for(int i:borrowerDvdIndexBuffer) {
                        borrowerDvdNameBuffer.add(inventoryBorrowerController.getSharableInventory().get(i).getName());
                    }
                    Log.e(TAG, String.valueOf(borrowerDvdNameBuffer));
                    Log.e(TAG, String.valueOf(borrowerDvdIndexBuffer));
                    String tradeId =  String.valueOf(System.currentTimeMillis());// giving a new ID for the counter trade
                    // add this trade to borrower's tradelist
                    //NOTE: Upon sending the counter trade, the role of borrower and owner switched
                    //Owner in the counter trade --> Borrower of the new trade request
                    //vice versa.
                    tradeListController.addTrade(
                            User.instance().getProfile().getName(),//Now, treat user as borrower
                            fc.getByName(friendName).getProfile().getName(),// treat friend as owner
                            borrowerDvdNameBuffer,
                            ownerDvdNameBuffer,
                            "Current Outgoing",
                            "Pending",
                            tradeId);// save to user's tradelist

                    //send trade to the owner(now--> friend)
                    tradeListController.sendCounterTrade(User.instance().getProfile().getName(),
                            fc.getByName(friendName).getProfile().getName(),
                            borrowerDvdNameBuffer, ownerDvdNameBuffer, tradeId);

                    Toast.makeText(CounterTradeActivity.this, "Trade has been sent to the borrower", Toast.LENGTH_SHORT).show();
                    CounterTradeActivity.this.finish();
                }else{
                    showPromptDialog();
                }
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ownerSingleChoiceDialog();
            }
        });

        tradeListController.addObserver(this);
        inventoryOwnerController.addObserver(this);
        inventoryBorrowerController.addObserver(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CounterTradeActivity.this);
        builder.setTitle("Select DVDs");

        // load already checked items
        boolean[] checked = new boolean[inventoryBorrowerController.getSharableInventory().size()];
        for(int i=0;i<checked.length;i++) {
            if (borrowerDvdIndexBuffer.contains(i)) {
                checked[i] = true;
            } else {
                checked[i] = false;
            }
        }

        builder.setMultiChoiceItems(borrowerDvdNames, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) { // if check the item
                    borrowerDvdIndexBuffer.add(which);
                } else {// if un-check the item
                    borrowerDvdIndexBuffer.remove(Integer.valueOf(which));
                }

                // write the selected dvds to the textview
                textView1.setText("");
                for (int index : borrowerDvdIndexBuffer) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CounterTradeActivity.this);
        builder.setTitle("Select one DVD");

        int checked = 0;
        if (ownerDvdNameBuffer !=null){ // if have selected a dvd before
            for (int i=0;i<ownerDvdNames.length;i++){
                if(ownerDvdNames[i].equals(ownerDvdNameBuffer)){
                    checked = i;
                }
            }
        } else{ // if it's the first time selecting owner's dvd
            try {
                ownerDvdNameBuffer = inventoryOwnerController.getSharableInventory().get(checked).getName();
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
                ownerDvdNameBuffer = null;
            }
        }

        builder.setSingleChoiceItems(ownerDvdNames, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ownerDvdNameBuffer = inventoryOwnerController.getInventory().get(which).getName();
            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // write the selected dvd to the textview
                textView2.setText(ownerDvdNameBuffer);

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
