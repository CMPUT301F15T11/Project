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
import com.example.zhaorui.dvdcollector.Controller.TradeHttpClient;
import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Friends;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

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
public class StartTradeActivity extends BaseActivity implements Observer {
    /**
     * Initialize linear layout ll1
     */
    private LinearLayout ll1;
    /**
     * Initialize linear layout ll2
     */
    private LinearLayout ll2;
    /**
     * Initialize Spinner
     */
    private Spinner spinner;
    /**
     * Initialize Send Request button
     */
    private Button btnSendRequest;
    /**
     * Initialize TextView textView1
     */
    private TextView textView1;
    /**
     * Initialize TextView textView2
     */
    private TextView textView2;
    /**
     * Initialize Friends Controller
     */
    private FriendsController friendsController = new FriendsController();
    /**
     * Initialize Inventory controller for borrower
     */
    //Inventory controller for borrower, in this case-->Device user
    private InventoryController inventoryBorrowerController = new InventoryController();
    String[] borrowerDvdNames = inventoryBorrowerController.getAllNames();
    /**
     * Initialize Inventory controller for owner
     */
    //Inventory controller for owner, in this case-->One friend
    private InventoryController inventoryOwnerController = new InventoryController();
    String[] ownerDvdNames;

    //Buffer for selecting dvds in the checkbox and radiobutton
    ArrayList<String> borrowerDvdNameBuffer = new ArrayList<>();
    String ownerDvdNameBuffer = null;
    ArrayList<Integer> borrowerDvdIndexBuffer = new ArrayList<>();
    /**
     * Initialize TradeList Controller
     */
    private TradeListController tradeListController = new TradeListController(User.instance().getTradeList());
    /**
     * Initialize static string TAG
     */
    private static String TAG = "StartTradeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trade);
        textView1 = (TextView)findViewById(R.id.tv_listing_names_borrower_dvd);
        textView2 = (TextView)findViewById(R.id.tv_listing_names_onwer_dvd);
        ll1 = (LinearLayout)findViewById(R.id.ll_add_borrower_dvd_new_trade);
        ll2 = (LinearLayout)findViewById(R.id.ll_add_owner_dvd_new_trade);

        Log.e(TAG, String.valueOf(inventoryBorrowerController.getSharableInventory().size()));

        // click to add dvds from borrower's inventory
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowerMultipleChoiceDialog();
            }
        });

        // check to select a friend to trade with
        spinner = (Spinner)findViewById(R.id.spinner_choose_owner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, friendsController.getFriends());
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                inventoryOwnerController.setInventory(friendsController.getByName
                        (spinner.getSelectedItem().toString()).getInventory());
                ownerDvdNames = inventoryOwnerController.getAllNamesFriend();

                // if user switch to another owner to trade with, clear all selected buffer
                ownerDvdNameBuffer = null;
                // clear the textview
                textView2.setText("");

                ll2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if (owner != null) { // if have selected a friend to trade with
                            ownerSingleChoiceDialog();
                        //}
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
                if (!ContextUtil.getInstance().isConnected()){
                    Toast.makeText(ContextUtil.getInstance(), "Not Connect to Internet!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ownerDvdNameBuffer != null) {
                    borrowerDvdNameBuffer.clear();
                    for(int i:borrowerDvdIndexBuffer) {
                        borrowerDvdNameBuffer.add(inventoryBorrowerController.getSharableInventory().get(i).getName());
                    }
                    String tradeId = String.valueOf(System.currentTimeMillis());
                    // add this trade to borrower's tradelist
                    tradeListController.addTrade(User.instance().getProfile().getName(),
                            friendsController.getByName(spinner.getSelectedItem().toString()).getProfile().getName(),
                            borrowerDvdNameBuffer,
                            ownerDvdNameBuffer,
                            "Current Outgoing",
                            "Pending",
                            tradeId);

                    //send trade to the owner by adding this trade to his tradelist, see sendTrade() for details
                    tradeListController.sendTrade(
                            friendsController.getByName(spinner.getSelectedItem().toString()).getProfile().getName(),
                            borrowerDvdNameBuffer,
                            ownerDvdNameBuffer,
                            tradeId);

                    Toast.makeText(StartTradeActivity.this, "Trade has been sent to the owner", Toast.LENGTH_SHORT).show();
                    StartTradeActivity.this.finish();
                } else {
                    //if haven't chosen any dvd from owner's tradelist
                    showPromptDialog();
                }
            }
        });

        tradeListController.addObserver(this);
        inventoryOwnerController.addObserver(this);
        inventoryBorrowerController.addObserver(this);
    }
    /**
     * Send email during trade
     * @param emailAddress string variable
     * @param ownerComments string variable
     */
    private void sendEmail(String emailAddress, String ownerComments){
        Intent stats = new Intent(Intent.ACTION_SENDTO);
        stats.setData(Uri.parse("mailto:" + emailAddress));
        stats.putExtra(Intent.EXTRA_SUBJECT, "Trade Details");

        String content = "Trade Items from owner: "+ String.valueOf(ownerDvdNames)
                                + "\n Trade Items from borrower: "+String.valueOf(borrowerDvdNames)
                                + "\n Owner's comments: " + ownerComments
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
    /**
     * Prompt alert if user haven't chosen a dvd from the owner
     */
    private void showPromptDialog() {
        FragmentManager fm = getFragmentManager();
        TradeRequestInvalidDialog newDialog = new TradeRequestInvalidDialog();
        newDialog.show(fm, "abc");
    }

    // open a multiple choice dialog for the borrower
    // borrower is always the device user in this activity
    /**
     * Open a multiple choice dialog for the borrower
     */
    public void borrowerMultipleChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartTradeActivity.this);
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
    /**
     * Open a single choice dialog for the owner
     */
    public void ownerSingleChoiceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartTradeActivity.this);
        builder.setTitle("Select one DVD");

        int checked = 0;
        if (ownerDvdNameBuffer !=null){ // if have selected a dvd before
            for (int i=0;i<ownerDvdNames.length;i++){
                if(ownerDvdNames[i].equals(ownerDvdNameBuffer)){
                    checked = i;
                }
            }
        }else{ // if it's the first time selecting owner's dvd
            try {
                ownerDvdNameBuffer = inventoryOwnerController.get(checked).getName();
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
                ownerDvdNameBuffer = null;
            }
        }

        builder.setSingleChoiceItems(ownerDvdNames, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ownerDvdNameBuffer = inventoryOwnerController.get(which).getName();
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
    /**
     * Update observer
     * @param ob Observable variable
     * @param o Object variable
     */
    public void update(Observable ob,Object o){
    }
}
