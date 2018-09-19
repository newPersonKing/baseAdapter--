package com.system.translationpen.hotspot.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.system.translationpen.R;
import com.system.translationpen.hotspot.adapter.MyConnectAdapter;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.bean.WifiApClient;
import com.system.translationpen.hotspot.manager.IWifiApListener;
import com.system.translationpen.hotspot.manager.WifiController;

import java.util.List;

import butterknife.BindView;

public class ConnetListActivity extends BaseTitleActivity {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_connet_size)
    TextView tv_connet_size;

    private MyConnectAdapter adapter;
    @Override
    protected int onSetContentView() {
        return R.layout.layout_connect_list;
    }

    IWifiApListener wifiApListener=new IWifiApListener() {
        @Override
        public void onScanWifiApClients(List<WifiApClient> wifiApClients) {
            if (WifiController.instance().isWifiApOpen()){
                tv_connet_size.setText("已连接"+wifiApClients.size()+"台");
                adapter.setDatas(wifiApClients);
            }else {
                tv_connet_size.setText("已连接"+0+"台");
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
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.retur,"连接设备列表",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        adapter=new MyConnectAdapter(this);
        recyclerView.setAdapter(adapter);

        WifiController.instance().registerWifiApReceiver(this,wifiApListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WifiController.instance().unregisterWifiApReceiver(this,wifiApListener);
    }
}
