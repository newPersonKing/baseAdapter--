package com.system.translationpen.hotspot.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.system.translationpen.R;
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

public class MakeWifiHotActivity extends BaseTitleActivity {

    public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    public static final String EXTRA_WIFI_AP_STATE = "wifi_state";
    public static int WIFI_AP_STATE_DISABLING = 10;
    public static int WIFI_AP_STATE_DISABLED = 11;
    public static int WIFI_AP_STATE_ENABLING = 12;
    public static int WIFI_AP_STATE_ENABLED = 13;
    public static int WIFI_AP_STATE_FAILED = 14;

    Keyboard fuhao ;
    Keyboard zhu ;
    Keyboard zhu_small ;

    boolean isBig=true;

    @BindView(R.id.et_wifi_name)
    TextView et_wifi_name;
    @BindView(R.id.et_wifi_password)
    TextView et_wifi_password;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;


    private Random random=new Random();
    @Override
    protected int onSetContentView() {
        return R.layout.layout_make_wifi_hot;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onInitData() {
        setTitle(R.drawable.retur, "配置wifi热点", 0);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WIFI_AP_STATE_CHANGED_ACTION);

        fuhao = new Keyboard(this, R.xml.keybord_fuhao);// 符号键盘
        zhu = new Keyboard(this, R.xml.keybord_zhu); // 主键盘
        zhu_small = new Keyboard(this, R.xml.keybord_zhu_small);//小写的键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        WifiApServer wifiApServer= WifiController.instance().getWifiApConfiguration();
        if (wifiApServer!=null){
            et_wifi_name.setText(wifiApServer.getSSID());
            et_wifi_password.setText(wifiApServer.getPassword());
        }

        et_wifi_password.setShowSoftInputOnFocus(false);
        et_wifi_name.setShowSoftInputOnFocus(false);
        WifiController.instance().registerWifiApReceiver(this,listener);
    }

    IWifiApListener listener=new IWifiApListener() {
        @Override
        public void onScanWifiApClients(List<WifiApClient> wifiApClients) {

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    private void  turnWifiHot(String name, String password){
        WifiAPUtil.getInstance(getApplicationContext()).turnOnWifiAp(name,password, WifiAPUtil.WifiSecurityType.WIFICIPHER_WPA);
    }

    @OnClick({
            R.id.btn_cancle,
            R.id.btn_sure,
            R.id.et_wifi_name,
            R.id.et_wifi_password
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_cancle:
                finish();
                break;
            case R.id.btn_sure:
                if (et_wifi_name.getText().toString().isEmpty()){
                    Toast.makeText(this,"热点名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_wifi_password.getText().toString().isEmpty()){
                    Toast.makeText(this,"密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_wifi_password.getText().length()<8){
                    Toast.makeText(this,"密码必须大于8位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (WifiController.instance().isWifiApOpen()){
                    WifiController.instance().closeWifiAp();
                }

                WifiAPUtil.getInstance(this).setNameAndPassword(et_wifi_name.getText().toString(),et_wifi_password.getText().toString(), WifiAPUtil.WifiSecurityType.WIFICIPHER_WPA);
                Toast.makeText(this,"保存成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.et_wifi_name:
                Intent nameIntent=new Intent(this,MakeWifiHotNameActivity.class);
                startActivityForResult(nameIntent,100);
                break;
            case R.id.et_wifi_password:
                Intent passIntent=new Intent(this,MakeWifiHotPasswordActivity.class);
                startActivityForResult(passIntent,200);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("cccccccccccc","dasdasdasd");
        if (data==null){
            return;
        }

        if (requestCode==100){
            String name=data.getStringExtra("name");
            if (name!=null){
                et_wifi_name.setText(name);
            }
        }
        if (requestCode==200){
            String password=data.getStringExtra("password");
            if (password!=null){
                et_wifi_password.setText(password);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
