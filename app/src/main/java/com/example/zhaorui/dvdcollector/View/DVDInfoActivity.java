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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Controller.UserHttpClient;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

import java.util.ArrayList;
/**
 * <p>
 * The <code>DVDInfoActivity</code> class controls the user interface of dvd information
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
public class DVDInfoActivity extends BaseActivity {
    /**
     * Initialize an int to store position
     */
    private int position;
    /**
     * Initialize an int to store friend position
     */
    private int friendPosition;
    /**
     * Initialize Inventory Controller
     */
    private InventoryController ic;
    /**
     * Initialize clone button
     */
    private Button btnClone;
    /**
     * Initialize a static string of TAG
     */
    private static String TAG = "DVDInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdinfo);
        ic = new InventoryController();
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        friendPosition = intent.getIntExtra("friendPosition",-1);
        if (friendPosition != -1){
            FriendsController fc = new FriendsController();
            ic.setInventory(fc.get(friendPosition).getInventory());
            Log.e(TAG, "Now it's a dvd of others");
            btnClone = (Button)findViewById(R.id.btn_clone_dvd);
            // color this button
            btnClone.setBackground(getResources().getDrawable(R.drawable.button_shape_indigo));
            btnClone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "Cloning other's DVD");
                    InventoryController inventoryControllerClone = new InventoryController();
                    ArrayList<String> info = ic.getInfo(position);
                    inventoryControllerClone.add(info);

                    // push to the webservice
                    UserHttpClient userHttpClient = new UserHttpClient(new Friend(User.instance()));
                    userHttpClient.runPush();

                    Toast.makeText(DVDInfoActivity.this, "Successfully cloned this dvd to my inventory", Toast.LENGTH_SHORT).show();
                    btnClone.setBackground(getResources().getDrawable(R.drawable.button_shape_grey));
                }
            });

        }
        ArrayList<String> info = (ic.getInfo(position));
        TextView text;
        text = (TextView) findViewById(R.id.tv_category_dvdinfo);
        text.setText(info.get(0));
        text = (TextView) findViewById(R.id.tv_name_dvdinfo);
        text.setText(info.get(1));
        text = (TextView) findViewById(R.id.tv_quantity_dvdinfo);
        text.setText(info.get(2));
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar_info);
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

        text = (TextView) findViewById(R.id.tv_sharable_dvdinfo);
        text.setText(info.get(4));
        text = (TextView) findViewById(R.id.tv_comment_view_dvdinfo);
        text.setText(info.get(5));

    }
    /**
     * To start gallery
     * @param view view variable
     */
    public void onPhotoShow(View view){
        // open the activity to show the list of photos attached to this dvd
        Intent intent = new Intent(DVDInfoActivity.this, PhotoActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("friendPosition",friendPosition);
        startActivity(intent);
    }

}