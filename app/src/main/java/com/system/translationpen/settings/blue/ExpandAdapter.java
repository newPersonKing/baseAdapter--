package com.system.translationpen.settings.blue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.adapter.ExchangeAdapter;
import com.system.translationpen.exchangerate.bean.ExchangeBean;
import com.system.translationpen.hotspot.manager.BluetoothController;
import com.system.translationpen.hotspot.manager.BluetoothDetail;
import com.system.translationpen.settings.bean.Expandbean;

import java.util.ArrayList;
import java.util.List;

import static com.system.translationpen.settings.Constant.getWlanState;

public class ExpandAdapter extends BaseExpandableListAdapter {

    private Expandbean expandbean=new Expandbean();

    private Context context;
    private BlueCallBack callBack;

    public ExpandAdapter(Context context,BlueCallBack callBack){
        this.callBack=callBack;
        this.context=context;
    }

    /*暂定只有两个组*/
    @Override
    public int getGroupCount() {
        return expandbean.getDatas().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return  expandbean.getDatas().get(groupPosition).getDetails().size();
    }

    @Override
    public List<BluetoothDetail> getGroup(int groupPosition) {
        return expandbean.getDatas().get(groupPosition).getDetails();
    }

    @Override
    public BluetoothDetail getChild(int groupPosition, int childPosition) {
        return expandbean.getDatas().get(groupPosition).getDetails().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition+childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view=null;
        GroupHolder groupholder = null;
        if(convertView!=null){
            view = convertView;
            groupholder = (GroupHolder) view.getTag();
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_title,parent,false);
            groupholder =new GroupHolder();
            groupholder.tv_can_use_name =  view.findViewById(R.id.tv_can_use_name);
            groupholder.iv_blue_refresh=view.findViewById(R.id.iv_blue_refresh);
            view.setTag(groupholder);
        }

        if (groupPosition==0){
            groupholder.iv_blue_refresh.setVisibility(View.GONE);
        }
        groupholder.iv_blue_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onRefresh();
            }
        });
        groupholder.tv_can_use_name.setText(expandbean.getDatas().get(groupPosition).getTitle());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view=null;
        ChildHolder childHolder = null;
        if(convertView!=null){
            view = convertView;
            childHolder = (ChildHolder) view.getTag();
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting_blue,parent,false);
            childHolder =new ChildHolder();
            childHolder.blue_name =  view.findViewById(R.id.blue_name);
            childHolder.blue_state=view.findViewById(R.id.blue_state);
            childHolder.blue_img=view.findViewById(R.id.blue_img);
            view.setTag(childHolder);
        }

        final BluetoothDetail bluetoothDetail=expandbean.getDatas().get(groupPosition).getDetails().get(childPosition);

        childHolder.blue_name.setText(bluetoothDetail.getBluetoothDevice().getName());

        boolean isConnect=false;
        if (BluetoothController.instance().isConnected(bluetoothDetail)){
            childHolder.blue_state.setText(getWlanState(0));
            childHolder.blue_name.setTextColor(R.color.language_checked_color);
            isConnect=true;
        }else {
            childHolder.blue_state.setText("可用");
            isConnect=false;
        }
        final boolean finalIsConnect = isConnect;
        if (groupPosition!=0){
            view.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setExpandbean(Expandbean bean){
        this.expandbean=bean;
        notifyDataSetChanged();
    }

    static class GroupHolder{
        TextView tv_can_use_name;
        ImageView iv_blue_refresh;
    }

    static class ChildHolder{
        TextView blue_name;
        TextView blue_state;
        ImageView blue_img;
    }

    public interface BlueCallBack{
        void onClickConnect(BluetoothDetail bluetoothDetail);
        void onClickNoConnect(BluetoothDetail bluetoothDetail);
        void onRefresh();
    }
}
