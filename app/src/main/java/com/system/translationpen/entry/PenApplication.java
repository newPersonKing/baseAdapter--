package com.system.translationpen.entry;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.gy.permission.Utils;
import com.system.translationpen.exchangerate.Constant;
import com.system.translationpen.exchangerate.bean.FirstPageBean;
import com.system.translationpen.exchangerate.until.DaoUntils;
import com.system.translationpen.exchangerate.until.NetworkUtils;
import com.system.translationpen.exchangerate.until.SPUtils;
import com.system.translationpen.hotspot.manager.BluetoothController;
import com.system.translationpen.hotspot.manager.WifiController;
import com.system.translationpen.utils.BaseUtils;

/**
 * Created by huison on 2018/5/28.
 */

public class PenApplication extends MultiDexApplication {

    private static PenApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        WifiController.instance().init(this);
        BaseUtils.init(this);
        Constant.init();
        DaoUntils.init(this);
        NetworkUtils.init(this);
        SPUtils.init(this);
        BluetoothController.instance().init(this);


        DaoUntils.insertFirstBean(getFirstBean(0,"USD"));
        DaoUntils.insertFirstBean(getFirstBean(1,"CNY"));
        DaoUntils.insertFirstBean(getFirstBean(2,"EUR"));

        Utils.init(this);

        SPUtils.getInstance().set("language",2);
    }

    public static Context context() {
        return context;
    }

    public static SharedPreferences getSharedPreferences() {
        return context().getSharedPreferences("caesar_app", MODE_APPEND);
    }

    public FirstPageBean getFirstBean(int position,String Curno){
        FirstPageBean firstPageBean=new FirstPageBean();
        firstPageBean.setPosition(position);
        firstPageBean.setCurno(Curno);
        return firstPageBean;
    }
}
