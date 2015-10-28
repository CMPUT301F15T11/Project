package com.example.zhaorui.dvdcollector.View;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

public class MyInventActivity extends BaseActivity {
    
    // sample data
    // TODO: 27/10/15 Delete these testing sample data 
    private String[] data = { "Titanic", "Star war", "The Shawshank Redemption", "The God father",
            "The Dark Knight", "12 Angry Man", "Schindler's List", "Pulp Fiction", "The Lord of Rings", "Forrest Gump",
            "Inception", "The matrix"};
    //http://stackoverflow.com/questions/18913635/how-to-trigger-a-menu-button-click-event-through-code-in-android
    Button btnMenuMyInvent;
    ListView listView;
    ListViewDialog dialog;
    Button check;
    Button edit;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invent);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyInventActivity.this, android.R.layout.simple_list_item_1, data);
        listView = (ListView) findViewById(R.id.listViewMyInventory);
        listView.setAdapter(adapter);
        check = (Button)findViewById(R.id.dialog_function_1);
        edit = (Button)findViewById(R.id.dialog_function_2);
        remove = (Button)findViewById(R.id.dialog_remove);

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
                // retrieve the item which has been clicked
                //DVD dvd = (DVD) parent.getItemAtPosition(position);
                Log.e("HEHE","Yes111111");
                // open up a dialog
                showDialog();
            }
        });

        // click the check button of the dialog
/*        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInventActivity.this, DVDInfoActivity.class);
                startActivity(intent);
            }
        });*/
    }

    //http://stackoverflow.com/questions/17287054/dialogfragment-without-fragmentactivity
    public void showDialog() {
        FragmentManager fm = getFragmentManager();
        ListViewDialog newDialog = (ListViewDialog) new ListViewDialog();
        Log.e("HEHE","Yes");
        newDialog.show(fm, "abc");
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

