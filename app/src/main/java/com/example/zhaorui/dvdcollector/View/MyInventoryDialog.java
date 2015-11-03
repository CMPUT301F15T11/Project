package com.example.zhaorui.dvdcollector.View;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.Controller.InventoryController;
import com.example.zhaorui.dvdcollector.Model.DVD;
import com.example.zhaorui.dvdcollector.R;

/**
 * Created by teppie on 27/10/15.
 */
public class MyInventoryDialog extends DialogFragment {
    private View customView;
    private Button check;
    private Button edit;
    private Button remove;
    private Context context;
    private int position;
    private ArrayAdapter<?> adapter;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAdapter(ArrayAdapter<?> adapter) {this.adapter = adapter;}

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
                ic.remove(position);
                adapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });

        return dialog;
    }

}
