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

import static com.system.translationpen.settings.Constant.getTimeTime;

public class TimeMakeTimeActivity extends BaseTitleActivity {

    @BindView(R.id.tv_houre)
    TextView tv_houre;
    @BindView(R.id.tv_minute)
    TextView tv_minute;

    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_sure)
    Button btn_sure;

    int houre;
    int minute;

    List<String> thirty=new ArrayList<>();
    @Override
    protected int onSetContentView() {
        return R.layout.activity_make_time;
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.wback,getTimeTime(0),0);
        thirty.add("4");thirty.add("6");thirty.add("9");thirty.add("11");
        String time=DateUtils.getDate(System.currentTimeMillis(),DateUtils.HH_MM);
        String[] strings=time.split(":");
        tv_houre.setText(strings[0]);tv_minute.setText(strings[1]);
    }

    @OnClick({
            R.id.houre_up,
            R.id.minute_up,
            R.id.houre_down,
            R.id.minute_down,
            R.id.btn_cancel,
            R.id.btn_sure
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.houre_up:
                houre=Integer.parseInt(tv_houre.getText().toString())+1;
                if (houre==24){
                    houre=0;
                }
                tv_houre.setText(getTime(houre+""));
                break;
            case R.id.minute_up:
                minute=Integer.parseInt(tv_minute.getText().toString())+1;
                if (minute==60){
                    minute=1;
                }
                tv_minute.setText(getTime(minute+""));
                break;
            case R.id.houre_down:
                houre=Integer.parseInt(tv_houre.getText().toString())-1;

                if (houre==-1){
                    houre=23;
                }
                tv_houre.setText(getTime(houre+""));
                break;
            case R.id.minute_down:
                minute=Integer.parseInt(tv_minute.getText().toString())-1;
                if (minute==-1){
                    minute=60;
                }
                tv_minute.setText(getTime(minute+""));
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_sure:
                String time=tv_minute.getText().toString()+":"+tv_minute.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("time",time);
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

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        btn_cancel.setText(getTimeTime(1));
        btn_sure.setText(getTimeTime(2));
    }
}
