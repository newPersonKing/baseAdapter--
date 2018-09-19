package com.system.translationpen.exchangerate.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.translationpen.R;

public class ContentHolder extends BaseViewHolder {

    public ImageView country_pic;
    public TextView country_name;
    public TextView unit;
    public LinearLayout ll_two;

    public ContentHolder(View itemView) {
        super(itemView);
        country_pic=itemView.findViewById(R.id.country_pic);
        country_name=itemView.findViewById(R.id.country_name);
        unit=itemView.findViewById(R.id.unit);
        ll_two=itemView.findViewById(R.id.ll_two);
    }
}
