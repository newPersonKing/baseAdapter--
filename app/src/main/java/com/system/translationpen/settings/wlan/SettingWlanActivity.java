package com.system.translationpen.settings.wlan;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.algorithm.android.utils.AlRecyclerViewDecoration;
import com.algorithm.android.utils.DateUtils;
import com.fanwe.lib.switchbutton.FSwitchButton;
import com.fanwe.lib.switchbutton.SwitchButton;
import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.DaoUntils;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.bean.WifiDetail;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.hotspot.untils.SettingsController;
import com.system.translationpen.settings.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getHintMessage;

public class SettingWlanActivity extends BaseTitleActivity implements WifiController.WifiConnectCallback {

    @BindView(R.id.sb_rect)
    FSwitchButton sb_rect;
    @BindView(R.id.rl_wlan)
    RecyclerView rl_wlan;

    @BindView(R.id.tv_turn_name)
    TextView tv_turn_name;
    @BindView(R.id.tv_wlan_name)
    TextView tv_wlan_name;
    @BindView(R.id.tv_wlan_list_name)
    TextView tv_wlan_list_name;

    private WlanAdapter adapter;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_setting_wlan;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback, Constant.getWlanMain(0),0);

        sb_rect.setOnCheckedChangeCallback(new SwitchButton.OnCheckedChangeCallback() {
            @Override
            public void onCheckedChanged(boolean checked, SwitchButton view) {
                if (checked){
                    refreshWlan();
                    WifiController.instance().openWifi();
                }else {
                    List<WifiDetail> wifiDetails=new ArrayList<>();
                    adapter.setData(wifiDetails);
                    WifiController.instance().closeWifi();
                }
            }
        });

        rl_wlan.setLayoutManager(new LinearLayoutManager(this));
        rl_wlan.addItemDecoration(new AlRecyclerViewDecoration(getResources(), R.color.money_unit_color,
                R.dimen.dp_1, LinearLayoutManager.VERTICAL));
        adapter=new WlanAdapter(this, new WlanAdapter.WlanCallBack() {
            @Override
            public void onClickConnectPass(WifiDetail wifiDetail) {
                Intent intent=new Intent();
                intent.putExtra("name",wifiDetail.getWifiName());
                intent.putExtra("type",wifiDetail.getCapabilities());
                intent.putExtra("streng",wifiDetail.getStrength());
                String password=DaoUntils.getRememberPassword(wifiDetail.getWifiName());
                if (password!=""){
                    intent.putExtra("password",password);
                    intent.setClass(SettingWlanActivity.this,RememberPassActivity.class);
                }else {
                    intent.setClass(SettingWlanActivity.this,ConnectWlanActivity.class);
                }

                startActivity(intent);
            }

            @Override
            public void onClickConnectNoPass(WifiDetail wifiDetail) {
                connectWifi(wifiDetail,"");
            }

            @Override
            public void onClickConnect(WifiDetail wifiDetail) {
                Intent intent=new Intent(SettingWlanActivity.this,WifiBreakActivity.class);
                intent.putExtra("name",wifiDetail.getWifiName());
                intent.putExtra("streng",wifiDetail.getStrength());
                intent.putExtra("type",wifiDetail.getCapabilities());
                startActivity(intent);
            }
        });
        rl_wlan.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (WifiController.instance().isWifiOpen()){
            refreshWlan();
            sb_rect.setChecked(true,false,false);
        }else {
            sb_rect.setChecked(false,false,false);
        }
    }

    @OnClick({
            R.id.iv_wlan_refresh
    })
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_wlan_refresh:

                if(WifiController.instance().isWifiOpen()){
                    mProgressDialog.setMessage(getHintMessage(0));
                    mProgressDialog.show();
                    refreshWlan();
                }
                break;
        }
    }
    /*重新获取可连接wifi*/
    private void refreshWlan(){
        List<WifiDetail> wifiDetails= WifiController.instance().getWifiDetails();
        mProgressDialog.dismiss();
        adapter.setData(wifiDetails);
    }

    private void connectWifi(WifiDetail wifiDetail,String password){

        WifiConfiguration wifiConfiguration=WifiController.instance().genWifiConfiguration(wifiDetail,password);
        WifiController.instance().connectToWifi(wifiConfiguration,this);

    }

    @Override
    public void onWifiConnect(boolean success, String error) {

        if (success){
            Toast.makeText(this,"连接成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"连接失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
        if (WifiController.instance().isWifiOpen()){
            refreshWlan();
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        tv_turn_name.setText(Constant.getWlanMain(1));
        tv_wlan_name.setText(Constant.getWlanMain(2));
        tv_wlan_list_name.setText(Constant.getWlanMain(3));
    }
}
