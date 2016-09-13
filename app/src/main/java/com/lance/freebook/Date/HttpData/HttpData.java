package com.lance.freebook.Date.HttpData;

import android.content.Context;
import android.content.Intent;

import com.lance.freebook.Date.APi.CacheProviders;
import com.lance.freebook.Date.APi.MovieService;
import com.lance.freebook.Date.Retrofit.ApiException;
import com.lance.freebook.Util.FileUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

import io.rx_cache.internal.RxCache;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.observables.AsyncOnSubscribe;
import rx.schedulers.Schedulers;

/*
 *所有的请求数据的方法集中地
 * 根据MovieService的定义编写合适的方法
 */
public class HttpData extends RetrofitUtils {

    private static File cacheDirectory = FileUtil.getcacheDirectory();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory)
            .using(CacheProviders.class);
    protected static final MovieService service = getRetrofit().create(MovieService.class);

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    //获取单例
    public static HttpData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getHtml(Observer<String> observer) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Document doc = Jsoup.connect("http://m.txt99.cc/category/wuxia.html").get();
                    subscriber.onNext(doc.html().toString());
                } catch (Exception e) {
                    subscriber.onNext("error");
                }
                subscriber.onCompleted();
            }
        }).map(new HttpResultString());
        setSubscribe(observable, observer);
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     *  Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    private  class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (httpResult.getCode() !=1 ) {
//                throw new ApiException(httpResult);
//            }
//            return httpResult.getResults();
//        }
//    }
//    private  class HttpResultFunc1<T> implements Func1<Reply<T>, T> {
//
//        @Override
//        public T call(Reply<T> httpResult) {
//            Log.d("HttpResultFunc1", "httpResult:" + httpResult);
//            return httpResult.getData();
//        }
//    }
    private  class HttpResultString implements Func1<String, String> {

        @Override
        public String call(String strType) {
            if (strType.equals("error")) {
                throw new ApiException(strType);
            }
            return strType;
        }
    }

}
