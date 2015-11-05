package com.example.zhaorui.dvdcollector.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.Model.UserProfile;
import com.example.zhaorui.dvdcollector.R;

import org.w3c.dom.Text;

public class FriendProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        FriendsController fc = new FriendsController();
        int position = getIntent().getIntExtra("position",-1);
        UserProfile profile = fc.get(position).getProfile();
        TextView text;
        text = (TextView) findViewById(R.id.tv_friend_profile_name);
        text.setText(profile.getName());
        text = (TextView) findViewById(R.id.tv_friend_profile_contact);
        text.setText(profile.getContact());
        text = (TextView) findViewById(R.id.tv_friend_profile_city);
        text.setText(profile.getCity());
    }

}
