package com.system.translationpen.exchangerate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.Constant;
import com.system.translationpen.exchangerate.bean.ExChangeMultipleItem;

import java.util.List;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.MyViewHolder> {

    private Context context;
    private ExchangeAdapterCallBack callBack;
    private List<ExChangeMultipleItem> datas;
    public ExchangeAdapter(Context context, List<ExChangeMultipleItem> datas, ExchangeAdapterCallBack callBack){
        this.context=context;
        this.callBack=callBack;
        this.datas=datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.exchange_rote_china,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.go_to_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onCountryClick(position);
            }
        });
        holder.go_to_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onMoneyClick(position);
            }
        });

        ExChangeMultipleItem item=datas.get(position);
        holder.country_pic.setImageResource(Constant.map.get(item.getCurno()));
        holder.country_name.setText(item.getCurno());
        holder.unit.setText(item.getCname());
        if (item.getSsRote()!=null){
            holder.rote.setText(item.getSsRote());
        }else {
            holder.rote.setText(item.getMiddle());
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout go_to_country;
        LinearLayout go_to_money;
        ImageView country_pic;
        TextView country_name;
        TextView rote;
        TextView unit;
        public MyViewHolder(View itemView) {
            super(itemView);
            go_to_country=itemView.findViewById(R.id.go_to_country);
            go_to_money=itemView.findViewById(R.id.go_to_money);
            country_pic=itemView.findViewById(R.id.country_pic);
            country_name=itemView.findViewById(R.id.country_name);
            rote=itemView.findViewById(R.id.rote);
            unit=itemView.findViewById(R.id.unit);
        }
    }

    public interface ExchangeAdapterCallBack{
        void onCountryClick(int position);
        void onMoneyClick(int position);
    }
}
