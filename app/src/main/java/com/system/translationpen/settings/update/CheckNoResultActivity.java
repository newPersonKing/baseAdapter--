package com.system.translationpen.settings.update;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.settings.equipment.EquipmentUntil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getLanguage;
import static com.system.translationpen.settings.Constant.getUpdateOk;

public class CheckNoResultActivity extends BaseTitleActivity {
    @BindView(R.id.tv_version_message)
    TextView tv_version_message;
    @BindView(R.id.btn_back)
    Button btn_back;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_check_result_no;
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.wback,getUpdateOk(0),0);
        String version=EquipmentUntil.Instancce(this).getAppVersionName();
        if (getLanguage()==1){
            tv_version_message.setText(getResources().getString(R.string.version_message_c,version));
        }else {
            tv_version_message.setText(getResources().getString(R.string.version_message_e,version));
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();

    }

    @OnClick({
            R.id.btn_back
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
    }
}
