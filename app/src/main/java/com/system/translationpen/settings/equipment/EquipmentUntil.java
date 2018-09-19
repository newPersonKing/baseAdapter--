package com.system.translationpen.settings.equipment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

public class EquipmentUntil {

    public static EquipmentUntil instance;

    public static Context mContext;

    public static EquipmentUntil Instancce(Context context){
        mContext=context
                ;
        if (instance==null){
            instance=new EquipmentUntil();
        }
        return instance;
    }

    //获取当前版本号
    public  String getAppVersionName( ){
        String versionName = "";
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("cn.testgethandsetinfo", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /*系统版本号*/
    public  String getSystemVersion(){
        return android.os.Build.VERSION.RELEASE;
    }
    /*获取系统型号*/
    public String getSystemType(){
        return  android.os.Build.MODEL;
    }

    public String getDeviceInfo(){
        StringBuffer sb =new StringBuffer();

        sb.append("手机制造商："+Build.PRODUCT);
        sb.append("\n版本："+Build.MODEL);
        return sb.toString();
    }

}
