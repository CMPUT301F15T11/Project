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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

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
    private InventoryController ic = new InventoryController();
    private Spinner spinner;
    private int position;

    private RatingBar ratingBar;
    private String ratingStr = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdadd);
        Button show = (Button) findViewById(R.id.btn_show_gallery_add);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DVD.getCategories());
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int r = (int)rating;
                ratingStr = String.valueOf(r);
            }
        });

        if (position != -1){ // if is editing a pre-existed dvd
            ArrayList<String> info = (ic.getInfo(position));
            EditText text;
            spinner.setSelection(DVD.getCategories().indexOf(info.get(0)));
            text = (EditText) findViewById(R.id.ed_add_name);
            text.setText(info.get(1));
            text = (EditText) findViewById(R.id.et_add_quantity);
            text.setText(info.get(2));
            Log.e("DVD", info.get(3));
            switch (info.get(3)){
                case "0":
                    ratingBar.setRating(0);
                    break;
                case "1":
                    ratingBar.setRating(1);
                    break;
                case "2":
                    ratingBar.setRating(2);
                    break;
                case "3":
                    ratingBar.setRating(3);
                    break;
                case "4":
                    ratingBar.setRating(4);
                    break;
                case "5":
                    ratingBar.setRating(5);
                    break;
            }

            CheckBox sharable = (CheckBox) findViewById(R.id.checkBox_sharable);
            sharable.setChecked(info.get(4).equals("Yes"));
            text = (EditText) findViewById(R.id.ed_add_comment);
            text.setText(info.get(5));
        } else {
            show.setVisibility(View.INVISIBLE);
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
            info.add(spinner.getSelectedItem().toString()); // add category 0
            info.add(name); // add name 1
            text = (EditText) findViewById(R.id.et_add_quantity);
            info.add(text.getText().toString()); // add quantity 2
            info.add(ratingStr); // add quality 3
            CheckBox sharable = (CheckBox) findViewById(R.id.checkBox_sharable);
            if(sharable.isChecked()){ // add if sharable 4
                info.add("Yes");
            }else{
                info.add("No");
            }

            text = (EditText) findViewById(R.id.ed_add_comment);
            info.add(text.getText().toString()); // add comments 5

            if (position == -1) {
                ic.add(info);
            } else {
                ic.set(position, info); // create a new dvd or update the dvd
            }
            this.finish();
        }
    }

    public void onAddPhoto(View view){
        // open the activity to show the list of photos attached to this dvd
        Intent intent = new Intent(DVDAddActivity.this, PhotoActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("friendPosition",-1);
        startActivity(intent);
    }

}
