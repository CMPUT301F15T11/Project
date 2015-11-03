package com.example.zhaorui.dvdcollector.View;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        // click on a item from the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // todo: retrieve the item which has been clicked

                // open up a dialog (should with a parameter )
                showDialog();

                //now check user choose which action
                //see MyInventoryDialog.java for implementation
            }
        });
    }

    //http://stackoverflow.com/questions/17287054/dialogfragment-without-fragmentactivity
    //// TODO: 27/10/15 should take parameter of type DVD
    public void showDialog() {
        FragmentManager fm = getFragmentManager();
        FriendListDialog newDialog = (FriendListDialog) new FriendListDialog();
        newDialog.show(fm, "abc");
    }

    public void showSearchDialog() {
        FragmentManager fm = getFragmentManager();
        SearchDialog newDialog = (SearchDialog) new SearchDialog();
        newDialog.show(fm, "abc");
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
            showSearchDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}

