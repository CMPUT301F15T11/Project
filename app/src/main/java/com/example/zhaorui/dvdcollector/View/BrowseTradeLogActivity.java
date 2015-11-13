package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;
import java.util.List;

public class BrowseTradeLogActivity extends BaseActivity {
    private Button btnCurrentIncoming;
    private Button btnCurrentOutgoing;
    private Button btnPastIncoming;
    private Button btnPastOutgoing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_trade_log);

        btnCurrentIncoming = (Button)findViewById(R.id.btn_browse_trades_current_incoming);
        btnCurrentOutgoing = (Button)findViewById(R.id.btn_browse_trades_current_outgoing);
        btnPastIncoming = (Button)findViewById(R.id.btn_browse_trades_past_incoming);
        btnPastOutgoing = (Button)findViewById(R.id.btn_browse_trades_past_outgoing);

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

    }

    public void startSpecificTradesLog(View view, int mode){
        Intent i = new Intent(BrowseTradeLogActivity.this, TradesLogActivity.class);
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

        }
    }
}
