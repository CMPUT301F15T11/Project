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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.DVDController;
import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.Model.Inventory;
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
    private int position;
    private InventoryController ic;
    private DVDController dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvdinfo);

        dc = new DVDController();
        ic = new InventoryController();
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        int friendPosition = intent.getIntExtra("friendPosition",-1);
        if (friendPosition != -1){
            FriendsController fc = new FriendsController();
            ic.setInventory(fc.get(friendPosition).getInventory());
        }
        ArrayList<String> info = dc.read(ic.get(position));
        TextView text;
        text = (TextView) findViewById(R.id.tv_category_dvdinfo);
        text.setText(info.get(0));
        text = (TextView) findViewById(R.id.tv_name_dvdinfo);
        text.setText(info.get(1));
        text = (TextView) findViewById(R.id.tv_quantity_dvdinfo);
        text.setText(info.get(2));
        text = (TextView) findViewById(R.id.tv_quality_dvdinfo);
        text.setText(info.get(3));

        text = (TextView) findViewById(R.id.tv_sharable_dvdinfo);
        text.setText(info.get(5));
        text = (TextView) findViewById(R.id.tv_comment_view_dvdinfo);
        text.setText(info.get(6));

    }

    public void startGallery(View view){
        // open the activity to show the list of photos attached to this dvd
        Intent intent = new Intent(DVDInfoActivity.this, PhotoActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

}