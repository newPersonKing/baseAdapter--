package com.system.translationpen.http;

import android.content.Context;

import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.RxSubscriber;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.ResponseCallback;

import okhttp3.ResponseBody;

public class RxGySubscriber<T, E> extends BaseSubscriber<ResponseBody>{

    private ResponseCallback<T, E> callBack;
    private Object tag = null;
    private Context context;

    /*用来做文件下载 保存的文件名称*/
    private String fileName;

    public RxGySubscriber(Object tag,String fileName, ResponseCallback<T, E> callBack) {
        super();
        if (callBack == null) {
            this.callBack = ResponseCallback.CALLBACK_DEFAULT;
        } else {
            this.callBack = callBack;
        }
        this.callBack.setTag(tag);
        this.tag = tag;
        this.fileName=fileName;
    }

    public Context context() {
        return context;
    }

    public RxGySubscriber addContext(Context context) {
        this.context = context;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart(tag,fileName);
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            callBack.onCompleted(tag,fileName);
            callBack.onRelease();
        }
    }


    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(tag, e);
            callBack.onRelease();
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            if (callBack.isReponseOk(tag, responseBody)) {
                callBack.onGyNext(tag, fileName, callBack.onHandleResponse(responseBody));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
