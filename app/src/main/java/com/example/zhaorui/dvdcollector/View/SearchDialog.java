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

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.R;

/**
 * <p>
 * The <code>SearchDialog</code> class controls the search dialog.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 27/10/15
 */
public class SearchDialog extends DialogFragment {
    private View customView;
    private Button search;
    private EditText editText;
    private Context context;
    private InventoryController ic;
    private FriendsController fc;
    private String mode;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setIc(InventoryController ic) {
        this.ic = ic;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_search_dialog, null);
        context = getActivity();
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        fc = new FriendsController();
        if (ic == null){ic = new InventoryController();}
        editText = (EditText) customView.findViewById(R.id.editText_search_dialog);

        search = (Button)customView.findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(ContextUtil.getInstance(), "Empty Input!", Toast.LENGTH_LONG).show();
                } else if (mode == "inventory") {
                    if (ic.find(name)) {
                        Intent intent = new Intent(context, DVDInfoActivity.class);
                        intent.putExtra("position", ic.indexOf(name));
                        startActivity(intent);
                    } else {
                        FragmentManager fm = getFragmentManager();
                        InputInvalidDialog newDialog = new InputInvalidDialog();
                        newDialog.setText("No result found!");
                        newDialog.show(fm, "abc");
                    }
                    dialog.cancel();
                } else {
                    if (fc.getFriends().contains(name)){
                        FragmentManager fm = getFragmentManager();
                        InputInvalidDialog newDialog = new InputInvalidDialog();
                        newDialog.setText(name + " is already your friend!");
                        newDialog.show(fm, "abc");
                    } else if (fc.nameExist(name)){
                        fc.add(name);
                    } else {
                        FragmentManager fm = getFragmentManager();
                        InputInvalidDialog newDialog = new InputInvalidDialog();
                        newDialog.setText(name + " is not a user!");
                        newDialog.show(fm, "abc");
                    }
                    dialog.cancel();
                }
            }
        });

        return dialog;
    }

}
