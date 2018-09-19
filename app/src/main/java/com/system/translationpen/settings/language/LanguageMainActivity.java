package com.system.translationpen.settings.language;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.SPUtils;
import com.system.translationpen.hotspot.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.system.translationpen.settings.Constant.getLanguage;
import static com.system.translationpen.settings.Constant.getLanguageMain;

public class LanguageMainActivity extends BaseTitleActivity {

    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private int language=1;
    @Override
    protected int onSetContentView() {
        return R.layout.activity_language_main;
    }

    @Override
    protected void onInitData() {

        if (getLanguage()==1){
          RadioButton radioButton= (RadioButton) radioGroup.getChildAt(0);
            radioButton.setChecked(true);
        }else {
            RadioButton radioButton= (RadioButton) radioGroup.getChildAt(2);
            radioButton.setChecked(true);
        }

        setTitle(R.drawable.wback,getLanguageMain(0),0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                  switch (checkedId){
                      case R.id.rt_one:
                          language=1;
                          break;
                      case R.id.rt_two:
                          break;
                      case R.id.rt_three:
                          language=2;
                          break;
                  }
            }
        });
    }

    @OnClick({
            R.id.btn_sure
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_sure:
                SPUtils.getInstance().set("language",language);
                Toast.makeText(this,"设置成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void refreshLanguage() {
        super.refreshLanguage();
        btn_sure.setText(getLanguageMain(1));
    }
}
