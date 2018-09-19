package com.system.translationpen.settings.wlan;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.bean.WifiDetail;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.settings.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.system.translationpen.settings.Constant.getHintMessage;

public class WlanAdapter extends RecyclerView.Adapter<WlanAdapter.WlanHolder> {

    List<WifiDetail> wifiDetails=new ArrayList<>();
    WlanCallBack wlanCallBack;
    Context context;

    public WlanAdapter(Context context,WlanCallBack wlanCallBack){
        this.context=context;
        this.wlanCallBack=wlanCallBack;
    }
    @Override
    public WlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_wlan,parent,false);
        return new WlanHolder(view);
    }

    @Override
    public void onBindViewHolder(WlanHolder holder, int position) {

        final WifiDetail wifiDetail=wifiDetails.get(position);

        holder.wifiName.setText(wifiDetail.getWifiName());

        String connectWifiName="";
        if (WifiController.instance().isWifiConnected()){
            WifiInfo wifiInfo= WifiController.instance().getConnectionWifiInfo();
            connectWifiName=wifiInfo.getSSID();
        }
        int tag=0;
        boolean isConnect=false;
        if (wifiDetail.getCapabilities().equals(getHintMessage(1))){
            holder.wifiState.setText(Constant.getWlanState(2));
            holder.wifi_img_lock.setVisibility(View.GONE);
            tag=1;
        }else {
            holder.wifiState.setText(Constant.getWlanState(1));
            holder.wifi_img_lock.setVisibility(View.VISIBLE);
            tag=2;
        };
        if (connectWifiName.equals("\""+wifiDetail.getWifiName()+"\"")){
            holder.wifiState.setText(Constant.getWlanState(0));
            isConnect=true;
        }

        final boolean finalIsConnect = isConnect;
        final int finalTag = tag;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalIsConnect){
                    wlanCallBack.onClickConnect(wifiDetail);
                }else {
                    if (finalTag ==2){
                        wlanCallBack.onClickConnectPass(wifiDetail);
                    }else {
                        wlanCallBack.onClickConnectNoPass(wifiDetail);
                    }
                }
            }
        });



        switch (wifiDetail.getStrength()){
            case 1:
                holder.wifiimg.setImageResource(R.drawable.wlan_wifi4);
                break;
            case 2:
                holder.wifiimg.setImageResource(R.drawable.wlan_wifi3);
                break;
            case 3:
                holder.wifiimg.setImageResource(R.drawable.wlan_wifi2);
                break;
            case 4:
                holder.wifiimg.setImageResource(R.drawable.wlan_wifi1);
                break;
        }

    }

    public void setData(List<WifiDetail> wifiDetails){
        this.wifiDetails=wifiDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wifiDetails.size();
    }

    class WlanHolder extends RecyclerView.ViewHolder{

        TextView wifiName;
        TextView wifiState;
        ImageView wifiimg;
        ImageView wifi_img_lock;

        public WlanHolder(View itemView) {
            super(itemView);
            wifiName=itemView.findViewById(R.id.wifi_name);
            wifiState=itemView.findViewById(R.id.wifi_state);
            wifiimg=itemView.findViewById(R.id.wifi_img);
            wifi_img_lock=itemView.findViewById(R.id.wifi_img_lock);
        }
    }

   public interface  WlanCallBack{
        void onClickConnectPass(WifiDetail wifiDetail);
        void onClickConnectNoPass(WifiDetail wifiDetail);
        void onClickConnect(WifiDetail wifiDetail);
    }
}
