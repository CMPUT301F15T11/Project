package com.example.zhaorui.dvdcollector.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.R;

public class FriendListActivity extends BaseActivity {
    private String[] data = { "Jack", "Lucy", "Calvin", "Frank", "Mike"};
    Button btnMenuMyInvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendListActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.listViewFriendList);
        listView.setAdapter(adapter);

        // open the menu
        btnMenuMyInvent = (Button)findViewById(R.id.btn_title_my_friends);
        btnMenuMyInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.friendlist_add){
            final EditText editText = new EditText(this);
            final AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Please input a username  ")
                    .setView(editText)
                    .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }
}

