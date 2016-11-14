package com.lance.freebook.MyApplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.lance.freebook.MVP.Home.model.TasksManager;
import com.lance.freebook.MVP.WelcomeActivity;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.xiaochao.lcrapiddeveloplibrary.Exception.core.Recovery;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/*
 *自定义Application
 * 用于初始化各种数据以及服务
 *  */

public class MyApplication extends Application {
    //记录当前栈里所有activity
    private List<Activity> activities = new ArrayList<Activity>();
    //记录需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //异常友好管理初始化
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(WelcomeActivity.class)
//                .skip(H5PayActivity.class)  如果应用集成支付宝支付 记得加上这句代码  没时间解释了  快上车  老司机发车了
                .init(this);


        //文件下载管理初始化
        FileDownloader.init(getApplicationContext(),
                new FileDownloadHelper.OkHttpClientCustomMaker() { // is not has to provide.
                    @Override
                    public OkHttpClient customMake() {
                        // just for OkHttpClient customize.
                        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        // you can set the connection timeout.
                        builder.connectTimeout(15_000, TimeUnit.MILLISECONDS);
                        // you can set the HTTP proxy.
                        builder.proxy(Proxy.NO_PROXY);
                        // etc.
                        return builder.build();
                    }
                });
        //初始化下载管理
        TasksManager.init();
    }
    /**
     * 应用实例
     **/
    private static MyApplication instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    *给临时Activitys
    * 添加activity
    * */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }

    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    * 退出指定的Activity
    * */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
