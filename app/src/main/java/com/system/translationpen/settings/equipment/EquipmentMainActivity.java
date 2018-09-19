package com.system.translationpen.settings.equipment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.untils.SettingsController;
import com.system.translationpen.utils.FileUtils;
import com.tamic.novate.util.FileUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getDeviceMessage;
import static com.system.translationpen.settings.Constant.getLanguage;

public class EquipmentMainActivity extends BaseTitleActivity {
    @BindView(R.id.tv_system_type)
    TextView tv_system_type;
    @BindView(R.id.tv_system_version)
    TextView tv_system_version;
    @BindView(R.id.tv_run_size)
    TextView tv_run_size;
    @BindView(R.id.tv_usable_size)
    TextView tv_usable_size;
    @BindView(R.id.tv_all_size)
    TextView tv_all_size;

    @BindView(R.id.tv_basic_message)
    TextView tv_basic_message;
    @BindView(R.id.tv_device_ocr)
    TextView tv_device_ocr;
    @BindView(R.id.tv_type_name)
    TextView tv_type_name;
    @BindView(R.id.tv_system_version_name)
    TextView tv_system_version_name;
    @BindView(R.id.tv_storege_message)
    TextView tv_storege_message;
    @BindView(R.id.tv_run_name)
    TextView tv_run_name;
    @BindView(R.id.tv_phone_storage)
    TextView tv_phone_storage;



    @Override
    protected int onSetContentView() {
        return R.layout.activity_equipment_main;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getDeviceMessage(0),0);
        tv_system_type.setText(EquipmentUntil.Instancce(this).getSystemType());
        tv_system_version.setText(EquipmentUntil.Instancce(this).getSystemVersion());
        tv_run_size.setText(FileUtils.byte2FitMemorySize(SettingsController.getTotalMemorySize(this)));

    }

    @OnClick({
            R.id.ll_equipment_orc
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.ll_equipment_orc:
                intent.setClass(this,EquipmentOcrActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
        if (getLanguage()==1){
            tv_usable_size.setText(getResources().getString(R.string.usable_storage_c,FileUtils.byte2FitMemorySize(SettingsController.getAvailableInternalMemorySize())));
            tv_all_size.setText(getResources().getString(R.string.total_storage_c,FileUtils.byte2FitMemorySize(SettingsController.getTotalInternalMemorySize())));
        }else {
            tv_usable_size.setText(getResources().getString(R.string.usable_storage_e,FileUtils.byte2FitMemorySize(SettingsController.getAvailableInternalMemorySize())));
            tv_all_size.setText(getResources().getString(R.string.total_storage_e,FileUtils.byte2FitMemorySize(SettingsController.getTotalInternalMemorySize())));
        }

        tv_basic_message.setText(getDeviceMessage(1));
        tv_device_ocr.setText(getDeviceMessage(2));
        tv_type_name.setText(getDeviceMessage(3));
        tv_system_version_name.setText(getDeviceMessage(4));
        tv_storege_message.setText(getDeviceMessage(5));
        tv_run_name.setText(getDeviceMessage(6));
        tv_phone_storage.setText(getDeviceMessage(7));

    }
}
