package com.system.translationpen.settings;

import com.system.translationpen.exchangerate.until.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    static String[] SETTING_MAIN_C=new String[]{"设置","连接","WLAN","蓝牙","显示及声音","亮度","声音","系统","时间","系统语言","系统更新","恢复出厂设置","设备信息"};
    static String[] SETTING_MAIN_E=new String[]{"settings","Connect","WLAN","Bluetooth","Brigntness and voice","Brigntness","voice","system","Date & time","language","update","Facctory data reset","About device"};

    static String[] WIFI_MAIN_C=new String[]{"WLAN","开关","WLAN","可用WLAN表"};
    static String[] WIFI_MAIN_E=new String[]{"WLAN","Turn on or turn off","WLAN","Available networks"};

    static String[] WIFI_MAIN_STATE_C=new String[]{"已连接","加密","未加密"};
    static String[] WIFI_MAIN_STATE_E=new String[]{"Connected","Encrypted","Unencrypted"};

    static String[] WIFI_DETAILS_C= new String[]{"状态信息","信号强度","加密类型","取消","断开"};
    static String[] WIFI_DETAILS_E=new String[]{"status","Signal strength","Security","Cancel","Forget"};

    static String[] WIFI_STRENG_C=new String[]{"强","较强","中","差"};
    static  String[] WIFI_STRENG_E=new String[]{"Excellent","Good","Moderate","Poor"};

    String[] WIFI_ENTRY_TYPE_C=new String[]{"WPA2 PSK","没有"};
    String[] WIFI_ENTRY_TYPE_E=new String[]{"WPA2 PSK","NO"};

    static String[] WIFI_PASSWORD_C=new String[]{"信号强度","加密类型","密码","取消","连接"};
    static String[] WIFI_PASSWORD_E=new String[]{"Signal strength","Security","Password","Cancel","Connect"};


    static String[] BLUE_MAIN_C=new String[]{"蓝牙","开关","蓝牙","开放检测","允许周围设备检测到","设备名称","可用设备"};
    static String[] BLUE_MAIN_E=new String[]{"Bluetooth","Turn on or turn off","Turn on Bluetooth","Visibility","Visible to devices nearby","Device name","Available devices"};

    static String[] LIGHT_C= new String[]{"亮度"};
    static String[] LIGHT_E=new String[]{"Brigntness"};

    static String[] VOICE_C= new String[]{"声音","按键声音","媒体声音"};
    static String[] VOICE_E=new String[]{"Sound","Button sound","Media sound"};

    static String[] TIME_MAIN_C=new String[]{"时间","开关","自动确定时区","日期","时间","双时钟","双时钟","在锁屏界面显示双时钟"};
    static String[] TIME_MAIN_E=new String[]{"Date & time","Turn on or turn off","Automatic date & time","Set date","Set time","Double clock","Double clock","Display dual clock on lock screen"};

    static String[] TIME_DAY_C= new String[]{"日期","取消","确定"};
    static String[] TIME_DAY_E= new String[]{"Date","Cancel","OK"};

    static String[] TIME_TIME_C= new String[]{"时间","取消","确定"};
    static String[] TIME_TIME_E= new String[]{"time","Cancel","OK"};

    static String[] DOUBLE_CLOCK_C= new String[]{"双时钟","开关","双时钟","设置固定时区"};
    static String[] DOUBLE_CLOCK_E= new String[]{"Double clock","Turn on or turn off","Double clock","Select time zone"};

    static String[] SELECT_ORI_C= new String[]{"设置固定时区"};
    static String[] SELECT_ORI_E= new String[]{"Select time zone"};

    static String[] SYSTEM_LANGUAGE_C = new String[]{"系统语言","确定"};
    static String[] SYSTEM_LANGUAGE_E=new String[]{"System language","OK"};

    static String[] SYSTEM_UPDATE_C= new String[]{"系统更新","当前版本","确定"};
    static String[] SYSTEM_UPDATE_E= new String[]{"Updater","Current version","OK"};

    static String[] UPDATE_RESULT_OK_C= new String[]{"检测结果","返回"};
    static String[] UPDATE_RESULT_OK_E =new String[]{"Test results","return"};

    static String[] UPDATE_RESULT_NO_C = new String[]{"检测结果","更新日志","返回","更新"};
    static String[] IPDATE_RESULT_NO_E= new String[]{"Test results","Update log","return","Update"};

    static String[] DOWNLOAD_PROGRESS_C = new String[]{"下载更新"};
    static String[] DOWNLOAD_PROGRESS_E = new String[]{"Download update"};

    static String[] SYSTEM_UPDATE_LOADING_C =  new String[]{"系统更新","系统更新中...","更新已完成","重启设备"};
    static String[] SYSTEM_UPDATE_LOADING_E = new String[]{"Updater","System update...","update completed","Restart the device"};

    static String[] RECOVERY_C= new String[]{"恢复出厂设置","恢复出厂","已恢复出厂设置","重启设备"};
    static String[] RECOVERY_E =new String[]{"Facctory data reset","Reset","","Facctory data reset!","Restart the device"};

    static String[] DEVICE_MESSAGE_C= new  String[]{"设备信息","设备信息","设备二维码","型号","系统版本","存储信息","运行内存","手机存储"};
    static String[] DEVICE_MESSAGE_E = new String[]{"About device","Basic Information","Device QR code","Equipment model","System version","Store information","RAM","Device storage"};

    static String[] SWITCH_STATE_C = new String[]{"开启","关闭","未连接"};
    static String[] SWITCH_STATE_E = new String[]{"on","off","Not connected"};

    static String[] HINT_MESSAGE_C= new String[]{"正在扫描...","未加密","可配对设备","可连接设备","配置设备名称","取消","保存","未设置","修改成功","修改失败",""};
    static String[] HINT_MESSAGE_E= new String[]{"Scanning...","no","Paired devices","Available devices","Device name","Cancel","Save","Not set","Modify Successfully","Change Failed"};

    static String[] CONNECT_STATC_E = new String[]{"successful connection","faile connection","connecting ..."};
    static String[] CONNECT_STATC_C = new String[]{"连接成功","连接失败","连接中..."};

    static String[] PEIDUI_STATC_E = new String[]{"successful Matching","faile Matching","Matching ...","Connected","Can connect"};
    static String[] PEIDUI_STATC_C = new String[]{"配对成功","配对失败","配对中...","已连接","可用"};

    public static String getPeiDuiState(int index){
        if (getLanguage()==1){
            return PEIDUI_STATC_C[index];
        }else {
            return PEIDUI_STATC_E[index];
        }
    }

    public static String getConnectState(int index){
        if (getLanguage()==1){
            return CONNECT_STATC_C[index];
        }else {
            return CONNECT_STATC_E[index];
        }
    }

    public static String getHintMessage(int index){
        if (getLanguage()==1){
            return HINT_MESSAGE_C[index];
        }else {
            return HINT_MESSAGE_E[index];
        }
    }

    public static String getSettinMain(int index){
        if (getLanguage()==1){
            return SETTING_MAIN_C[index];
        }else {
            return SETTING_MAIN_E[index];
        }
    }

    public static String getWlanMain(int index){
        if (getLanguage()==1){
            return WIFI_MAIN_C[index];
        }else {
            return WIFI_MAIN_E[index];
        }
    }

    public static String getWlanState(int index){
        if (getLanguage()==1){
            return WIFI_MAIN_STATE_C[index];
        }else {
            return WIFI_MAIN_STATE_E[index];
        }
    }

    public static String getWifiDetail(int index){
        if (getLanguage()==1){
            return WIFI_DETAILS_C[index];
        }else {
            return WIFI_DETAILS_E[index];
        }
    }

    public static String getWifiStrength(int index){
        if (getLanguage()==1){
            return WIFI_STRENG_C[index];
        }else {
            return WIFI_STRENG_E[index];
        }
    }

    public static String getWifiPassword(int index){
        if (getLanguage()==1){
            return WIFI_PASSWORD_C[index];
        }else {
            return WIFI_PASSWORD_E[index];
        }
    }

    public static String getBlueMain(int index){
        if (getLanguage()==1){
            return BLUE_MAIN_C[index];
        }else {
            return BLUE_MAIN_E[index];
        }
    }

    public static String getLight(int index){
        if (getLanguage()==1){
            return LIGHT_C[index];
        }else {
            return LIGHT_E[index];
        }
    }

    public static String getVoice(int index){
        if (getLanguage()==1){
            return VOICE_C[index];
        }else {
            return VOICE_E[index];
        }
    }

    public static String getTimeMain(int index){
        if (getLanguage()==1){
            return TIME_MAIN_C[index];
        }else {
            return TIME_MAIN_E[index];
        }
    }

    public static String getTimeDate(int index){
        if (getLanguage()==1){
            return TIME_DAY_C[index];
        }else {
            return TIME_DAY_E[index];
        }
    }

    public static String getTimeTime(int index){
        if (getLanguage()==1){
            return TIME_TIME_C[index];
        }else {
            return TIME_TIME_E[index];
        }
    }

    public static String getDoubleTimeMain(int index){
        if (getLanguage()==1){
            return DOUBLE_CLOCK_C[index];
        }else {
            return DOUBLE_CLOCK_E[index];
        }
    }

    public static String getSelectOri(int index){
        if (getLanguage()==1){
            return SELECT_ORI_C[index];
        }else {
            return SELECT_ORI_E[index];
        }
    }

    public static String getLanguageMain(int index){
        if (getLanguage()==1){
            return SYSTEM_LANGUAGE_C[index];
        }else {
            return SYSTEM_LANGUAGE_E[index];
        }
    }

    public static String getSystemUpdate(int index){
        if (getLanguage()==1){
            return SYSTEM_UPDATE_C[index];
        }else {
            return SYSTEM_UPDATE_E[index];
        }
    }

    public static String getUpdateOk(int index){
        if (getLanguage()==1){
            return UPDATE_RESULT_OK_C[index];
        }else {
            return UPDATE_RESULT_OK_E[index];
        }
    }

    public static String getUpdateNo(int index){
        if (getLanguage()==1){
            return UPDATE_RESULT_NO_C[index];
        }else {
            return IPDATE_RESULT_NO_E[index];
        }
    }

    public static String getUpdateProgree(int index){
        if (getLanguage()==1){
            return DOWNLOAD_PROGRESS_C[index];
        }else {
            return DOWNLOAD_PROGRESS_E[index];
        }
    }

    public static String getRecovery(int index){
        if (getLanguage()==1){
            return RECOVERY_C[index];
        }else {
            return RECOVERY_E[index];
        }
    }

    public static String getDeviceMessage(int index){
        if (getLanguage()==1){
            return DEVICE_MESSAGE_C[index];
        }else {
            return DEVICE_MESSAGE_E[index];
        }
    }

    public static String getSwitchState(int index){
        if (getLanguage()==1){
            return SWITCH_STATE_C[index];
        }else {
            return SWITCH_STATE_E[index];
        }
    }


    public static int getLanguage(){
        return (int) SPUtils.getInstance().get("language",1);
    }

    // 英文日期转中文日期
    // "Saturday, June 13, 2009 at 2:28pm"转化成"2009年6月13日 12:28"
    public static String date2String(String date) {

        StringBuilder result = new StringBuilder();
        String year = "";
        String month = "";
        String day = "";
        String time = "";

        String[] timeStr = date.split(", ");

        for (int i = timeStr.length - 1; i > 0; i--) {
            // System.out.println(timeStr[i]);
            if (i == 2) {
                String[] yearAndTime = timeStr[i].split(" at ");
                year = yearAndTime[0];
                time = yearAndTime[1].contains("am") ? yearAndTime[1].replace("am", "")
                        : new StringBuilder("1").append(yearAndTime[1].replace("pm", "")).toString();
            } else if (i == 1) {
                String[] monthAndDay = timeStr[i].split(" ");
                day = monthAndDay[1];

                switch (monthAndDay[0]) {
                    case "January":
                        month = "1";
                        break;
                    case "February":
                        month = "2";
                        break;
                    case "March":
                        month = "3";
                        break;
                    case "April":
                        month = "4";
                        break;
                    case "May":
                        month = "5";
                        break;
                    case "June":
                        month = "6";
                        break;
                    case "July":
                        month = "7";
                        break;
                    case "August":
                        month = "8";
                        break;
                    case "September":
                        month = "9";
                        break;
                    case "October":
                        month = "10";
                        break;
                    case "November":
                        month = "11";
                        break;
                    case "December":
                        month = "12";
                        break;
                }
            }
        }
        result.append(year).append("年").append(month).append("月").append(day).append("日 ").append(time);
        return result.toString();
    }

    /*中文日期转换为英文日期 2018年6月1日 转换为1 June 2018*/
    public static String Cdate2Edata(long millisions){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String date=sdf.format(new Date(millisions));
        int languageType= (int) SPUtils.getInstance().get("language",1);
        if (languageType==1){
            return  date;
        }else {
            return transform(date);
        }
    }

    public static String transform(String date){
        String[] strs=date.split("年");
        String year=strs[0];
        String[] month_day=strs[1].split("月");
        String month=month_day[0];
        String day=month_day[1].replace("日","");

        String eMonth="";

        switch (month) {
            case "1":
                eMonth = "January";
                break;
            case "2":
                eMonth = "February";
                break;
            case "3":
                eMonth = "March";
                break;
            case "4":
                eMonth = "April";
                break;
            case "5":
                eMonth = "May";
                break;
            case "6":
                eMonth = "June";
                break;
            case "7":
                eMonth = "July";
                break;
            case "8":
                eMonth = "August";
                break;
            case "9":
                eMonth = "September";
                break;
            case "10":
                eMonth = "October";
                break;
            case "11":
                eMonth = "November";
                break;
            case "12":
                eMonth = "December";
                break;
        }
        return day+" "+eMonth+" "+year;
    }

}
