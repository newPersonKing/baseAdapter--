package com.system.translationpen.settings;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gy.permission.PermissionHelper;
import com.gy.permission.PermissionUtils;
import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.manager.BluetoothController;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.settings.blue.SettingBlueActivity;
import com.system.translationpen.settings.equipment.EquipmentMainActivity;
import com.system.translationpen.settings.language.LanguageMainActivity;
import com.system.translationpen.settings.light.LightMainActivity;
import com.system.translationpen.settings.recovery.RecoveryMainActivity;
import com.system.translationpen.settings.time.TimeMainActivity;
import com.system.translationpen.settings.update.UpdateMainActivity;
import com.system.translationpen.settings.voice.VoiceMainActivity;
import com.system.translationpen.settings.wlan.SettingWlanActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getSwitchState;

public class SettingMainActivity extends BaseTitleActivity {

    @BindView(R.id.setting_wifi_state)
    TextView setting_wifi_state;
    @BindView(R.id.setting_blue_state)
    TextView setting_blue_state;
    @BindView(R.id.setting_hot_state)
    TextView setting_hot_state;

    @BindView(R.id.tv_connect)
    TextView tv_connect;
    @BindView(R.id.tv_wlan_name)
    TextView tv_wlan_name;
    @BindView(R.id.tv_blue_name)
    TextView tv_blue_name;
    @BindView(R.id.tv_bri_voice)
    TextView tv_bri_voice;
    @BindView(R.id.tv_bri_name)
    TextView tv_bri_name;
    @BindView(R.id.tv_voice_name)
    TextView tv_voice_name;
    @BindView(R.id.tv_system)
    TextView tv_system;
    @BindView(R.id.tv_time_name)
    TextView tv_time_name;
    @BindView(R.id.tv_system_language_name)
    TextView tv_system_language_name;
    @BindView(R.id.tv_update_name)
    TextView tv_update_name;
    @BindView(R.id.tv_recovery_name)
    TextView tv_recovery_name;
    @BindView(R.id.tv_device_message)
    TextView tv_device_message;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_setting_main;
    }

    @Override
    protected void onInitData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSettingState();
    }

    @OnClick({
            R.id.ll_setting_wifi,
            R.id.ll_blue,
            R.id.ll_light,
            R.id.ll_voice,
            R.id.ll_setting_time,
            R.id.ll_setting_language,
            R.id.ll_setting_recovery,
            R.id.ll_system_message,
            R.id.ll_system_update
    })
    @Override
    public void onClick(View v) {

        Intent intent=new Intent();

        switch (v.getId()){
            case R.id.ll_setting_wifi:
                requestWlanPer();
                intent.setClass(this,SettingWlanActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_blue:
                intent.setClass(this, SettingBlueActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_light:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(this)) {
                        Intent intentA = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intentA, 200);
                    } else {
                        intent.setClass(this, LightMainActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_voice:
                intent.setClass(this, VoiceMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_setting_time:
                intent.setClass(this, TimeMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_setting_language:
                intent.setClass(this, LanguageMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_setting_recovery:
                intent.setClass(this, RecoveryMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_system_update:
                intent.setClass(this, UpdateMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_system_message:
                intent.setClass(this, EquipmentMainActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void refreshSettingState() {

        /*wifi 打开与关闭 显示不同内容*/
        if (WifiController.instance().isWifiOpen()){
            if (WifiController.instance().isWifiConnected()){
                WifiInfo wifiInfo= WifiController.instance().getConnectionWifiInfo();
               String name= wifiInfo.getSSID().replace("\"","");
                setting_wifi_state.setText(name);
            }else {
                setting_wifi_state.setText(getSwitchState(2));
            }
        }else {
            setting_wifi_state.setText(getSwitchState(1));
        }

        /*判断蓝牙*/
        if (BluetoothController.instance().isBluetoothOpen()){
            setting_blue_state.setText(getSwitchState(0));
        }else {
            setting_blue_state.setText(getSwitchState(1));
        }

        /*热点状态*/
        if (WifiController.instance().isWifiApOpen()){
            setting_hot_state.setText(getSwitchState(0));
        }else{
            setting_hot_state.setText(getSwitchState(1));
        }
    }

    private void requestWlanPer(){
        PermissionUtils.permission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        ).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied() {

            }
        }).request();
    }

    public void refreshLanguage(){

        setTitle(R.drawable.wback,Constant.getSettinMain(0),0);

        tv_connect.setText(Constant.getSettinMain(1));
        tv_wlan_name.setText(Constant.getSettinMain(2));
        tv_blue_name.setText(Constant.getSettinMain(3));
        tv_bri_voice.setText(Constant.getSettinMain(4));
        tv_bri_name.setText(Constant.getSettinMain(5));
        tv_voice_name.setText(Constant.getSettinMain(6));
        tv_system.setText(Constant.getSettinMain(7));
        tv_time_name.setText(Constant.getSettinMain(8));
        tv_system_language_name.setText(Constant.getSettinMain(9));
        tv_update_name.setText(Constant.getSettinMain(10));
        tv_recovery_name.setText(Constant.getSettinMain(11));
        tv_device_message.setText(Constant.getSettinMain(12));
    }

}
