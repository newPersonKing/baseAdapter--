package com.system.translationpen.settings.time;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.algorithm.android.utils.DateUtils;
import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getTimeDate;

public class TimeMakeDateActivity extends BaseTitleActivity {

    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_day)
    TextView tv_day;

    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_sure)
    Button btn_sure;

    int year;
    int month;
    int day;
    List<String> thirty=new ArrayList<>();
    @Override
    protected int onSetContentView() {
        return R.layout.activity_make_date;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getTimeDate(0),0);

        thirty.add("4");thirty.add("6");thirty.add("9");thirty.add("11");
        String time=DateUtils.getDate(System.currentTimeMillis(),DateUtils.yyyy_MM_dd);
        String[] strings=time.split("-");
        tv_year.setText(strings[0]);tv_month.setText(strings[1]);tv_day.setText(strings[2]);
    }

    @OnClick({
            R.id.year_up,
            R.id.month_up,
            R.id.day_up,
            R.id.year_down,
            R.id.month_down,
            R.id.day_down,
            R.id.btn_cancel,
            R.id.btn_sure
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.year_up:
                year=Integer.parseInt(tv_year.getText().toString())+1;
                tv_year.setText(year+"");
                break;
            case R.id.month_up:
                month=Integer.parseInt(tv_month.getText().toString())+1;
                if (month==13){
                    month=1;
                }
                tv_month.setText(getTime(month+""));
                break;
            case R.id.day_up:
                day=Integer.parseInt(tv_day.getText().toString())+1;
                if (day>checkDay()){
                    day=1;
                }
                tv_day.setText(getTime(day+""));
                break;
            case R.id.year_down:
                year=Integer.parseInt(tv_year.getText().toString())-1;
                tv_year.setText(year+"");
                break;
            case R.id.month_down:
                month=Integer.parseInt(tv_month.getText().toString())-1;
                if (month==0){
                    month=12;
                }
                tv_month.setText(getTime(month+""));
                break;
            case R.id.day_down:
                day=Integer.parseInt(tv_day.getText().toString())-1;
                if (day==0){
                    day=checkDay();
                }
                tv_day.setText(getTime(day+""));
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_sure:
                String date=tv_year.getText().toString()+"年"+tv_month.getText().toString()+"月"+tv_day.getText().toString()+"日";
                Intent intent=new Intent();
                intent.putExtra("date",date);
                setResult(100,intent);
                finish();
                break;
        }
    }

    private String getTime(String time){
        if (time.length()==1){
            return "0"+time;
        }
        return time;
    }

    private int checkDay(){
        if (month==2){
            if (year%4==0){
                return 29;
            }else {
                return 28;
            }
        }else {
            if (thirty.contains(month+"")){
                return 30;
            }else {
                return 31;
            }
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        btn_cancel.setText(getTimeDate(1));
        btn_sure.setText(getTimeDate(2));
    }
}
