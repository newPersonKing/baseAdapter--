package com.system.translationpen.settings.blue;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.manager.BluetoothController;
import com.system.translationpen.hotspot.manager.BluetoothDetail;

import java.util.ArrayList;
import java.util.List;

public class BlueAdapter extends RecyclerView.Adapter<BlueAdapter.BlueHolder> {

    List<BluetoothDetail> datas=new ArrayList<>();

    private Context context;
    private BlueCallBack callBack;
    public  BlueAdapter(Context context,BlueCallBack callBack){
        this.callBack=callBack;
        this.context=context;
    }

    @Override
    public BlueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()). inflate(R.layout.item_setting_blue,parent,false);
        return new BlueHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BlueHolder holder, int position) {
        final BluetoothDetail bluetoothDetail=datas.get(position);
        holder.blue_name.setText(bluetoothDetail.getBluetoothDevice().getName());

        boolean isConnect=false;
        if (BluetoothController.instance().isConnected(bluetoothDetail)){
            holder.blue_state.setText("已连接");
            holder.blue_name.setTextColor(R.color.language_checked_color);
            isConnect=true;
        }else {
            holder.blue_state.setText("可用");
            isConnect=false;
        }
        final boolean finalIsConnect = isConnect;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalIsConnect){
                    callBack.onClickConnect(bluetoothDetail);
                }else {
                    callBack.onClickNoConnect(bluetoothDetail);
                }
            }
        });
    }

    public void setDatas( List<BluetoothDetail> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class BlueHolder extends RecyclerView.ViewHolder{

        TextView blue_name;
        TextView blue_state;
        ImageView blue_img;

        public BlueHolder(View itemView) {
            super(itemView);
            blue_name=itemView.findViewById(R.id.blue_name);
            blue_state=itemView.findViewById(R.id.blue_state);
            blue_img=itemView.findViewById(R.id.blue_img);
        }
    }

    public interface BlueCallBack{
        void onClickConnect(BluetoothDetail bluetoothDetail);
        void onClickNoConnect(BluetoothDetail bluetoothDetail);
    }
}
