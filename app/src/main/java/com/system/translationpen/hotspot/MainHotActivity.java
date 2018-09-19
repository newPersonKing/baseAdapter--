package com.system.translationpen.hotspot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.system.translationpen.R;
import com.system.translationpen.hotspot.activity.ConnetListActivity;
import com.system.translationpen.hotspot.activity.MakeWifiHotActivity;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.bean.WifiApClient;
import com.system.translationpen.hotspot.manager.IWifiApListener;
import com.system.translationpen.hotspot.manager.WifiAPUtil;
import com.system.translationpen.hotspot.manager.WifiApServer;
import com.system.translationpen.hotspot.manager.WifiController;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class MainHotActivity extends BaseTitleActivity {

    @BindView(R.id.wifi_hot_name)
    TextView wifi_hot_name;
    @BindView(R.id.turn_on)
    CheckBox turn_on;
    @BindView(R.id.connect_count)
    TextView connect_count;

    private Random random=new Random();
    boolean isSetting=false;
    @Override
    protected int onSetContentView() {
        return R.layout.activity_hot_main;
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.retur,"WLAN热点分享",0);
        WifiApServer wifiApServer= WifiController.instance().getWifiApConfiguration();
        if (wifiApServer!=null){
            wifi_hot_name.setText(wifiApServer.getSSID());
            isSetting=true;
        }else {
            isSetting=false;
            wifi_hot_name.setText("EUPIN"+random.nextInt(1000000));
            WifiAPUtil.getInstance(this).setNameAndPassword(wifi_hot_name.getText().toString(),"12345678", WifiAPUtil.WifiSecurityType.WIFICIPHER_WPA);
        }


        turn_on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!WifiController.instance().isMobileNetOpen(MainHotActivity.this)){
                    Toast.makeText(MainHotActivity.this,"请插入sim卡",Toast.LENGTH_SHORT).show();
                    turn_on.setChecked(false);
                    return;
                }

                turn_on.setChecked(isChecked);

                if (!WifiController.instance().requestWritePermissionSettings(MainHotActivity.this,200)){
                    turn_on.setChecked(false);
                    return;
                }

                if (!isChecked){
                    WifiController.instance().closeWifiAp();
                }else {
                    if (isSetting){
                        WifiController.instance().openWifiAp();
                    }else {
                        turn_on.setChecked(false);
                        Toast.makeText(MainHotActivity.this,"请先设置wifi热点",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},200);
            return;
        }else {
            getYYS();
        }
    }
    IWifiApListener listener=new IWifiApListener() {
        @Override
        public void onScanWifiApClients(List<WifiApClient> wifiApClients) {
            if (WifiController.instance().isWifiApOpen()){
                connect_count.setText("已连接"+wifiApClients.size()+"台");
            }else {
                connect_count.setText("已连接"+0+"台");
            }
        }

        @Override
        public void getState(int state) {
            if (state==10||state==11){
                top_wifi_hot.setVisibility(View.GONE);
            }else {
                top_wifi_hot.setVisibility(View.VISIBLE);
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();

        WifiApServer wifiApServer=WifiController.instance().getWifiApConfiguration();
        if (wifiApServer!=null) {
            wifi_hot_name.setText(wifiApServer.getSSID());
            isSetting = true;
        }

        turn_on.setChecked(WifiController.instance().isWifiApOpen());

        WifiController.instance().registerWifiApReceiver(this, listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WifiController.instance().unregisterWifiApReceiver(this,listener);

    }

    @OnClick({
            R.id.btn_make_wifi,
            R.id.btn_connect_number
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.btn_make_wifi:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (WifiController.instance().requestWritePermissionSettings(MainHotActivity.this,200)){
                        Intent intent=new Intent(this,MakeWifiHotActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.btn_connect_number:
                Intent connect=new Intent(this,ConnetListActivity.class);
                startActivity(connect);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
