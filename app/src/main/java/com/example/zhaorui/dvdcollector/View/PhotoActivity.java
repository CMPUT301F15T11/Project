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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.GalleryListController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Controller.MyHttpClient;
import com.example.zhaorui.dvdcollector.Model.Gallery;
import com.example.zhaorui.dvdcollector.Model.User;
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
public class PhotoActivity extends BaseActivity {
    private int dvdPosition;
    private InventoryController ic = new InventoryController();
    private FriendsController fc = new FriendsController();

    private Gallery gallery;
    private GalleryListController glc;

    private int numPhotos;
    private ArrayList<Integer> photoIndexes;
    private ArrayAdapter<Integer> photoAdapter;

    private ListView listView;

    private static final int CHOOSE_PHOTO = 11;
    private static final int TAKE_PHOTO = 22;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        listView = (ListView)findViewById(R.id.listView_photos);

        Intent intent = getIntent();
        dvdPosition = intent.getIntExtra("position", -1);
        int friendPosition = intent.getIntExtra("friendPosition", -1);

        if (friendPosition != -1) {
            // if is viewing friend's dvd gallery, disable upload
            ic.setInventory(fc.get(friendPosition).getInventory());
            glc = new GalleryListController(fc.get(friendPosition).getProfile().getName());
            if(User.instance().isDownloadImage()){
                glc.pullGalleryList();
                gallery = glc.get(dvdPosition);
            }

            Button upload = (Button) findViewById(R.id.button_upload_photo);
            upload.setText("Download Photos");
            // enable download photo button when user didn't check for automatically downloading photos
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!User.instance().isDownloadImage()) {
                        glc.pullGalleryList();
                        gallery = glc.get(dvdPosition);

                        // initialize the listview, each entry is provided with an index of image
                        numPhotos = gallery.getSize();
                        photoIndexes = new ArrayList<Integer>();
                        for (int i = 0; i < numPhotos; i++) {
                            photoIndexes.add(i);
                        }
                        photoAdapter = new ArrayAdapter<Integer>(
                                com.example.zhaorui.dvdcollector.View.PhotoActivity.this,
                                android.R.layout.simple_list_item_1, photoIndexes);
                        photoAdapter.notifyDataSetChanged();
                        listView.setAdapter(photoAdapter);
                    }
                }
            });

            // initialize the listview, each entry is provided with an index of image
            if(User.instance().isDownloadImage()) {
                numPhotos = gallery.getSize();
            }else {
                numPhotos = 0;
            }
            photoIndexes = new ArrayList<Integer>();
            for (int i = 0; i < numPhotos; i++) {
                photoIndexes.add(i);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    // if click on the listview item, show the image on a new activity
                    Intent i = new Intent(com.example.zhaorui.dvdcollector.View.PhotoActivity.this, DisplayPhotoActivity.class);
                    String photoStrToShow = gallery.getPhotoStrs().get(position);
                    i.putExtra("photoStr", photoStrToShow);
                    startActivity(i);
                }
            });
        }else {
            //if is viewing my own dvd gallery
            glc = new GalleryListController();
            gallery = glc.get(dvdPosition);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    // if click on the listview item, show the image on a new activity
                    Intent i = new Intent(com.example.zhaorui.dvdcollector.View.PhotoActivity.this, DisplayPhotoActivity.class);
                    String photoStrToShow = gallery.getPhotoStrs().get(position);
                    i.putExtra("photoStr", photoStrToShow);
                    startActivity(i);
                }
            });

            // initialize the listview, each entry is provided with an index of image
            numPhotos = gallery.getSize();
            photoIndexes = new ArrayList<Integer>();
            for (int i = 0; i < numPhotos; i++) {
                photoIndexes.add(i);
            }

            // remove the photo
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //int index = photoIndexes.get(dvdPosition);
                    glc.removePhotoStr(dvdPosition, gallery.getPhotoStrs().get(position)); // remove the image from the gallery
                    //dc.changeGallery(ic.get(photoPosition), gallery);// change the dvd with the new gallery

                    // update the listview
                    photoIndexes.remove(position);
                    photoAdapter.notifyDataSetChanged();

                    return true;
                }
            });
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        photoAdapter = new ArrayAdapter<Integer>(com.example.zhaorui.dvdcollector.View.PhotoActivity.this, android.R.layout.simple_list_item_1, photoIndexes);
        listView.setAdapter(photoAdapter);
        photoAdapter.notifyDataSetChanged();
    }

    //https://github.com/CMPUT301W15T06/Project/blob/master/App/src/ca/ualberta/CMPUT301W15T06/ClaimantReceiptActivity.java
    // Modified: Zhaorui CHEN
    public void takePhoto(View view) {
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
        DataManager.instance().setImgUri(imageUri);

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
                        //imageUri = data.getData();
                        imageUri = DataManager.instance().getImgUri();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        String photo = glc.encodeFromBitmap(bitmap); // encode image to string
                        glc.addPhotoStr(dvdPosition, photo); // add the photo to the gallery
                        //dc.changeGallery(ic.get(dvdPosition), gallery);// change the dvd with the new gallery

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
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.e("Taking Photo", String.valueOf(imageUri));
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        String photo = glc.encodeFromBitmap(bitmap); // encode image to string
                        glc.addPhotoStr(dvdPosition, photo); // add the photo to the gallery
                        //dc.changeGallery(ic.get(dvdPosition), gallery);// change the dvd with the new gallery

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
                    Toast.makeText(getApplicationContext(), "Choose photo canceled!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    //http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }
}
