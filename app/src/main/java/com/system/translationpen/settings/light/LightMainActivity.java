package com.system.translationpen.settings.light;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.untils.SettingsController;
import com.xw.repo.BubbleSeekBar;

import butterknife.BindView;

import static com.system.translationpen.settings.Constant.getLight;

public class LightMainActivity extends BaseTitleActivity {

    @BindView(R.id.light_seekbar)
    BubbleSeekBar light_seekbar;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_light_main;
    }

    @Override
    protected void onInitData() {


        int screenBrightness = SettingsController.getScreenBrightness(this);
        light_seekbar.setProgress(screenBrightness);

        light_seekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                boolean success = SettingsController.setScreenBrightness(LightMainActivity.this,progress);
                Window localWindow = getWindow();
                WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
                float f = progress / 255.0F;
                localLayoutParams.screenBrightness = f;
                localWindow.setAttributes(localLayoutParams);

                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                //保存到系统中
                Uri uri = android.provider.Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
                android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
                getContentResolver().notifyChange(uri, null);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

    }

    @Override
    public void refreshSettingState() {
        super.refreshSettingState();
        setTitle(R.drawable.wback,getLight(0),0);
    }
}
