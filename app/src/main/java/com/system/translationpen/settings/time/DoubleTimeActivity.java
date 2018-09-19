package com.system.translationpen.settings.time;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.lib.switchbutton.FSwitchButton;
import com.fanwe.lib.switchbutton.SwitchButton;
import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.SPUtils;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.untils.ObjectUtils;

import org.apache.poi.hssf.record.formula.functions.T;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getDoubleTimeMain;
import static com.system.translationpen.settings.Constant.getHintMessage;

public class DoubleTimeActivity extends BaseTitleActivity{

    @BindView(R.id.double_time_switch)
    FSwitchButton double_time_switch;
    @BindView(R.id.ll_double_time)
    LinearLayout ll_double_time;
    @BindView(R.id.tv_double_time)
    TextView tv_double_time;

    @BindView(R.id.turn_on_name)
    TextView turn_on_name;
    @BindView(R.id.tv_double_clock_name)
    TextView tv_double_clock_name;
    @BindView(R.id.tv_make_ori_name)
    TextView tv_make_ori_name;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_double__time;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getDoubleTimeMain(0),0);

        String fix_time= (String) SPUtils.getInstance().get("FIX_TIME","");
        if (ObjectUtils.isNull(fix_time)){
            tv_double_time.setText(getHintMessage(7));
        }else {
            tv_double_time.setText(fix_time);
        }

        boolean isDouble= (boolean) SPUtils.getInstance().get("isDouble",false);

        if (isDouble){
            double_time_switch.setChecked(true,false,false);
            ll_double_time.setClickable(true);
        }else {
            double_time_switch.setChecked(false,false,false);
            ll_double_time.setClickable(false);
        }

        double_time_switch.setOnCheckedChangeCallback(new SwitchButton.OnCheckedChangeCallback() {
            @Override
            public void onCheckedChanged(boolean checked, SwitchButton view) {
                ll_double_time.setClickable(checked);
                SPUtils.getInstance().set("isDouble",checked);
            }
        });
    }
    @OnClick({
            R.id.ll_double_time
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.ll_double_time:
                intent.setClass(this,FixedRegionActivity.class);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String time=data.getStringExtra("time");
            String address=data.getStringExtra("address");
            tv_double_time.setText(address+"("+time+")");
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        turn_on_name.setText(getDoubleTimeMain(1));
        tv_double_clock_name.setText(getDoubleTimeMain(2));
        tv_make_ori_name.setText(getDoubleTimeMain(3));
    }
}
