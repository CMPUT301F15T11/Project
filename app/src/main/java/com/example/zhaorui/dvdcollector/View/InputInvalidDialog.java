package com.example.zhaorui.dvdcollector.View;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhaorui.dvdcollector.R;

/**
 * Created by teppie on 27/10/15.
 */
public class InputInvalidDialog extends DialogFragment {
    private View customView;
    private Button ok;
    private Context context;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        customView = getActivity().getLayoutInflater().inflate(R.layout.layout_search_not_found_dialog, null);
        context = getActivity();
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(customView);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ok = (Button)customView.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cathch exception if no dvd if found
                dialog.cancel();
            }
        });

        //new dialog

        return dialog;
    }
}
