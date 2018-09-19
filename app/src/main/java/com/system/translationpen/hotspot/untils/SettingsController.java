package com.system.translationpen.hotspot.untils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.LocaleList;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Locale;

/**
 * Created by huison on 2018/6/5.
 */

public class SettingsController {

    /**
     * 设置屏幕亮度调节模式
     * Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL = 0  手动调节
     * Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC = 1 自动调节
     */
    public static boolean setScreenBrightnessMode(Context context, int brightnessMode) {
        try {
            return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, brightnessMode);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取屏幕亮度调节模式
     * Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL = 0  手动调节
     * Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC = 1 自动调节
     */
    public static int getScreenBrightnessMode(Context context) {
        int screenMode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        try {
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return screenMode;
    }

    public static boolean isAutomaticBrightness(Context context) {
        return getScreenBrightnessMode(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    public static boolean isManualBrightness(Context context) {
        return getScreenBrightnessMode(context) == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
    }

    public static boolean setScreenBrightnessAutomatic(Context context) {
        return setScreenBrightnessMode(context, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    public static boolean setScreenBrightnessManual(Context context) {
        return setScreenBrightnessMode(context, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 设置屏幕亮度0~255
     */
    public static boolean setScreenBrightness(Context context, int brightness) {
        try {
            return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取屏幕亮度0~255
     */
    public static int getScreenBrightness(Context context) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    /**
     * 获取手机运营商
     */
    public static String getOperator(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = telephonyManager.getSubscriberId();
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                return "中国移动";
            } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                return "中国联通";
            } else if (IMSI.startsWith("46003")) {
                return "中国电信";
            } else {
                return "无sim卡";
            }
        } else {
            return "无sim卡";
        }
    }

    /**
     * 获取系统时间
     */
    public static long getSystemTime() {
        return System.currentTimeMillis();
    }

    private static BatteryBroadcastReceiver sBatteryBroadcastReceiver = null;
    private static OnBatteryChangedListener sOnBatteryChangedListener = null;
    private static OnBatteryListener sOnBatteryListener = null;
    private static boolean sIsOneTime = true;

    /**
     * 获取设备电量
     */
    public static void queryBatteryCapacity(Context context, OnBatteryListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (listener != null) {
                BatteryManager batteryManager = (BatteryManager) context.getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
                int capacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                listener.onReadBattery(capacity);
            }
        } else {
            sIsOneTime = true;
            if (sOnBatteryListener != null && sOnBatteryListener != listener) {
                sOnBatteryListener = null;
            }
            sOnBatteryListener = listener;
            if (sBatteryBroadcastReceiver == null) {
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                sBatteryBroadcastReceiver = new BatteryBroadcastReceiver();
                context.registerReceiver(sBatteryBroadcastReceiver, intentFilter);
            }
        }
    }

    /**
     * 监听电池电量变化，不用时需要手动释放 {@link #release()}
     */
    public static void setOnBatteryChangedListener(Context context, OnBatteryChangedListener listener) {
        sIsOneTime = false;
        if (sOnBatteryChangedListener != null && sOnBatteryChangedListener != listener) {
            sOnBatteryChangedListener = null;
        }
        sOnBatteryChangedListener = listener;
        if (sBatteryBroadcastReceiver == null) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            sBatteryBroadcastReceiver = new BatteryBroadcastReceiver();
            context.registerReceiver(sBatteryBroadcastReceiver, intentFilter);
        }
    }

    private static class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int capacity = intent.getIntExtra("level", 100);
                if (sOnBatteryListener != null) {
                    sOnBatteryListener.onReadBattery(capacity);
                }
                if (sOnBatteryChangedListener != null) {
                    sOnBatteryChangedListener.onBatteryChanged(capacity);
                }
                if (sIsOneTime) {
                    sBatteryBroadcastReceiver = null;
                }
            }
        }
    }

    public interface OnBatteryListener {
        void onReadBattery(int capacity);
    }

    public interface OnBatteryChangedListener {
        void onBatteryChanged(int capacity);
    }

    public interface OnSignalStrengthListener {
        void onReadSignalStrength(SignalStrength signalStrength);
    }

    private static PhoneStateListener sPhoneStateListener = null;

    /**
     * 获取信号强度
     */
    public static void querySignalStrength(Context context, final OnSignalStrengthListener listener) {
        final TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (sPhoneStateListener == null) {
            sPhoneStateListener = new PhoneStateListener() {
                @Override
                public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                    super.onSignalStrengthsChanged(signalStrength);
                    if (listener != null) {
                        listener.onReadSignalStrength(signalStrength);
                    }
                    telephonyManager.listen(sPhoneStateListener, TelephonyManager.PHONE_TYPE_NONE);
                    sPhoneStateListener = null;
                }
            };
        }
        telephonyManager.listen(sPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    /**
     * 获取音量
     *
     * @params streamType
     * The stream type to query. One of
     * {@link AudioManager#STREAM_VOICE_CALL},
     * {@link AudioManager#STREAM_SYSTEM},
     * {@link AudioManager#STREAM_RING},
     * {@link AudioManager#STREAM_MUSIC},
     * {@link AudioManager#STREAM_ALARM},
     * {@link AudioManager#STREAM_NOTIFICATION},
     * {@link AudioManager#STREAM_DTMF}.
     */
    public static int getStreamVolume(Context context, int streamType) {
        try {
            AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            return audioManager.getStreamVolume(streamType);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取最大音量
     */
    public static int getStreamMaxVolume(Context context, int streamType) {
        try {
            AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            return audioManager.getStreamMaxVolume(streamType);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 100;
    }

    public static void setStreamVolume(Context context, int streamType, int volume, boolean playSound) {
        try {
            AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            int flag = AudioManager.FLAG_SHOW_UI;
            if (playSound) {
                flag = flag | AudioManager.FLAG_PLAY_SOUND;
            }
            audioManager.setStreamVolume(streamType, volume, flag);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统语言
     */
    public static Locale[] getSystemLanguages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = LocaleList.getDefault();
            int size = localeList.size();
            Locale[] locales = new Locale[size];
            for (int i = 0; i < size; i++) {
                locales[i] = localeList.get(i);
            }
            return locales;
        } else {
            return Locale.getAvailableLocales();
        }
    }

    /**
     * 系统时间是否24小时制
     */
    public static boolean is24Hour(Context context) {
        return DateFormat.is24HourFormat(context);
    }

    /**
     * 设置系统时间为24小时制
     */
    public static boolean set24Hour(Context context) {
        return Settings.System.putString(context.getContentResolver(), Settings.System.TIME_12_24, "24");
    }

    /**
     * 设置系统时间为12小时制
     */
    public static boolean set12Hour(Context context) {
        return Settings.System.putString(context.getContentResolver(), Settings.System.TIME_12_24, "12");
    }

    /**
     * 设置自动确定日期和时间
     */
    public static boolean setAutoModifyTime(Context context, boolean auto) {
        try {
            return Settings.System.putInt(context.getContentResolver(), Settings.System.AUTO_TIME, auto ? 1 : 0);
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否自动确定日期和时间
     */
    public static boolean isAutoModifyTime(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME) == 1;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置自动确定时区
     */
    public static boolean setAutoModifyTimeZone(Context context, boolean auto) {
        try {
            return Settings.System.putInt(context.getContentResolver(), Settings.System.AUTO_TIME_ZONE, auto ? 1 : 0);
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否自动确定时区
     */
    public static boolean isAutoModifyTimeZone(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME_ZONE) == 1;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置系统时间
     */
    public static void setSystemTimes(Context context, long times) {
        try {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(times);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机运行内存
     */
    public static long getTotalMemorySize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        return outInfo.totalMem;
    }

    /**
     * 获取手机内部剩余存储空间
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 释放静态变量，防止内存泄露
     */
    public static void release() {
        sOnBatteryListener = null;
        sOnBatteryChangedListener = null;
        sBatteryBroadcastReceiver = null;
        sPhoneStateListener = null;
    }
}
