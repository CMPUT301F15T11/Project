package com.example.zhaorui.dvdcollector.View;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

public class MyInventoryActivity extends BaseActivity {
    
    // sample data
    // TODO: 27/10/15 Delete these testing sample data 
    private String[] data = { "Titanic", "Star war", "The Shawshank Redemption", "The God father",
            "The Dark Knight", "12 Angry Man", "Schindler's List", "Pulp Fiction", "The Lord of Rings", "Forrest Gump",
            "Inception", "The matrix"};

    //http://stackoverflow.com/questions/18913635/how-to-trigger-a-menu-button-click-event-through-code-in-android
    Button btnMenuMyInvent;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inventory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyInventoryActivity.this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.listViewMyInventory);
        listView.setAdapter(adapter);

        // open the menu
        btnMenuMyInvent = (Button)findViewById(R.id.btn_title_my_inventory);
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
                //sample
                DVD dvd= new DVD("Team 11");

                // open up a dialog (should with a parameter )
                showDialog(dvd);

                //now check user choose which action
                //see MyInventoryDialog.java for implementation
            }
        });
    }

    //http://stackoverflow.com/questions/17287054/dialogfragment-without-fragmentactivity
    //// TODO: 27/10/15 should take parameter of type DVD
    private void showDialog(DVD dvd) {
        FragmentManager fm = getFragmentManager();
        MyInventoryDialog newDialog = (MyInventoryDialog) new MyInventoryDialog();
        newDialog.setDvd(dvd);//sample
        newDialog.show(fm, "abc");
    }

    private void showSearchDialog() {
        FragmentManager fm = getFragmentManager();
        SearchDialog newDialog = (SearchDialog) new SearchDialog();
        newDialog.show(fm, "abc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_to_my_inventory) {
            Intent i = new Intent(MyInventoryActivity.this, DVDAddActivity.class);
            startActivity(i);
        }

        if (id == R.id.browse_my_inventory) {
            Intent i = new Intent(MyInventoryActivity.this, BrowseInventActivity.class);
            startActivity(i);
        }

        if (id == R.id.search_my_inventory){
            showSearchDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}

