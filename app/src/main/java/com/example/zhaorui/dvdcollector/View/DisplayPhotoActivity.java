package com.example.zhaorui.dvdcollector.View;

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
