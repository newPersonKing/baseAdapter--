package com.system.translationpen.entry;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.entry.ui.DialogWaiting;

/**
 * Created by huison on 2018/6/13.
 */

public class ActivityBase extends FragmentActivity {

    public static void open(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    private FrameLayout rootView;
    private ImageButton backView;
    private TextView titleView;

    private DialogWaiting dialogWaiting;

    protected boolean hasNavigationBar() {
        return true;
    }

    protected boolean hasBackView() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rootView = (FrameLayout) findViewById(android.R.id.content);
        LayoutInflater inflater = LayoutInflater.from(this);


        View toolBar = inflater.inflate(R.layout.layout_action_bar, null);
        toolBar.setBackgroundColor(getResources().getColor(R.color.action_bar));
        FrameLayout.LayoutParams toolbarLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.toolbarHeight));
        toolbarLp.setMargins(0, (int) getResources().getDimension(R.dimen.statusBarHeight), 0, 0);
        if (hasNavigationBar()) {
            rootView.addView(toolBar, toolbarLp);
        }
        backView = (ImageButton) toolBar.findViewById(R.id.ib_back);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!hasBackView()) {
            backView.setVisibility(View.GONE);
        }
        titleView = (TextView) toolBar.findViewById(R.id.tv_title);

        View contentView = inflater.inflate(layoutResID, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if (hasNavigationBar()) {
            params.setMargins(0, (int) getResources().getDimension(R.dimen.toolbarHeight) + (int) getResources().getDimension(R.dimen.statusBarHeight), 0, 0);
        } else {
            params.setMargins(0, (int) getResources().getDimension(R.dimen.statusBarHeight), 0, 0);
        }
        rootView.addView(contentView, params);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showLoading() {
        if (dialogWaiting == null) {
            dialogWaiting = new DialogWaiting(this);
            dialogWaiting.setCanceledOnTouchOutside(false);
            dialogWaiting.setCancelable(true);
        }
        if (!dialogWaiting.isShowing()) {
            dialogWaiting.show();
        }
    }

    public void dismissLoading() {
        if (dialogWaiting != null && dialogWaiting.isShowing()) {
            dialogWaiting.dismiss();
        }
    }

    public void showAlertDialog(String title, String positionButtonText, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton(positionButtonText, listener)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
