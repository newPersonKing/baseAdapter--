package com.system.translationpen.settings.time;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.algorithm.android.utils.AlRecyclerViewDecoration;
import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.SPUtils;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.settings.bean.FixBean;
import com.system.translationpen.utils.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.system.translationpen.settings.Constant.getSelectOri;

public class FixedRegionActivity extends BaseTitleActivity {
    @BindView(R.id.fixed_region_rec)
    RecyclerView fixed_region_rec;

    FixedRegionAdapter adapter;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_fixed_region;
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.wback,getSelectOri(0),0);


        fixed_region_rec.setLayoutManager(new LinearLayoutManager(this));

        fixed_region_rec.addItemDecoration(new AlRecyclerViewDecoration(getResources(), R.color.money_unit_color,
                R.dimen.dp_1, LinearLayoutManager.VERTICAL));

        adapter=new FixedRegionAdapter(this, new FixedRegionAdapter.FixedCallBack() {
            @Override
            public void onClick(FixBean fixBean) {
                String time=fixBean.getTime();
                Intent intent=new Intent();
                intent.putExtra("time",time);
                intent.putExtra("address",fixBean.getAddress());
                setResult(100,intent);
                SPUtils.getInstance().set("FIX_ADDRESS",fixBean.getAddress());
                SPUtils.getInstance().set("FIX_TIME",fixBean.getTime());
                finish();
            }
        });

        fixed_region_rec.setAdapter(adapter);
        try {
            InputStream inputStream=getAssets().open("fixed_region.xlsx");
            List<FixBean> datas=getDatas(ExcelUtil.readExcel2007(inputStream));
            adapter.setDatas(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<FixBean> getDatas(ArrayList<ArrayList<Object>> lists){
        List<FixBean> fixBeans=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            FixBean fixBean=new FixBean();
            ArrayList childs=lists.get(i);
            fixBean.setAddressE((String) childs.get(0));
            fixBean.setAddress((String) childs.get(1));
            fixBean.setTime((String) childs.get(2));
            fixBeans.add(fixBean);
        }
        return fixBeans;
    }
}
