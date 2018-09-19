package com.system.translationpen.settings.wlan;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.settings.Constant;

import org.apache.poi.hssf.record.formula.functions.T;

import butterknife.BindView;
import butterknife.OnClick;

public class WifiBreakActivity extends BaseTitleActivity {

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

    @Override
    protected void beforeSetView() {
        super.beforeSetView();
        name=getIntent().getStringExtra("name");
        type=getIntent().getStringExtra("type");
        streng=getIntent().getIntExtra("streng",0);
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
                breakWifi();
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void breakWifi(){
        WifiController.instance().disconnectWifi();
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        tv_state_name.setText(Constant.getWifiDetail(0));
        tv_streng_name.setText(Constant.getWifiDetail(1));
        tv_entry_name.setText(Constant.getWifiDetail(2));
        btn_cancel.setText(Constant.getWifiDetail(3));
        btn_break.setText(Constant.getWifiDetail(4));
        tv_type.setText(Constant.getWlanState(0));
    }
}
