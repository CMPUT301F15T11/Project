package com.example.zhaorui.dvdcollector.View;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.FriendsController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

/**
 * Created by teppie on 27/10/15.
 */
public class FriendListDialog extends DialogFragment {
    private View customView;
    private Button check;
    private Button checkInventory;
    private Button remove;
    private Context context;
    private FriendsController fc;
    private int position;
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_friendlist_dialog, null);
        context = getActivity();
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        fc = new FriendsController();

        check = (Button)customView.findViewById(R.id.dialog_function_4);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendProfileActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                dialog.cancel();
            }
        });

        checkInventory = (Button)customView.findViewById(R.id.dialog_function_5);
        checkInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendInventoryActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                dialog.cancel();
            }
        });

        remove = (Button)customView.findViewById(R.id.dialog_remove2);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.remove(position);
                dialog.cancel();
            }
        });

        return dialog;
    }

}
