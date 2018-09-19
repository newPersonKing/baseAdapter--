package com.system.translationpen.exchangerate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.algorithm.android.utils.DisplayUtils;
import com.system.translationpen.R;
import com.system.translationpen.exchangerate.Constant;
import com.system.translationpen.exchangerate.bean.ExChangeMultipleItem;
import com.system.translationpen.exchangerate.bean.ExchangeBean;
import com.system.translationpen.exchangerate.bean.MultiItemEntity;
import com.system.translationpen.exchangerate.holder.BaseViewHolder;
import com.system.translationpen.exchangerate.holder.ContentHolder;
import com.system.translationpen.exchangerate.holder.TitleHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    Context context;

    private MultipleItemQuickAdapterCallBack callBack;

    private List<ExChangeMultipleItem> datas=new ArrayList<>();

    public MultipleItemQuickAdapter(Context context,MultipleItemQuickAdapterCallBack callBack){
        this.context=context;
        this.callBack=callBack;
    }

    public void setDatas(List<ExChangeMultipleItem> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType== ExChangeMultipleItem.one||viewType== ExChangeMultipleItem.three){
            View view= LayoutInflater.from(context).inflate(R.layout.exchange_item_one,parent,false);

            return new TitleHolder(view);
        }else {
            View view= LayoutInflater.from(context).inflate(R.layout.exchange_item_two,parent,false);
            return new ContentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {

           final ExChangeMultipleItem item= datas.get(position);

           if (datas.get(position).getItemType()==ExChangeMultipleItem.one){
               TitleHolder titleHolder= (TitleHolder) holder;
               titleHolder.title.setText(item.getCname());
               if (item.getCname().equals("常用")){
                   titleHolder.alltv.setVisibility(View.GONE);
                   titleHolder.qqwl_enter.setVisibility(View.GONE);
               }else {
                   titleHolder.alltv.setVisibility(View.VISIBLE);
                   titleHolder.qqwl_enter.setVisibility(View.VISIBLE);
                   titleHolder.itemView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           callBack.onClickMore(item.getCname());
                       }
                   });
               }
           }else if (datas.get(position).getItemType()==ExChangeMultipleItem.two){
               ContentHolder contentHolder= (ContentHolder) holder;
               contentHolder.country_name.setText(item.getCurno());
               contentHolder.country_pic.setImageResource(Constant.map.get(item.getCurno()));
               contentHolder.unit.setText(item.getCname());
               contentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       callBack.onItemClick(position);
                   }
               });
           }else {
               TitleHolder titleHolder= (TitleHolder) holder;
               titleHolder.alltv.setVisibility(View.GONE);
               titleHolder.qqwl_enter.setVisibility(View.GONE);
               titleHolder.title.setText(item.getCname());
           }
    }


    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface MultipleItemQuickAdapterCallBack{
        void onClickMore(String cname);
        void onItemClick(int position);
    }
}
