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

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.TradeHttpClient;
import com.example.zhaorui.dvdcollector.Controller.UserHttpClient;
import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.Friend;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.Model.TradeList;
import com.example.zhaorui.dvdcollector.Model.User;
import com.example.zhaorui.dvdcollector.R;

/**
 * <p>
 * The <code>NameInputDialog</code> class controls the name input dialog.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 27/10/15
 */
public class NameInputDialog extends DialogFragment {
    private View customView;
    private Button ok;
    private EditText editText;
    private EditText editTextEmail;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_name_input_dialog, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        editText = (EditText) customView.findViewById(R.id.editText_name_input_dialog);
        editTextEmail = (EditText) customView.findViewById(R.id.editText_name_input_email_dialog);
        ok = (Button)customView.findViewById(R.id.btn_ok_name_dialog);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String email = editTextEmail.getText().toString();
                FriendsController fc = new FriendsController();
                if (name.isEmpty()||email.isEmpty()){
                    Toast.makeText(ContextUtil.getInstance(), "Name or Email can not be empty!", Toast.LENGTH_LONG).show();
                } else if (fc.nameExist(name)) {
                    Toast.makeText(ContextUtil.getInstance(), "Same Name User Existed!", Toast.LENGTH_LONG).show();
                } else {
                    DataManager.instance().initFile(name, email);
                    dialog.cancel();
                }
            }
        });

        return dialog;
    }




}
