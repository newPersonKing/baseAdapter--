package com.system.translationpen.exchangerate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.system.translationpen.R;
import com.system.translationpen.exchangerate.until.Constants;
import com.system.translationpen.exchangerate.until.ExpressionHandler;
import com.system.translationpen.hotspot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CalculateActivity extends BaseActivity {


    private Thread calcThread;
    @BindView(R.id.et_number)
    EditText inputtext;
    @BindView(R.id.key_jisuan)
    LinearLayout key_jisuan;
    @BindView(R.id.img_alpha)
    ImageView img_alpha;
    @BindView(R.id.ll_keybord)
    LinearLayout ll_keybord;

    boolean isFinish=false;
    @Override
    protected int onSetContentView() {
        return R.layout.exchange_calculate;
    }

    @Override
    protected void onInitData() {
        img_alpha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                key_jisuan.setVisibility(View.GONE);
                img_alpha.setVisibility(View.GONE);
                return true;
            }
        });
    }

    @OnClick({
            R.id.key0,
            R.id.key1,
            R.id.key2,
            R.id.key3,
            R.id.key4,
            R.id.key5,
            R.id.key6,
            R.id.key7,
            R.id.key8,
            R.id.key9,
            R.id.iv_jia,
            R.id.iv_jian,
            R.id.iv_cheng,
            R.id.iv_chu,
            R.id.keyd,
            R.id.keyF,
            R.id.del,
            R.id.clear,
            R.id.btn_sure,
            R.id.img_alpha
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.del:
                Editable editable = inputtext.getText();
                int index = inputtext.getSelectionStart();
                int index2 = inputtext.getSelectionEnd();
                if (index == index2) {
                    if (index == 0) return;
                    editable.delete(index - 1, index);
                } else {
                    editable.delete(index, index2);
                }
                break;
            case R.id.clear:
                break;
            case R.id.key0:
                modifyInText("0");
                break;
            case R.id.key1:
                modifyInText("1");
                break;
            case R.id.key2:
                modifyInText("2");
                break;
            case R.id.key3:
                modifyInText("3");
                break;
            case R.id.key4:
                modifyInText("4");
                break;
            case R.id.key5:
                modifyInText("5");
                break;
            case R.id.key6:
                modifyInText("6");
                break;
            case R.id.key7:
                modifyInText("7");
                break;
            case R.id.key8:
                modifyInText("8");
                break;
            case R.id.key9:
                modifyInText("9");
                break;
            case R.id.keyd:
                modifyInText(".");
                break;

            case R.id.iv_jia:
                modifyInText("+");
                key_jisuan.setVisibility(View.GONE);
                img_alpha.setVisibility(View.GONE);
                break;
            case R.id.iv_jian:
                key_jisuan.setVisibility(View.GONE);
                img_alpha.setVisibility(View.GONE);
                modifyInText("-");
                break;
            case R.id.iv_cheng:
                key_jisuan.setVisibility(View.GONE);
                img_alpha.setVisibility(View.GONE);
                modifyInText("*");
                break;
            case R.id.iv_chu:
                key_jisuan.setVisibility(View.GONE);
                img_alpha.setVisibility(View.GONE);
                modifyInText("/");
                break;
            case R.id.keyF:
                getResult();
                key_jisuan.setVisibility(View.VISIBLE);
                img_alpha.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_sure:
                isFinish=true;
                getResult();
                break;
            case R.id.img_alpha:
                key_jisuan.setVisibility(View.GONE);
                img_alpha.setVisibility(View.GONE);
                break;
        }
    }

    private void modifyInText(String str) {
        int index = inputtext.getSelectionStart();
        int index2 = inputtext.getSelectionEnd();
        if (index == index2) {
            inputtext.getText().insert(index, str);
        } else {
            inputtext.getText().replace(index, index2, str);
        }
    }

    private void getResult(){
        calcThread = new Calc(inputtext.getText().toString());
        calcThread.start();
    }

    class Calc extends Thread implements Runnable {
        private String exp;

        public Calc(String exp) {
            this.exp = exp;
        }

        @Override
        public void run() {
            final long t = System.currentTimeMillis();
            final String[] value = ExpressionHandler.calculation(exp);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (value[1].equals("true")) {


                    } else {
                        Constants.setAns(value[0]);
                        if (value[0].getBytes().length > 1000) {
                            Toast.makeText(getApplicationContext(),"数值太大",Toast.LENGTH_SHORT).show();
                        } else{}
                    }
                    if (isFinish){
                        Intent intent=new Intent();
                        intent.putExtra("ssrote",value[0]);
                        setResult(100,intent);
                        finish();
                    }else {
                        if (value[0].length()>10){
                            return;
                        }else {
                            inputtext.setText(value[0]);
                            inputtext.setSelection(value[0].length());
                        }
                    }

                    calcThread = null;
                }
            });
        }

    }
}
