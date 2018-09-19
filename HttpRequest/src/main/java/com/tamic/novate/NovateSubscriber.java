/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer/Novate
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.tamic.novate;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tamic.novate.constant.BroadcastAction;
import com.tamic.novate.exception.NovateException;
import com.tamic.novate.exception.ServerException;
import com.tamic.novate.util.LogWraper;
import com.tamic.novate.util.ReflectionUtil;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

import static com.tamic.novate.Novate.TAG;

/**
 * NovateSubscriber
 * Created by Tamic on 2016-06-06.
 *
 * @param <T>
 */
class NovateSubscriber<T> extends BaseSubscriber<ResponseBody> {

    //Gson gson = new Gson();
    private Novate.ResponseCallBack<T> callBack;
    private Context context;

    private Type finalNeedType;
    //private Type dataType;

    public NovateSubscriber(Context context, Novate.ResponseCallBack callBack) {
        super(context);
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        //dataType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Type[] types = ReflectionUtil.getParameterizedTypeswithInterfaces(callBack);
        if (ReflectionUtil.methodHandler(types) == null || ReflectionUtil.methodHandler(types).size() == 0) {
            LogWraper.e(TAG, "callBack<T> 中T不合法: -->" + finalNeedType);
            throw new NullPointerException("callBack<T> 中T不合法");
        }
        finalNeedType = ReflectionUtil.methodHandler(types).get(0);
        // todo some common as show loadding  and check netWork is NetworkAvailable
        if (callBack != null) {
            callBack.onStart();
        }

    }

    @Override
    public void onCompleted() {
        // todo some common as  dismiss loadding
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            byte[] bytes = responseBody.bytes();
            String jsStr = new String(bytes);
            LogWraper.d("Novate", "ResponseBody:" + jsStr.trim());

            if (callBack!=null){
                callBack.onSuccess(200,"访问成功",null,jsStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(NovateException.handleException(e));
            }
        }
    }
}