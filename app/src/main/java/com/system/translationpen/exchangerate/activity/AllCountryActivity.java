package com.system.translationpen.exchangerate.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.algorithm.android.utils.AlRecyclerViewDecoration;
import com.system.translationpen.R;
import com.system.translationpen.exchangerate.Constant;
import com.system.translationpen.exchangerate.adapter.MultipleItemQuickAdapter;
import com.system.translationpen.exchangerate.bean.ExChange;
import com.system.translationpen.exchangerate.bean.ExChangeMultipleItem;
import com.system.translationpen.exchangerate.bean.ExchangeBean;
import com.system.translationpen.exchangerate.bean.SelectCountryBean;
import com.system.translationpen.exchangerate.until.DaoUntils;
import com.system.translationpen.exchangerate.until.NetworkUtils;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.http.HttpRequestUtils;
import com.system.translationpen.utils.BaseUtils;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class AllCountryActivity extends BaseTitleActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    ProgressDialog progressDialog;

    private List<ExChangeMultipleItem> datas=new ArrayList<>();
    private MultipleItemQuickAdapter adapter;
    private String cname;
    @Override
    protected int onSetContentView() {
        return R.layout.exchange_all_country_layout;
    }

    @Override
    protected void onInitData() {

        cname=getIntent().getStringExtra("cname");

        setTitle(R.drawable.wback,"全部货币",0);
        progressDialog=new ProgressDialog(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MultipleItemQuickAdapter(this, new MultipleItemQuickAdapter.MultipleItemQuickAdapterCallBack() {
            @Override
            public void onClickMore(String cname) {

            }

            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent();
                intent.putExtra("CName",datas.get(position).getCname());
                intent.putExtra("Curno",datas.get(position).getCurno());
                intent.putExtra("Middle",datas.get(position).getMiddle());
                intent.putExtra("unit",datas.get(position).getCname());
                if (!Constant.lru.keySet().contains(datas.get(position).getCurno())){
                    Constant.lru.put(datas.get(position).getCurno(),datas.get(position).getCurno());
                }
                setResult(100,intent);
                finish();
            }
        });
        recycler.addItemDecoration(new AlRecyclerViewDecoration(getResources(), R.color.small_text_color,
                R.dimen.dp_1, LinearLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);

        if (NetworkUtils.getNetworkType()== NetworkUtils.NetworkType.NETWORK_NO){
            getDataWithNoWIfi();
        }else {
            getdata();
        }
    }


    private void getdata(){
        HashMap<String,Object> map=new HashMap<>();
        HttpRequestUtils.getInstance().getNovate().executeGet("/client/gethuilv.php", map, new Novate.ResponseCallBack<Object>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(int code, String msg, Object response, String originalResponse) throws JSONException {

                SelectCountryBean selectCountryBean= BaseUtils.getGson().fromJson(originalResponse,SelectCountryBean.class);

                List<ExchangeBean> results=selectCountryBean.getResult();
                for (ExchangeBean result:results){
                    ExChangeMultipleItem dataBean=new ExChangeMultipleItem(3);
                    dataBean.setCname(result.getType());
                    datas.add(dataBean);
                    datas.addAll(result.getData());
                }

                adapter.setDatas(datas);

                for (int i=0;i<datas.size();i++){
                    if (datas.get(i).getCname().equals(cname)){
                        recycler.scrollToPosition(i);
                    }
                }
            }
        });
    }


    public void getDataWithNoWIfi(){
        ExChangeMultipleItem dataBeanA=new ExChangeMultipleItem(3);
        dataBeanA.setCname("亚洲地区");
        datas.add(dataBeanA);
        datas.addAll(DaoUntils.queryByQuyu("亚洲"));

        ExChangeMultipleItem dataBeanB=new ExChangeMultipleItem(3);
        dataBeanB.setCname("欧洲地区");
        datas.add(dataBeanB);
        datas.addAll(DaoUntils.queryByQuyu("欧洲"));

        ExChangeMultipleItem dataBeanC=new ExChangeMultipleItem(3);
        dataBeanC.setCname("美洲地区");
        datas.add(dataBeanC);
        datas.addAll(DaoUntils.queryByQuyu("美洲"));

        ExChangeMultipleItem dataBeanD=new ExChangeMultipleItem(3);
        dataBeanD.setCname("大洋洲地区");
        datas.add(dataBeanD);
        datas.addAll(DaoUntils.queryByQuyu("大洋洲"));

        ExChangeMultipleItem dataBeanE=new ExChangeMultipleItem(3);
        dataBeanE.setCname("非洲地区");
        datas.add(dataBeanE);
        datas.addAll(DaoUntils.queryByQuyu("非洲"));
        adapter.setDatas(datas);
        Log.i("cccccccccc","cname==="+cname);

        for (int i=0;i<datas.size();i++){

            if (datas.get(i).getCname().equals(cname)){
                recycler.scrollToPosition(i);
            }
        }
    }
}
