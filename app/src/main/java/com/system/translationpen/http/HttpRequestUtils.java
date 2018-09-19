package com.system.translationpen.http;


import com.system.translationpen.BuildConfig;
import com.system.translationpen.hotspot.untils.ObjectUtils;
import com.system.translationpen.utils.BaseUtils;
import com.tamic.novate.Novate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josn on 2017/11/10.
 */

public class HttpRequestUtils {

    /**
     * 深圳锦绣科学园 线上正式环境地址
     */
    public static final String HOST_URL = "http://fyhl.vdong.com/";

    /**
     * 北京易麦克提供给深圳锦绣科学园 临时的线上地址
     */
//    public static final String HOST_URL = "http://124.65.123.170:8088";

    // 开发环境 nginx
//    public static final String HOST_URL = "http://192.168.88.30:8088";

    // 启动新的测试环境 二期开发后期 新加入的测试环境 开发后期部署环境
//    public static final String HOST_URL = "http://192.168.66.86:8088";

    //  锦绣测试-30 的外网地址
//    public static final String HOST_URL = "http://124.65.123.170:8188";

    /*10.10.20.7:8090*/
//    public static final String HOST_URL = "http://10.10.10.74:8090";
    /**
     * 192.168.66.86：8088）映射到公网，可以通过124.65.123.170:8088，在公网环境直接访问
     */
//    public static final String HOST_URL = "http://124.65.123.170:8088";

//    public static final String HOST_URL = "http://192.168.66.86:8088";

    //开发环境 zuul
//    public static final String HOST_URL = "http://192.168.66.86:8090";

    private static final int COTTECT_TIME_OUT = 30;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;

    private static Novate novate;
    private static Map<String, Object> parameters = new HashMap<String, Object>();
    private static Map<String, String> headers = new HashMap<>();

    private static HttpRequestUtils instance;

    /**
     * 私有构造
     */
    private HttpRequestUtils() {
    }

    /**
     * 初始化
     * @return
     */
    public static synchronized HttpRequestUtils getInstance() {
        if (ObjectUtils.isNull(instance)) {
            instance = new HttpRequestUtils();
            novate = new Novate.Builder(BaseUtils.getContext())
                    .connectTimeout(COTTECT_TIME_OUT)
                    .writeTimeout(WRITE_TIME_OUT)
                    .readTimeout(READ_TIME_OUT)
                    .baseUrl(HOST_URL)
                    .addParameters(parameters)
                    .addHeader(headers)
                    .addCookie(true)
                    .addCache(false)
                    .addLog(BuildConfig.DEBUG)
                    .build();
        }
        return instance;
    }



    public Novate getNovate() {
        return novate;
    }

    public HttpRequestUtils setParameters(Map<String, Object> parameters){
        this.parameters.clear();
        this.parameters.putAll(parameters);
        return this;
    }

    public HttpRequestUtils setHeaders(Map<String, String> headers){
        this.headers.clear();
        this.headers.putAll(headers);
        return this;
    }
}
