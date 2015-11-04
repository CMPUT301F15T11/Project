package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.DVDController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Controller.PhotoController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.Model.Photo;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.io.File;
import java.util.ArrayList;

public class DVDAddActivity extends BaseActivity {
    private DVDController dc = new DVDController();
    private InventoryController ic = new InventoryController();
    private Spinner spinner;
    private int position;

    private static final int ADD_PHOTO_RQ = 22;
    private Uri imageFileUri;
    private PhotoController pc=null;
    private Photo photo = null;
    private ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdadd);


        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dc.categories());
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        //photo = User.getInstance().getCurrentItem().getRecipt();
        //photo =
        //pc = new PhotoController(photo);
        imageView = (ImageView)findViewById(R.id.imgv_add_dvd);

        if (position != -1){
            ArrayList<String> info = dc.read(ic.get(position));
            EditText text;
            spinner.setSelection(dc.categories().indexOf(info.get(0)));
            text = (EditText) findViewById(R.id.ed_add_name);
            text.setText(info.get(1));
            text = (EditText) findViewById(R.id.et_add_quantity);
            text.setText(info.get(2));
            text = (EditText) findViewById(R.id.et_add_quality);
            text.setText(info.get(3));
            CheckBox sharable = (CheckBox) findViewById(R.id.checkBox_sharable);
            sharable.setChecked(info.get(4) == "Yes");
            text = (EditText) findViewById(R.id.ed_add_comment);
            text.setText(info.get(5));
        }
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dvdadd, menu);
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

    public void onAddSave(View view){
        EditText text;
        ArrayList<String> info = new ArrayList<String>();
        info.add(spinner.getSelectedItem().toString());
        text = (EditText) findViewById(R.id.ed_add_name);
        info.add(text.getText().toString());
        text = (EditText) findViewById(R.id.et_add_quantity);
        info.add(text.getText().toString());
        text = (EditText) findViewById(R.id.et_add_quality);
        info.add(text.getText().toString());
        text = (EditText) findViewById(R.id.ed_add_comment);
        info.add(text.getText().toString());
        CheckBox sharable = (CheckBox) findViewById(R.id.checkBox_sharable);
        if (position == -1) {
            ic.add(dc.create(info, sharable.isChecked()));
        } else {
            ic.set(ic.get(position), dc.create(info, sharable.isChecked()));
        }
        this.finish();
    }

    public void addPhoto(MenuItem m) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
            File folderF = new File(folder);
            if (!folderF.exists()) {
                folderF.mkdir();
            }

            String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpeg";
            File imageFile = new File(imageFilePath);
            imageFileUri = Uri.fromFile(imageFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(intent,ADD_PHOTO_RQ);
    }

}
