package com.system.translationpen.utils;
import com.system.translationpen.entry.PenApplication;
/**
 * Created by huison on 2018/6/17.
 */

public class DensityUtils {

    public static int dp2px(float dpValue) {
        final float scale = PenApplication.context().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = PenApplication.context().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = PenApplication.context().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = PenApplication.context().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
