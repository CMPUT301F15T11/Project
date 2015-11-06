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
/**
 * <p>
 * The <code>DisplayPhotoActivity</code> class controls the interface that displays the selected photo.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 4/11/15
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.zhaorui.dvdcollector.R;

public class DisplayPhotoActivity extends BaseActivity {

    private ImageView imageView;
    private String photoStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        imageView = (ImageView)findViewById(R.id.imageView_display_photo);

        Intent intent = getIntent();
        photoStr = intent.getStringExtra("photoStr");

        // show the image
        byte[] byteArray = Base64.decode(photoStr, Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bm);
    }


}
