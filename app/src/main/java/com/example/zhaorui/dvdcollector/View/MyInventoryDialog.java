
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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

/**
 * <p>
 * The <code>MyInventoryDialog</code> class controls the user interface of dialog in my inventory
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 27/10/15
 */
public class MyInventoryDialog extends DialogFragment {
    /**
     * Initialize view of custom view
     */
    private View customView;
    /**
     * Initialize check button
     */
    private Button check;
    /**
     * Initialize edit button
     */
    private Button edit;
    /**
     * Initialize remove button
     */
    private Button remove;
    /**
     * Initialize context
     */
    private Context context;
    /**
     * Initialize an int to store position
     */
    private int position;
    /**
     * Initialize an int to store position
     * @param position int variable
     */
    public void setPosition(int position) {
        this.position = position;
    }
    /**
     * Check inventory by press button
     * @return check
     */
    public Button getCheck() {
        return check;
    }
    /**
     * Edit inventory by press button
     * @return edit
     */
    public Button getEdit() {
        return edit;
    }
    /**
     * Remove inventory by press button
     * @return remove
     */
    public Button getRemove() {
        return remove;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_my_inventory_dialog, null);
        context = getActivity();
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        check = (Button)customView.findViewById(R.id.dialog_function_1);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DVDInfoActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                dialog.cancel();
            }
        });

        edit = (Button)customView.findViewById(R.id.dialog_function_2);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DVDAddActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                dialog.cancel();
            }
        });

        remove = (Button)customView.findViewById(R.id.dialog_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryController ic = new InventoryController();
                ic.remove(ic.get(position));
                dialog.cancel();
            }
        });

        return dialog;
    }

}
