package com.system.translationpen.exchangerate.activity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.algorithm.android.utils.AlRecyclerViewDecoration;
import com.system.translationpen.R;
import com.system.translationpen.exchangerate.Constant;
import com.system.translationpen.exchangerate.adapter.ExchangeAdapter;
import com.system.translationpen.exchangerate.adapter.MultipleItemQuickAdapter;
import com.system.translationpen.exchangerate.bean.ExChange;
import com.system.translationpen.exchangerate.bean.ExChangeMultipleItem;
import com.system.translationpen.exchangerate.bean.ExchangeBean;
import com.system.translationpen.exchangerate.bean.FirstPageBean;
import com.system.translationpen.exchangerate.bean.SelectCountryBean;
import com.system.translationpen.exchangerate.until.Constants;
import com.system.translationpen.exchangerate.until.DaoUntils;
import com.system.translationpen.exchangerate.until.ExpressionHandler;
import com.system.translationpen.exchangerate.until.NetworkUtils;
import com.system.translationpen.exchangerate.until.SPUtils;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.http.HttpRequestUtils;
import com.system.translationpen.utils.BaseUtils;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.util.NetworkUtil;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExchangeActivity extends BaseTitleActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;


    private ExchangeAdapter adapter;
    List<ExChangeMultipleItem> datas=new ArrayList<>();
    List<ExChangeMultipleItem> lsdaras=new ArrayList<>();


    private List<ExChangeMultipleItem> allDatas=new ArrayList<>();
    private int changePosition;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_exchange;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,"汇率计算",0);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ExchangeAdapter(this,datas ,new ExchangeAdapter.ExchangeAdapterCallBack() {
            @Override
            public void onCountryClick(int position) {
                changePosition=position;
                Intent intent=new Intent(ExchangeActivity.this,ExChangeSelectActivity.class);
                startActivityForResult(intent,200);
            }

            @Override
            public void onMoneyClick(int position) {
                changePosition=position;
                Intent intent=new Intent(ExchangeActivity.this,CalculateActivity.class);
                startActivityForResult(intent,300);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        if (requestCode==200){
            if (data!=null){
                String countryName=data.getStringExtra("CName");
                String Curno=data.getStringExtra("Curno");
                String Middle=data.getStringExtra("Middle");
                String unit=data.getStringExtra("unit");
                /*这里有个神坑 貌似grenndao会自动同步*/
                ExChangeMultipleItem newItem=new ExChangeMultipleItem();
                newItem.setCurno(Curno);
                newItem.setCname(countryName);
                newItem.setSsRote("100");
                newItem.setMiddle(Middle);
                datas.set(changePosition,newItem);

                for (int i=0;i<datas.size();i++){
                    if (changePosition==i)continue;
                    ExChangeMultipleItem item=datas.get(i);
                    Double mix=getMiddle(item.getCurno());
                    Double mis=Double.parseDouble(Middle);
                    Double des=mix/mis*100;
                    DecimalFormat format = new DecimalFormat("0.00");
                    item.setSsRote(format.format(des));
                    /*更新数据库*/
                    FirstPageBean firstPageBean=new FirstPageBean();
                    firstPageBean.setCurno(item.getCurno());
                    firstPageBean.setSsRote(item.getSsRote());
                    firstPageBean.setMiddle(item.getMiddle());
                    DaoUntils.upadteByCurno(firstPageBean);
                }
                /*更新数据库*/
                FirstPageBean firstPageBean=new FirstPageBean();
                firstPageBean.setPosition(changePosition);
                firstPageBean.setCurno(Curno);
                firstPageBean.setSsRote("100");
                firstPageBean.setMiddle(Middle);
                DaoUntils.updateFirstBean(firstPageBean);
                adapter.notifyDataSetChanged();
            }
        }else if (requestCode==300){
            String ssrote=data.getStringExtra("ssrote");
            datas.get(changePosition).setSsRote(ssrote);

            for (int i=0;i<datas.size();i++){
                if (changePosition==i)continue;
                ExChangeMultipleItem item=datas.get(i);
                Double mix=getMiddle(item.getCurno());
                Double mis=Double.parseDouble(datas.get(changePosition).getMiddle());
                Log.i("ccccccccccccccc","mix==="+mix+"mis===="+mis+"changePosition=="+changePosition+"==="+datas.get(changePosition).getCurno());
                Double des=mix/mis*Double.parseDouble(ssrote);
                DecimalFormat format = new DecimalFormat("0.00");
                item.setSsRote(format.format(des));

                /*更新数据库*/
                FirstPageBean firstPageBean=new FirstPageBean();
                firstPageBean.setCurno(item.getCurno());
                firstPageBean.setSsRote(item.getSsRote());
                firstPageBean.setMiddle(item.getMiddle());
                DaoUntils.upadteByCurno(firstPageBean);
            }

            /*更新数据库*/
            FirstPageBean firstPageBean=new FirstPageBean();
            firstPageBean.setPosition(changePosition);
            firstPageBean.setCurno(datas.get(changePosition).getCurno());
            firstPageBean.setSsRote(ssrote);
            firstPageBean.setMiddle(datas.get(changePosition).getMiddle());
            firstPageBean.setPosition(changePosition);
            DaoUntils.updateFirstBean(firstPageBean);
            adapter.notifyDataSetChanged();
        }
    }

    private Double getMiddle(String key){
        for (int i=0; i<allDatas.size();i++){
            if (allDatas.get(i).getCurno().equals(key)){
                return Double.parseDouble(allDatas.get(i).getMiddle());
            }
        }
        return 0.0;
    }

    private void getdata(){
        datas.clear();
        lsdaras.clear();

        HashMap<String,Object> map=new HashMap<>();
        HttpRequestUtils.getInstance().getNovate().executeGet("/client/gethuilv.php", map, new Novate.ResponseCallBack<Object>() {
            @Override
            public void onStart() { }

            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onSuccess(int code, String msg, Object response, String originalResponse) throws JSONException {
                List<FirstPageBean> firstPageBeans=DaoUntils.loadAllFirstBean();
                SelectCountryBean selectCountryBean= BaseUtils.getGson().fromJson(originalResponse,SelectCountryBean.class);

                List<ExchangeBean> results=selectCountryBean.getResult();
                for (ExchangeBean result:results){
                    String quyu=result.getType();
                    for (ExChangeMultipleItem item:result.getData()){
                        item.setQuyu(quyu);
                        allDatas.add(item);

                        /*这里只做实时刷新middle 汇率*/
                        if (item.getCurno().equals("USD")){
//                            lsdaras.add(item);
                            FirstPageBean firstPageBean=firstPageBeans.get(0);
                            firstPageBean.setMiddle(item.getMiddle());
                            DaoUntils.updateMidddle(firstPageBean);
                        }
                        if (item.getCurno().equals("CNY")){
//                            lsdaras.add(item);
                            FirstPageBean firstPageBean=firstPageBeans.get(1);
                            firstPageBean.setMiddle(item.getMiddle());
                            DaoUntils.updateMidddle(firstPageBean);
                        }
                        if (item.getCurno().equals("EUR")){
//                            lsdaras.add(item);
                            FirstPageBean firstPageBean=firstPageBeans.get(2);
                            firstPageBean.setMiddle(item.getMiddle());
                            DaoUntils.updateMidddle(firstPageBean);
                        }
                    }
                }

                for (FirstPageBean firstPageBean:firstPageBeans){
                    ExChangeMultipleItem item=new ExChangeMultipleItem();
                    /*第一次默认显示与美元的汇率*/
                    if (firstPageBean.getMiddle().isEmpty()){
                        item.setSsRote(firstPageBean.getMiddle());
                    }else {
                        item.setSsRote(firstPageBean.getSsRote());
                    }
                    item.setSsRote(firstPageBean.getSsRote());
                    item.setMiddle(firstPageBean.getMiddle());
                    item.setCurno(firstPageBean.getCurno());
                    datas.add(item);
                }

                adapter.notifyDataSetChanged();

                if (Constant.isFirst){
                    DaoUntils.insertItems(allDatas);
                }
                Constant.isFirst=false;
            }
        });
    }

    public void getDataWithNoWIfi(){
        allDatas=DaoUntils.getAll();
        List<FirstPageBean> firstPageBeans=DaoUntils.loadAllFirstBean();
        datas.clear();
        lsdaras.clear();
        for (ExChangeMultipleItem item:allDatas){
            if (item.getCurno().equals(firstPageBeans.get(0).getCurno())||item.getCurno().equals(firstPageBeans.get(1).getCurno())||item.getCurno().equals(firstPageBeans.get(2).getCurno())){
                lsdaras.add(item);
            }
        }



        for (ExChangeMultipleItem item:lsdaras){
            if (item.getCurno().equals(firstPageBeans.get(0).getCurno())){
                datas.add(item);
            }
        }

        for (ExChangeMultipleItem item:lsdaras){
            if (item.getCurno().equals(firstPageBeans.get(1).getCurno())){
                datas.add(item);
            }
        }

        for (ExChangeMultipleItem item:lsdaras){
            if (item.getCurno().equals(firstPageBeans.get(2).getCurno())){
                datas.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
