package com.example.zhaorui.dvdcollector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MyInventActivity extends BaseActivity {
    private String[] data = { "Titanic", "Star war", "The Shawshank Redemption", "The God father",
            "The Dark Knight", "12 Angry Man", "Schindler's List", "Pulp Fiction", "The Lord of Rings", "Forrest Gump",
            "Inception", "The matrix"};
    //http://stackoverflow.com/questions/18913635/how-to-trigger-a-menu-button-click-event-through-code-in-android
    Button btnMenuMyInvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invent);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyInventActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.listViewMyInventory);
        listView.setAdapter(adapter);

        // open the menu
        btnMenuMyInvent = (Button)findViewById(R.id.btn_title_my_inventory);
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
        getMenuInflater().inflate(R.menu.menu_my_invent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_to_my_invent) {
            Intent i = new Intent(MyInventActivity.this, DVDAddActivity.class);
            startActivity(i);
        }

        if (id == R.id.browse_my_invent) {
            Intent i = new Intent(MyInventActivity.this, BrowseInventActivity.class);
            startActivity(i);
        }

        if (id == R.id.search_my_invent){
            final EditText editText = new EditText(this);
            final AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Name of the book?  ")
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

