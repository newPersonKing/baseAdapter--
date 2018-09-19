package com.system.translationpen.settings.recovery;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getRecovery;

public class RecoveryResultActivity extends BaseTitleActivity {

    @BindView(R.id.tv_recovery_result_name)
    TextView tv_recovery_result_name;
    @BindView(R.id.btn_restart)
    Button btn_restart;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_recovery_result;
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.wback,getRecovery(0),0);
    }

    @OnClick({
            R.id.btn_restart
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_restart:
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
        tv_recovery_result_name.setText(getRecovery(2));
        btn_restart.setText(getRecovery(3));
    }
}
