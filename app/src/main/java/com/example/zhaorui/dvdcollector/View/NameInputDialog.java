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
import com.example.zhaorui.dvdcollector.Model.ContextUtil;
import com.example.zhaorui.dvdcollector.Model.Inventory;
import com.example.zhaorui.dvdcollector.R;

/**
 * Created by teppie on 27/10/15.
 */
public class NameInputDialog extends DialogFragment {
    private View customView;
    private Button ok;
    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_name_input_dialog, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        editText = (EditText) customView.findViewById(R.id.editText_name_input_dialog);
        ok = (Button)customView.findViewById(R.id.btn_ok_name_dialog);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                FriendsController fc = new FriendsController();
                if (name.isEmpty()){
                    Toast.makeText(ContextUtil.getInstance(), "Name can not be empty!", Toast.LENGTH_LONG).show();
                } else if (fc.nameExist(name)) {
                    Toast.makeText(ContextUtil.getInstance(), "Same name user existed!", Toast.LENGTH_LONG).show();
                } else {
                    DataManager.instance().initFile(name);
                    dialog.cancel();
                }
            }
        });

        return dialog;
    }


}
