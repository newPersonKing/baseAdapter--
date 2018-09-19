package com.system.translationpen.settings.time;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algorithm.android.utils.DateUtils;
import com.fanwe.lib.switchbutton.FSwitchButton;
import com.fanwe.lib.switchbutton.SwitchButton;
import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getTimeMain;

public class TimeMainActivity extends BaseTitleActivity {

    @BindView(R.id.time_switch)
    FSwitchButton time_switch;
    @BindView(R.id.ll_time_date)
    LinearLayout ll_time_date;
    @BindView(R.id.ll_time_time)
    LinearLayout ll_time_time;
    @BindView(R.id.tv_time_time)
    TextView tv_time_time;
    @BindView(R.id.tv_time_date)
    TextView tv_time_date;

    @BindView(R.id.tv_turn_on_name)
    TextView tv_turn_on_name;
    @BindView(R.id.tv_Automatic_name)
    TextView tv_Automatic_name;
    @BindView(R.id.tv_date_name)
    TextView tv_date_name;
    @BindView(R.id.tv_time_name)
    TextView tv_time_name;
    @BindView(R.id.tv_double_time_name)
    TextView tv_double_time_name;
    @BindView(R.id.tv_double_time_name_t)
    TextView tv_double_time_name_t;
    @BindView(R.id.tv_double_time_show_name)
    TextView tv_double_time_show_name;


    boolean isChecked=false;
    @Override
    protected int onSetContentView() {
        return R.layout.activity_time_main;
    }

    @Override
    protected void onInitData() {


        time_switch.setChecked(true,false,false);
        time_switch.setOnCheckedChangeCallback(new SwitchButton.OnCheckedChangeCallback() {
            @Override
            public void onCheckedChanged(boolean checked, SwitchButton view) {
                ll_time_date.setClickable(!checked);
                ll_time_time.setClickable(!checked);
            }
        });
        if (isChecked){
            ll_time_date.setClickable(false);
            ll_time_time.setClickable(false);
        }

        String date=DateUtils.getDate(System.currentTimeMillis(),DateUtils.yyyy_MM_dd_HH_mm);
        String[] dates=date.split(" ");
        tv_time_date.setText(dates[0]);
        tv_time_time.setText(dates[1]);
    }

    @OnClick({
            R.id.ll_time_date,
            R.id.ll_time_time,
            R.id.ll_double_time
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.ll_time_date:
                if (!time_switch.isChecked()) {
                    intent.setClass(this, TimeMakeDateActivity.class);
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.ll_time_time:
                if (!time_switch.isChecked()){
                    intent.setClass(this,TimeMakeTimeActivity.class);
                    startActivityForResult(intent,200);
                }
                break;
            case R.id.ll_double_time:
                if (!time_switch.isChecked()){
                    intent.setClass(this,DoubleTimeActivity.class);
                    startActivityForResult(intent,200);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){

            if (requestCode==100){
                tv_time_date.setText(data.getStringExtra("date"));
            }

            if (requestCode==200){
                tv_time_time.setText(data.getStringExtra("time"));
            }
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        setTitle(R.drawable.wback,getTimeMain(0),0);
        tv_turn_on_name.setText(getTimeMain(1));
        tv_Automatic_name.setText(getTimeMain(2));
        tv_date_name.setText(getTimeMain(3));
        tv_time_name.setText(getTimeMain(4));
        tv_double_time_name.setText(getTimeMain(5));
        tv_double_time_name_t.setText(getTimeMain(6));
        tv_double_time_show_name.setText(getTimeMain(7));
    }
}
