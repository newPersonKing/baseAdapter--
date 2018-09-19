package com.system.translationpen.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.system.translationpen.hotspot.untils.ObjectUtils;

/**
 * Created by algorithm
 */
public class BaseUtils {

    private static Context sContext;

    private static Gson gson;

    private BaseUtils() {
        throw new UnsupportedOperationException("u can't instantiate m3...");
    }

    /**
     * Application 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        sContext = context.getApplicationContext();
        gson = new Gson();
    }

    /**
     * @return ApplicationContext
     */
    public static synchronized Context getContext() {
        if (sContext != null)
            return sContext;
        throw new NullPointerException("u should init first");
    }

    public static Gson getGson(){
        if(!ObjectUtils.isNull(gson)){
            return gson;
        }
        throw new NullPointerException("gson init error");
    }


    /** **************************************************************** */

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {

        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {

        //  DisplayMetrics dm = new DisplayMetrics(); 第二种方式

        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}
