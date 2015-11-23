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

/*
 * Created by Zhaorui Chen
 * Credit given to https://github.com/CMPUT301W15T06
 *
*/

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.DVDController;
import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.GalleryController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 * The <code>PhotoActivity</code> class controls the the interface that the list of photo of the selected dvd
 * It will call  <code>DisplyPhotoActivity</code> to display the photo if the user select a photo from the list of view
 * It can also take a new photo if the button start camera is pressed.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 4/11/15
 */
public class PhotoActivity extends BaseActivity{
    private int position;
    private InventoryController ic = new InventoryController();
    private DVDController dc = new DVDController();
    private FriendsController fc = new FriendsController();
    private Gallery gallery;
    private GalleryController gc;

    private int numPhotos;
    private ArrayList<Integer> photoIndexes;
    private ArrayAdapter<Integer> photoAdapter;

    private ListView listView;

    private static final int TAKE_PHOTO = 22;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        listView = (ListView)findViewById(R.id.listView_photos);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        int friendPosition = intent.getIntExtra("friendPosition",-1);
        if (friendPosition != -1){
            ic.setInventory(fc.get(friendPosition).getInventory());
            Button upload = (Button) findViewById(R.id.button_upload_photo);
            upload.setVisibility(View.INVISIBLE);
        }
        ArrayList<String> info = dc.read(ic.get(position));//get the current dvd

        // check if this dvd has an non-empty gallery
        if (info.get(4)=="Yes") {
            this.gallery = dc.readPhoto(ic.get(position));//get its gallery
            this.gc = new GalleryController(gallery);
        }else{
            this.gallery = new Gallery();
            this.gc = new GalleryController(gallery);
        }

        // initialize the listview, each entry is provided with an index of image
        numPhotos = gallery.getSize();
        photoIndexes = new ArrayList<Integer>();
        for (int i=0;i<numPhotos;i++){
            photoIndexes.add(i);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // if click on the listview item, show the image on a new activity
                Intent i = new Intent(PhotoActivity.this, DisplayPhotoActivity.class);
                String photoStrToShow = gallery.getPhotoStrs().get(position);
                i.putExtra("photoStr", photoStrToShow);
                startActivity(i);
            }
        });

        // remove the photo
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //int index = photoIndexes.get(position);
                gc.removeFromGallery(gallery.getPhotoStrs().get(position)); // remove the image from the gallery
                dc.changeGallery(ic.get(position), gallery);// change the dvd with the new gallery///////////////////////////////////////////////////

                // update the listview
                photoIndexes.remove(position);
                photoAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        photoAdapter = new ArrayAdapter<Integer>(PhotoActivity.this, android.R.layout.simple_list_item_1, photoIndexes);
        listView.setAdapter(photoAdapter);
        photoAdapter.notifyDataSetChanged();
    }

    //https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptActivity.java
    // Modified: Zhaorui CHEN
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create a path for storing the photograph
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }

        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpeg";
        File imageFile = new File(imageFilePath);
        imageUri = Uri.fromFile(imageFile);

        // start camera
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    //https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptActivity.java
    // Modified: Zhaorui CHEN
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        String photo = gc.encodeFromBitmap(bitmap); // encode image to string
                        gc.addToGallery(photo); // add the photo to the gallery
                        dc.changeGallery(ic.get(position), gallery);// change the dvd with the new gallery

                        // update the listview
                        numPhotos = gallery.getSize();
                        photoIndexes = new ArrayList<Integer>();
                        for (int i=0;i<numPhotos;i++){
                            photoIndexes.add(i);
                        }
                        photoAdapter.notifyDataSetChanged(); //update the listview

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Take photo canceled!", Toast.LENGTH_LONG).show();
                }
        }
    }

}
