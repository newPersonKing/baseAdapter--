package com.system.translationpen.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.system.translationpen.entry.PenApplication;
/**
 * Created by huison on 2018/6/2.
 */

public class ToastUtils {

    public static void showToast(int resId) {
        showToast(PenApplication.context().getString(resId));
    }

    public static void showToast(String toast) {
        showToast(toast, Toast.LENGTH_SHORT);
    }

    public static void showToast(String toast, int duration) {
        if (TextUtils.isEmpty(toast)) {
            return;
        }
        Toast.makeText(PenApplication.context(), toast, duration).show();
    }
}
