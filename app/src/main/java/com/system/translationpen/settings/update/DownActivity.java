package com.system.translationpen.settings.update;

import android.widget.TextView;

import com.liqi.nohttputils.download.NohttpDownloadUtils;
import com.system.translationpen.R;
import com.system.translationpen.hotspot.base.BaseTitleActivity;
import com.system.translationpen.http.HttpRequestUtils;
import com.system.translationpen.http.RxGyDownCallback;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxDownCallback;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

import java.io.File;

import butterknife.BindView;
import okhttp3.Call;

import static com.system.translationpen.settings.Constant.getLanguage;
import static com.system.translationpen.settings.Constant.getUpdateProgree;

public class DownActivity extends BaseTitleActivity {

    @BindView(R.id.tv_download_total)
    TextView tv_download_total;

    @Override
    protected int onSetContentView() {
        return R.layout.activity_down;
    }

    @Override
    protected void onInitData() {

        setTitle(R.drawable.wback,getUpdateProgree(0),0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getLanguage()==1){
            tv_download_total.setText(getResources().getString(R.string.download_progress_c,"150"));
        }else {
            tv_download_total.setText(getResources().getString(R.string.download_progress_e,"150"));
        }
    }

    public void downAPK(){
        HttpRequestUtils.getInstance().getNovate().rxGyDownload("", "down.apk", new RxGyDownCallback() {
            @Override
            public void onNext(Object tag, File file) {

            }

            @Override
            public void onProgress(Object tag, int progress, long speed, long downloaded, long total) {
               //TODO 更新进度
            }

            @Override
            public void onError(Object tag, Throwable e) {

            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }

            @Override
            public void onNext(Object tag, Call call, File response) {

            }
        });
    }

    public void downLoadApk(){
        NohttpDownloadUtils.getNohttpDownloadBuild()

                //Add download file parameters
                .addDownloadParameter("", "Download_Name.apk")

                //Set whether to continue downloading at breakpoint.If true yes,else not.
                .setRange(true)

                //Set the download progress monitoring interface
                .setDownloadListener(new DownloadListener() {
                    @Override
                    public void onDownloadError(int what, Exception exception) {

                    }

                    @Override
                    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

                    }

                    @Override
                    public void onProgress(int what, int progress, long fileCount, long speed) {

                    }

                    @Override
                    public void onFinish(int what, String filePath) {

                    }

                    @Override
                    public void onCancel(int what) {

                    }
                })

                //Set whether to delete the same file name file, and then re-download.If true yes,else not.
                .setDeleteOld(false)

                //Set the download file to store the file path
                .setFileFolder("")

                //Single request to set the read time. Unit seconds, default to global read timeout.
                .setReadTimeout(40)

                //Single request to set the link timeout. Unit seconds, default to global link timeout.
                .setConnectTimeout(40)

                //Single request to set the number of failed request retries, default number of global link retries.
                .setRetryCount(3);

        NohttpDownloadUtils.cancelAll();

        //Pause the specified download task
        NohttpDownloadUtils.cancel("url");

        NohttpDownloadUtils.getDownloadRequestsWhat("");

//Remove the 'What' value for the download URL
        NohttpDownloadUtils.removeWhatData("");

//Remove all downloads 'What' values
        NohttpDownloadUtils.removeWhatAll();
    }
}
