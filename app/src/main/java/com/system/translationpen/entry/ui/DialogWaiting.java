package com.system.translationpen.entry.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.system.translationpen.R;

/**
 * Created by huison on 2018/6/18.
 */

public class DialogWaiting extends AlertDialog {

    public DialogWaiting(Context context) {
        super(context, R.style.DialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_waiting, null);
        rotateView = v.findViewById(R.id.iv_loading);
        setContentView(v, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        setCanceledOnTouchOutside(false);

        animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setFillAfter(true);
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(1000);
    }

    private View rotateView;
    private RotateAnimation animation;

    @Override
    public void onStart() {
        rotateView.startAnimation(animation);
        super.onStart();
    }

    @Override
    protected void onStop() {
        rotateView.clearAnimation();
        super.onStop();
    }
}
