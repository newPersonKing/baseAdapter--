package com.system.translationpen.exchangerate.activity;

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
import java.util.Set;

import butterknife.BindView;

public class ExChangeSelectActivity extends BaseTitleActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;


    private List<ExChangeMultipleItem> datas=new ArrayList<>();

    private List<String> changyong=new ArrayList<>();
    private List<String> yazhou=new ArrayList<>();
    private List<String> ouzhou=new ArrayList<>();
    private List<String> meizhou=new ArrayList<>();
    private List<String> dayangzhou=new ArrayList<>();
    private List<String> feizhou=new ArrayList<>();

    private List<ExChangeMultipleItem> changyongAs=new ArrayList<>();
    private List<ExChangeMultipleItem> yazhouAs=new ArrayList<>();
    private List<ExChangeMultipleItem> ouzhouAs=new ArrayList<>();
    private List<ExChangeMultipleItem> meizhouAs=new ArrayList<>();
    private List<ExChangeMultipleItem> dayangzhouAs=new ArrayList<>();
    private List<ExChangeMultipleItem> feizhouAs=new ArrayList<>();


    private MultipleItemQuickAdapter adapter;

    private List<ExChangeMultipleItem> allDatas=new ArrayList<>();
    @Override
    protected int onSetContentView() {
        return R.layout.exchange_select_layout;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,"货币选择",0);
        initOrigin();

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MultipleItemQuickAdapter(this, new MultipleItemQuickAdapter.MultipleItemQuickAdapterCallBack() {
            @Override
            public void onClickMore(String cname) {
                Intent intent=new Intent(ExChangeSelectActivity.this,AllCountryActivity.class);
                intent.putExtra("cname",cname);
                startActivityForResult(intent,200);
            }

            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent();
                intent.putExtra("CName",datas.get(position).getCname());
                intent.putExtra("Curno",datas.get(position).getCurno());
                intent.putExtra("Middle",datas.get(position).getMiddle());
                intent.putExtra("unit",datas.get(position).getCname());
                setResult(100,intent);
                if (!Constant.lru.keySet().contains(datas.get(position).getCurno())){
                    Constant.lru.put(datas.get(position).getCurno(),datas.get(position).getCurno());
                }
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

    private void initOrigin(){

        changyong.add("USD");changyong.add("CNY");changyong.add("EUR");changyong.add("HKD");
        yazhou.add("CNY");yazhou.add("HKD");yazhou.add("JPY");yazhou.add("TWD");
        ouzhou.add("EUR");ouzhou.add("GBP");ouzhou.add("SEK");ouzhou.add("RUB");
        meizhou.add("USD");meizhou.add("CAD");meizhou.add("ASA");meizhou.add("CUP");
        dayangzhou.add("AUD");dayangzhou.add("NZD");
        feizhou.add("ZAR");feizhou.add("EGP");feizhou.add("XAF");

    }

    private void getdata(){
        changyongAs.clear();
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
                Log.i("cccccccccccc","e======"+e.getMessage());
            }

            @Override
            public void onSuccess(int code, String msg, Object response, String originalResponse) throws JSONException {
                SelectCountryBean selectCountryBean= BaseUtils.getGson().fromJson(originalResponse,SelectCountryBean.class);
                List<ExchangeBean> results=selectCountryBean.getResult();

                for (ExchangeBean result:results){
                    for (ExChangeMultipleItem item:result.getData()){
                        String curno=item.getCurno();
                        Set<String> sets=Constant.lru.keySet();
                        if (sets.contains(curno)){
                            changyongAs.add(item);
                        }

                        if (yazhou.indexOf(curno)!=-1){
                            yazhouAs.add(item);
                        }

                        if (ouzhou.indexOf(curno)!=-1){
                            ouzhouAs.add(item);
                        }

                        if (meizhou.indexOf(curno)!=-1){
                            meizhouAs.add(item);
                        }

                        if (dayangzhou.indexOf(curno)!=-1){
                            dayangzhouAs.add(item);
                        }
                        if (feizhou.indexOf(curno)!=-1){
                            feizhouAs.add(item);
                        }
                    }
                }

                ExChangeMultipleItem changyongQy=new ExChangeMultipleItem(1);
                changyongQy.setCname("常用");
                datas.add(changyongQy);
                datas.addAll(changyongAs);

                ExChangeMultipleItem yazhouQy=new ExChangeMultipleItem(1);
                yazhouQy.setCname("亚洲地区");
                datas.add(yazhouQy);
                datas.addAll(yazhouAs);

                ExChangeMultipleItem ouzhouQy=new ExChangeMultipleItem(1);
                ouzhouQy.setCname("欧洲地区");
                datas.add(ouzhouQy);
                datas.addAll(ouzhouAs);

                ExChangeMultipleItem meizhouQy=new ExChangeMultipleItem(1);
                meizhouQy.setCname("美洲地区");
                datas.add(meizhouQy);
                datas.addAll(meizhouAs);

                ExChangeMultipleItem dayangzhouQy=new ExChangeMultipleItem(1);
                dayangzhouQy.setCname("大洋洲地区");
                datas.add(dayangzhouQy);
                datas.addAll(dayangzhouAs);

                ExChangeMultipleItem feizhouQy=new ExChangeMultipleItem(1);
                feizhouQy.setCname("非洲地区");
                datas.add(feizhouQy);
                datas.addAll(feizhouAs);

                adapter.setDatas(datas);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200){
            if (data!=null){
                String countryName=data.getStringExtra("CName");
                String Curno=data.getStringExtra("Curno");
                String Middle=data.getStringExtra("Middle");
                String unit=data.getStringExtra("unit");
                Intent intent=new Intent();
                intent.putExtra("CName",countryName);
                intent.putExtra("Curno",Curno);
                intent.putExtra("Middle",Middle);
                intent.putExtra("unit",unit);
                setResult(100,intent);
                finish();
            }
        }
    }

    public void getDataWithNoWIfi(){
        changyongAs.clear();
        allDatas= DaoUntils.getAll();
        Set<String> sets=Constant.lru.keySet();

        int i=0;
        Log.i("ccccccccccc","110======"+allDatas.get(110).getCurno());
        for (ExChangeMultipleItem item:allDatas){
            String curno=item.getCurno();

            if (sets.contains(curno)){
                changyongAs.add(item);
            }

            if (yazhou.indexOf(curno)!=-1){
                yazhouAs.add(item);
            }

            if (ouzhou.indexOf(curno)!=-1){
                ouzhouAs.add(item);
            }

            if (meizhou.indexOf(curno)!=-1){
                meizhouAs.add(item);
            }

            if (dayangzhou.indexOf(curno)!=-1){
                dayangzhouAs.add(item);
            }
            if (feizhou.indexOf(curno)!=-1){
                feizhouAs.add(item);
            }
            i++;
        }

        ExChangeMultipleItem changyongQy=new ExChangeMultipleItem(1);
        changyongQy.setCname("常用");
        datas.add(changyongQy);
        datas.addAll(changyongAs);

        ExChangeMultipleItem yazhouQy=new ExChangeMultipleItem(1);
        yazhouQy.setCname("亚洲地区");
        datas.add(yazhouQy);
        datas.addAll(yazhouAs);

        ExChangeMultipleItem ouzhouQy=new ExChangeMultipleItem(1);
        ouzhouQy.setCname("欧洲地区");
        datas.add(ouzhouQy);
        datas.addAll(ouzhouAs);

        ExChangeMultipleItem meizhouQy=new ExChangeMultipleItem(1);
        meizhouQy.setCname("美洲地区");
        datas.add(meizhouQy);
        datas.addAll(meizhouAs);

        ExChangeMultipleItem dayangzhouQy=new ExChangeMultipleItem(1);
        dayangzhouQy.setCname("大洋洲地区");
        datas.add(dayangzhouQy);
        datas.addAll(dayangzhouAs);

        ExChangeMultipleItem feizhouQy=new ExChangeMultipleItem(1);
        feizhouQy.setCname("非洲地区");
        datas.add(feizhouQy);
        datas.addAll(feizhouAs);

        adapter.setDatas(datas);

        adapter.notifyDataSetChanged();
    }
}
