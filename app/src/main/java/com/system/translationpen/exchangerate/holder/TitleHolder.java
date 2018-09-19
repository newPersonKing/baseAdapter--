package com.system.translationpen.exchangerate.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.translationpen.R;

public class TitleHolder extends BaseViewHolder {

    public TextView title;
    public TextView alltv;
    public LinearLayout ll_one;
    public ImageView qqwl_enter;
    public TitleHolder(View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.title);
        alltv=itemView.findViewById(R.id.all);
        ll_one=itemView.findViewById(R.id.ll_one);
        qqwl_enter=itemView.findViewById(R.id.qqwl_enter);
    }
}
