package com.system.translationpen.settings.wlan;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.DaoUntils;
import com.system.translationpen.hotspot.activity.MakeWifiHotNameActivity;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.bean.WifiDetail;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.settings.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getConnectState;

public class ConnectWlanActivity extends BaseTitleActivity implements WifiController.WifiConnectCallback {

    String wifiName="";
    /*信号强度*/
    int wifiStrength=1;
    /*加密类型*/
    private String type;

    @BindView(R.id.tv_password)
    TextView tv_password;
    @BindView(R.id.tv_streng)
    TextView tv_streng;
    @BindView(R.id.tv_type)
    TextView tv_type;

    @BindView(R.id.tv_wifi_streng_name)
    TextView tv_wifi_streng_name;
    @BindView(R.id.tv_entry_type_name)
    TextView tv_entry_type_name;
    @BindView(R.id.tv_password_name)
    TextView tv_password_name;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_connect)
    Button btn_connect;

    @Override
    protected void beforeSetView() {
        super.beforeSetView();
        wifiName=getIntent().getStringExtra("name");
        wifiStrength=getIntent().getIntExtra("streng",0);
        type=getIntent().getStringExtra("type");
    }

    @Override
    protected int onSetContentView() {
        return R.layout.activity_connect_wlan;
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

    @Override
    protected void onInitData() {


        tv_type.setText(type);

        // 强、较强、中、弱
        if (wifiStrength==1){
            tv_streng.setText(Constant.getWifiStrength(3));
        }else if (wifiStrength==2){
            tv_streng.setText(Constant.getWifiStrength(2));
        }else if (wifiStrength==3){
            tv_streng.setText(Constant.getWifiStrength(1));
        }else {
            tv_streng.setText(Constant.getWifiStrength(0));
        }
    }
    @OnClick({
            R.id.tv_password,
            R.id.btn_cancel,
            R.id.btn_connect
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_password:
                Intent nameIntent=new Intent(this,WlanPassWordActivity.class);
                startActivityForResult(nameIntent,100);
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_connect:
                connectWifi();
                break;
        }
    }

    private void connectWifi(){
        mProgressDialog.setMessage(getConnectState(2));
        mProgressDialog.show();
        List<WifiDetail> wifiDetails= WifiController.instance().getWifiDetails();
        for (WifiDetail wifiDetail:wifiDetails){
            if (wifiDetail.getWifiName().equals(wifiName)){

                WifiConfiguration wifiConfiguration=WifiController.instance().genWifiConfiguration(wifiDetail,password);
                WifiController.instance().connectToWifi(wifiConfiguration,this);
                return;
            }
        }
    }

    String password="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100){
            if (data!=null){
                password=data.getStringExtra("password");
                tv_password.setText(password);
            }
        }
    }

    @Override
    public void onWifiConnect(boolean success, String error) {
        mProgressDialog.dismiss();
        if (success){
            Toast.makeText(this,getConnectState(0),Toast.LENGTH_SHORT).show();
            DaoUntils.remember(wifiName,password);
            finish();
        }else {
            Toast.makeText(this,getConnectState(1),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        setTitle(R.drawable.wback,wifiName,0);
        tv_wifi_streng_name.setText(Constant.getWifiPassword(0));
        tv_entry_type_name.setText(Constant.getWifiPassword(1));
        tv_password_name.setText(Constant.getWifiPassword(2));
        btn_cancel.setText(Constant.getWifiPassword(3));
        btn_connect.setText(Constant.getWifiPassword(4));
    }
}
