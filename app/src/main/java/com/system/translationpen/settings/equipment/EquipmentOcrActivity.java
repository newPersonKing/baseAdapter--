package com.system.translationpen.settings.equipment;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.zxing.util.CodeUtils;
import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import butterknife.BindView;

public class EquipmentOcrActivity extends BaseTitleActivity {

    @BindView(R.id.iv_equipment_orc)
    ImageView iv_equipment_orc;


    @Override
    protected int onSetContentView() {
        return R.layout.activity_equipment_ocr;
    }


    @Override
    protected void onInitData() {


        String content=EquipmentUntil.Instancce(this).getDeviceInfo();
        Bitmap bitmap=CodeUtils.createQRCode(content,600,null);
        iv_equipment_orc.setImageBitmap(bitmap);

    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        setTitle(R.drawable.wback,"设备二维码",0);
    }
}
