package com.system.translationpen.entry;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.activity.ExchangeActivity;
import com.system.translationpen.hotspot.MainHotActivity;
import com.system.translationpen.settings.ActivitySettings;
import com.system.translationpen.settings.SettingMainActivity;

public class ActivityMain extends ActivityBase implements View.OnClickListener {


    @Override
    protected boolean hasBackView() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_title);

        Button exchange =findViewById(R.id.exchange);
        Button hot =findViewById(R.id.hot);
        Button setting =findViewById(R.id.setting);
        exchange.setOnClickListener(this);
        hot.setOnClickListener(this);
        setting.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exchange:
                ActivityBase.open(this, ExchangeActivity.class);
                break;
            case R.id.hot:
                ActivityBase.open(this, MainHotActivity.class);
                break;
            case R.id.setting:
                ActivityBase.open(this, SettingMainActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
