package com.lance.freebook.Date.Retrofit;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable e);
    void onCompleted();
}
