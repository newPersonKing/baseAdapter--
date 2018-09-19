package com.system.translationpen.settings.wlan;

import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.bean.WifiDetail;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.settings.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getConnectState;
import static com.system.translationpen.settings.Constant.getSettinMain;

public class RememberPassActivity extends BaseTitleActivity implements WifiController.WifiConnectCallback{

    @BindView(R.id.tv_wifi_entry)
    TextView tv_wifi_entry;
    @BindView(R.id.tv_wifi_streng)
    TextView tv_wifi_streng;

    @BindView(R.id.tv_state_name)
    TextView tv_state_name;
    @BindView(R.id.tv_streng_name)
    TextView tv_streng_name;
    @BindView(R.id.tv_entry_name)
    TextView tv_entry_name;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_break)
    Button btn_break;
    @BindView(R.id.tv_type)
    TextView tv_type;

    String name;
    String type;
    int streng;
    String password;

    @Override
    protected void beforeSetView() {
        super.beforeSetView();
        name=getIntent().getStringExtra("name");
        type=getIntent().getStringExtra("type");
        streng=getIntent().getIntExtra("streng",0);
        password=getIntent().getStringExtra("password");
    }

    @Override
    protected int onSetContentView() {
        return R.layout.activity_wifi_break;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,name,0);
        tv_wifi_entry.setText(type);
        getStreng();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WifiController.instance().registerWifiReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WifiController.instance().unregisterWifiReceiver(this);
    }

    private void getStreng(){

        if (streng==1){
            tv_wifi_streng.setText(Constant.getWifiStrength(3));
        }else if (streng==2){
            tv_wifi_streng.setText(Constant.getWifiStrength(2));
        }else if (streng==3){
            tv_wifi_streng.setText(Constant.getWifiStrength(1));
        }else {
            tv_wifi_streng.setText(Constant.getWifiStrength(0));
        }
    };

    @OnClick({
            R.id.btn_cancel,
            R.id.btn_break
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.btn_break:
                connectWifi();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void connectWifi(){

        mProgressDialog.setMessage(getConnectState(2));
        mProgressDialog.show();

        if (!WifiController.instance().isWifiOpen()){
            WifiController.instance().openWifi();
        }

        List<WifiDetail> wifiDetails= WifiController.instance().getWifiDetails();
        for (WifiDetail wifiDetail:wifiDetails){
            if (wifiDetail.getWifiName().equals(name)){
                WifiConfiguration wifiConfiguration=WifiController.instance().genWifiConfiguration(wifiDetail,password);
                WifiController.instance().connectToWifi(wifiConfiguration,this);
                return;
            }
        }
    }


    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        tv_state_name.setText(Constant.getWifiDetail(0));
        tv_streng_name.setText(Constant.getWifiDetail(1));
        tv_entry_name.setText(Constant.getWifiDetail(2));
        btn_cancel.setText(Constant.getWifiDetail(3));
        btn_break.setText(Constant.getWifiPassword(4));
        tv_type.setText(Constant.getWlanState(0));
    }

    @Override
    public void onWifiConnect(boolean success, String error) {
        mProgressDialog.dismiss();
        if (success){
            Toast.makeText(this,getConnectState(0),Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this,getConnectState(1),Toast.LENGTH_SHORT).show();
        }
    }
}
