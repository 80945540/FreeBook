package com.lance.freebook.Data.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/*
 *okHttp的配置
 * 缓存已经添加  目前只支持GET请求的缓存  POST的我在找合适的处理方法
 * 也可根据自己的需要进行相关的修改
 */
public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;


    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

        }

        return mOkHttpClient;
    }


}
