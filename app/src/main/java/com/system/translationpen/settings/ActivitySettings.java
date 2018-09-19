package com.system.translationpen.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.system.translationpen.R;
import com.system.translationpen.entry.ActivityBase;

/**
 * Created by huison on 2018/6/5.
 */

public class ActivitySettings extends ActivityBase implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }
}
