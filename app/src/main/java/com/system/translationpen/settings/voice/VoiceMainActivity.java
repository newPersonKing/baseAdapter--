package com.system.translationpen.settings.voice;

import android.media.AudioManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.hotspot.untils.SettingsController;
import com.xw.repo.BubbleSeekBar;

import org.apache.poi.hssf.record.formula.functions.T;

import butterknife.BindView;

import static com.system.translationpen.settings.Constant.getVoice;

public class VoiceMainActivity extends BaseTitleActivity {

    @BindView(R.id.seekbar_an)
    BubbleSeekBar seekbar_an;
    @BindView(R.id.seekbar_mt)
    BubbleSeekBar seekbar_mt;

    @BindView(R.id.tv_an_voice_name)
    TextView tv_an_voice_name;
    @BindView(R.id.tv_media_voice_name)
    TextView tv_media_voice_name;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_voice_main;
    }

    @Override
    protected void onInitData() {
        setTitle(R.drawable.wback,getVoice(0),0);
//        a.按键音量AudioManager.STREAM_VOICE_SYSYEM
//        b.媒体音量AudioManager.STREAM_MUSIC
        /*按键声音*/

        final int anmax=SettingsController.getStreamMaxVolume(this,AudioManager.STREAM_SYSTEM);

        final int mtmax=SettingsController.getStreamMaxVolume(this,AudioManager.STREAM_MUSIC);


        int anvolume = SettingsController.getStreamVolume(this, AudioManager.STREAM_SYSTEM);
        int mtvolume = SettingsController.getStreamVolume(this, AudioManager.STREAM_MUSIC);
        seekbar_an.setProgress(anvolume);
        seekbar_mt.setProgress(mtvolume);

        seekbar_an.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                SettingsController.setStreamVolume(VoiceMainActivity.this,AudioManager.STREAM_SYSTEM,anmax*progress/100,false);
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        seekbar_mt.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                SettingsController.setStreamVolume(VoiceMainActivity.this,AudioManager.STREAM_MUSIC,mtmax*progress/100,false);
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
    public void refreshLanguage() {
        super.refreshLanguage();
        tv_an_voice_name.setText(getVoice(1));
        tv_media_voice_name.setText(getVoice(2));
    }
}
