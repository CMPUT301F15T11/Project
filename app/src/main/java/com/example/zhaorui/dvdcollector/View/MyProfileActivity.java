package com.example.zhaorui.dvdcollector.View;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.Model.UserProfile;
import com.example.zhaorui.dvdcollector.R;

public class MyProfileActivity extends BaseActivity {
    private EditText contact;
    private EditText city;
    private UserProfile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        TextView name = (TextView) findViewById(R.id.name_my_profile);
        profile = User.instance().getProfile();
        name.setText(profile.getName());
        contact = (EditText) findViewById(R.id.contact_my_profile);
        city = (EditText) findViewById(R.id.city_my_profile);
        contact.setText(profile.getContact());
        city.setText(profile.getCity());
    }

    public void onProfileSave(View view){
        profile.setCity(city.getText().toString());
        profile.setContact(contact.getText().toString());
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
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
}
