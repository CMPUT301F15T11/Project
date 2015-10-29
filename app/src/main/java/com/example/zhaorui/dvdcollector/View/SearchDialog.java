package com.example.zhaorui.dvdcollector.View;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhaorui.dvdcollector.R;

/**
 * Created by teppie on 27/10/15.
 */
public class SearchDialog extends DialogFragment {
    private View customView;
    private Button search;
    private EditText editText;
    private Context context;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_search_dialog, null);
        context = getActivity();
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        search = (Button)customView.findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DVDInfoActivity.class); // lead to this dvd's info page
                startActivity(intent);
                // cathch exception if no dvd if found ?
                dialog.cancel();
            }
        });

        return dialog;
    }

}
