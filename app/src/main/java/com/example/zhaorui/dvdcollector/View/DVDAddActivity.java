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

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.DVDController;
import com.example.zhaorui.dvdcollector.Controller.GalleryController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
/**
 * <p>
 * The <code>DVDAddActivity</code> class controls the user interface of adding a new dvd
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class DVDAddActivity extends BaseActivity {
    private DVDController dc = new DVDController();
    private InventoryController ic = new InventoryController();
    private Spinner spinner;
    private int position;

    private Gallery gallery;
    private GalleryController gc;

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

        //if first time add the dvd, show default image
        if(position == -1){
            gallery = new Gallery();
            gc = new GalleryController(gallery);
        }

        if (position != -1){ // if is editing a pre-existed dvd
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

            if(info.get(4)=="Yes"){ // check if this dvd has photos
                gallery = dc.readPhoto(ic.get(position));//get its gallery
                gc = new GalleryController(gallery);
            }else{
                gallery = new Gallery();
                gc = new GalleryController(gallery);
            }

            sharable.setChecked(info.get(5) == "Yes");
            text = (EditText) findViewById(R.id.ed_add_comment);
            text.setText(info.get(6));
        }
   }

    public void onAddSave(View view){
        EditText text;
        text = (EditText) findViewById(R.id.ed_add_name);
        String name = text.getText().toString();
        if (name.isEmpty() | (ic.find(name)&ic.indexOf(name)!=position)){
            FragmentManager fm = getFragmentManager();
            InputInvalidDialog newDialog = new InputInvalidDialog();
            if (name.isEmpty()) newDialog.setText("DVD must has a name!");
            else {
                newDialog.setText("Same name DVD existed!");
            }
            newDialog.show(fm, "abc");
        } else {
            ArrayList<String> info = new ArrayList<String>();
            info.add(spinner.getSelectedItem().toString()); // add category
            info.add(name); // add name 1
            text = (EditText) findViewById(R.id.et_add_quantity);
            info.add(text.getText().toString()); // add quantity
            text = (EditText) findViewById(R.id.et_add_quality);
            info.add(text.getText().toString()); // add quality

            if (gallery.getSize()==0) { // add if the dvd has photos
                info.add("No");
            }else{
                info.add("Yes");
            }

            text = (EditText) findViewById(R.id.ed_add_comment);
            info.add(text.getText().toString()); // add comments

            CheckBox sharable = (CheckBox) findViewById(R.id.checkBox_sharable);
            if (position == -1) {
                ic.add(dc.create(info, sharable.isChecked(),this.gallery));
            } else {
                ic.set(ic.get(position), dc.create(info, sharable.isChecked(), this.gallery)); // create a new dvd or update the dvd
            }
            this.finish();
        }
    }

}
