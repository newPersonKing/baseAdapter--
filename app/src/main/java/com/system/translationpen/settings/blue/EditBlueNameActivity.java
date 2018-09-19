package com.system.translationpen.settings.blue;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.manager.BluetoothController;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getBlueMain;
import static com.system.translationpen.settings.Constant.getHintMessage;

public class EditBlueNameActivity extends BaseTitleActivity {

    @BindView(R.id.tv_blue_name)
    TextView tv_blue_name;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.tv_blue_name_name)
    TextView tv_blue_name_name;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_edit_blue_name;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getHintMessage(4),0);

    }

    @OnClick({
            R.id.tv_blue_name,
            R.id.btn_save,
            R.id.btn_cancel
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_blue_name:
                Intent intent=new Intent(this,EnterBlueNameActivity.class);
                startActivityForResult(intent,200);
                break;
            case R.id.btn_cancel:
                finish();
            case R.id.btn_save:
                boolean isSuccess=BluetoothController.instance().setOwnBluetoothName(blue_name);
                if (isSuccess){
                    Toast.makeText(this,getHintMessage(8),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,getHintMessage(9),Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }
    }

    String blue_name="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            blue_name=data.getStringExtra("blue_name");
            tv_blue_name.setText(blue_name);
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        btn_cancel.setText(getHintMessage(5));
        btn_save.setText(getHintMessage(6));
        tv_blue_name_name.setText(getBlueMain(5));
    }
}
