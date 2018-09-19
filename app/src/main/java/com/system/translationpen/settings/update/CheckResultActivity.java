package com.system.translationpen.settings.update;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getLanguage;
import static com.system.translationpen.settings.Constant.getUpdateNo;

public class CheckResultActivity extends BaseTitleActivity {

    @BindView(R.id.tv_find_new_version)
    TextView tv_find_new_version;
    @BindView(R.id.tv_update_daily_name)
    TextView tv_update_daily_name;

    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.btn_update)
    Button btn_update;


    @Override
    protected int onSetContentView() {
        return R.layout.activity_check_result_has;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getUpdateNo(0),0);

        if (getLanguage()==1){
            tv_find_new_version.setText(getResources().getString(R.string.fund_new_version_c,"1.0.2"));
        }else {
            tv_find_new_version.setText(getResources().getString(R.string.fund_new_version_e,"1.0.2"));
        }

    }

    @OnClick({
            R.id.btn_back,
            R.id.btn_update
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_update:
                intent.setClass(this,DownActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
        tv_update_daily_name.setText(getUpdateNo(1));
        btn_back.setText(getUpdateNo(2));
        btn_update.setText(getUpdateNo(3));
    }
}
