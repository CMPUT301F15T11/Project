package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

public class BrowseTradeLogActivity extends BaseActivity {
    /**
     * Initialize Current Incoming button
     */
    private Button btnCurrentIncoming;
    /**
     * Initialize current Outgoing button
     */
    private Button btnCurrentOutgoing;
    /**
     * Initialize Past Incoming button
     */
    private Button btnPastIncoming;
    /**
     * Initialize Past Outgoing button
     */
    private Button btnPastOutgoing;
    /**
     * Initialize In Progress button
     */
    private Button btnInProgress;
    /**
     * Initialize Complete button
     */
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_trade_log);

        btnCurrentIncoming = (Button)findViewById(R.id.btn_browse_trades_current_incoming);
        btnCurrentOutgoing = (Button)findViewById(R.id.btn_browse_trades_current_outgoing);
        btnPastIncoming = (Button)findViewById(R.id.btn_browse_trades_past_incoming);
        btnPastOutgoing = (Button)findViewById(R.id.btn_browse_trades_past_outgoing);
        btnInProgress = (Button)findViewById(R.id.btn_browse_trades_in_progress);
        btnComplete = (Button)findViewById(R.id.btn_browse_trades_completed);

        btnCurrentIncoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificTradesLog(btnCurrentIncoming,1);
            }
        });

        btnCurrentOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificTradesLog(btnCurrentOutgoing,2);
            }
        });

        btnPastIncoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificTradesLog(btnPastIncoming,3);
            }
        });

        btnPastOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificTradesLog(btnPastOutgoing,4);
            }
        });

        btnInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificTradesLog(btnInProgress,5);
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificTradesLog(btnComplete,6);
            }
        });

        TradeListController tradeListController = new TradeListController(User.instance().getTradeList());
        tradeListController.updateTradeList(User.instance().getProfile().getName());
    }

    /**
     *To push specific trade log
     * @param view view variable view
     * @param mode in variable mode
     */
    public void startSpecificTradesLog(View view, int mode){
        Intent i = new Intent(BrowseTradeLogActivity.this, TradesLogTypeActivity.class);
        switch (mode){
            case 1: //current incoming trade
                i.putExtra("type", "Current Incoming".toString());
                startActivity(i);
                break;
            case 2:// current outgoing trade
                i.putExtra("type", "Current Outgoing".toString());
                startActivity(i);
                break;
            case 3://past incoming trade
                i.putExtra("type", "Past Incoming".toString());
                startActivity(i);
                break;
            case 4:// past outgoing trade
                i.putExtra("type", "Past Outgoing".toString());
                startActivity(i);
                break;
            case 5:
                i = new Intent(BrowseTradeLogActivity.this, TradesLogStatusActivity.class);
                i.putExtra("status", "In-progress");
                startActivity(i);
                break;
            case 6:
                i = new Intent(BrowseTradeLogActivity.this, TradesLogStatusActivity.class);
                i.putExtra("status","Complete");
                startActivity(i);
                break;
        }
    }
}
