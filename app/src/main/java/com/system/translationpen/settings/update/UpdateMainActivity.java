package com.system.translationpen.settings.update;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.http.HttpRequestUtils;
import com.system.translationpen.settings.equipment.EquipmentUntil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getSystemUpdate;

public class UpdateMainActivity extends BaseTitleActivity {

    @BindView(R.id.tv_version)
    TextView tv_version;

    @BindView(R.id.tv_current_version_name)
    TextView tv_current_version_name;
    @BindView(R.id.btn_sure)
    TextView btn_sure;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_update_main;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getSystemUpdate(0),0);
        tv_version.setText(EquipmentUntil.Instancce(this).getAppVersionName());
    }

    @OnClick({
            R.id.btn_sure
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();

        switch (v.getId()){
            case R.id.btn_sure:
                /*todo 这里检测版本 根据不同的检测结果跳转不同的页面*/
                intent.setClass(this,CheckResultActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        tv_current_version_name.setText(getSystemUpdate(1));
        btn_sure.setText(getSystemUpdate(2));
    }
}
