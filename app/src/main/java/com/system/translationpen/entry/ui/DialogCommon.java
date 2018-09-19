package com.system.translationpen.entry.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.translationpen.R;

/**
 * Created by huison on 2018/7/1.
 */

public class DialogCommon extends AlertDialog {

    public DialogCommon(Context context) {
        super(context, R.style.DialogTheme);
    }

    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_common, null);
        setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setCanceledOnTouchOutside(true);

        titleView = (TextView) v.findViewById(R.id.dialog_tv_title);

        findViewById(R.id.dialog_tv_known).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
