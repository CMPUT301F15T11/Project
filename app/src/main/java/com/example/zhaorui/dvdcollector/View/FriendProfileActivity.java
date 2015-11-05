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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.Model.UserProfile;
import com.example.zhaorui.dvdcollector.R;

import org.w3c.dom.Text;
/**
 * <p>
 * The <code>FriendProfileActivity</code> class controls the user interface of Friend's Profile.
 * This class contains functions onCreate
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 11/10/15
 */
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
