package com.system.translationpen.settings.time;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.SPUtils;
import com.system.translationpen.settings.bean.FixBean;

import java.util.ArrayList;
import java.util.List;

public class FixedRegionAdapter extends RecyclerView.Adapter<FixedRegionAdapter.FixedHolder> {

    List<FixBean> fixBeans=new ArrayList<>();

    FixedCallBack callBack;
    Context context;
    public FixedRegionAdapter(Context context,FixedCallBack callBack){
        this.callBack=callBack;
        this.context=context;
    }

    @Override
    public FixedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fixed_region,parent,false);
        return new FixedHolder(view);
    }

    @Override
    public void onBindViewHolder(FixedHolder holder, int position) {
        final FixBean fixBean=fixBeans.get(position);
        int language= (int) SPUtils.getInstance().get("language",1);
        if (language==1){
            holder.fixed_region_address.setText(fixBean.getAddress());
        }else {
            holder.fixed_region_address.setText(fixBean.getAddressE());
        }

        holder.fixed_region_time.setText(fixBean.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(fixBean);
            }
        });
    }
    @Override
    public int getItemCount() {
        return fixBeans.size();
    }

    public void setDatas(List<FixBean> datas){
        this.fixBeans=datas;
        notifyDataSetChanged();
    }

    class FixedHolder extends RecyclerView.ViewHolder{

        TextView fixed_region_address;
        TextView fixed_region_time;

        public FixedHolder(View itemView) {
            super(itemView);
            fixed_region_address=itemView.findViewById(R.id.fixed_region_address);
            fixed_region_time=itemView.findViewById(R.id.fixed_region_time);

        }
    }

    public  interface FixedCallBack{
        void onClick(FixBean fixBean);
    }
}
