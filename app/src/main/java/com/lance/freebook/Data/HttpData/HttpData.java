package com.lance.freebook.Data.HttpData;

import com.lance.freebook.Data.APi.BookService;
import com.lance.freebook.Data.APi.CacheProviders;
import com.lance.freebook.Data.Retrofit.ApiException;
import com.lance.freebook.Data.Retrofit.RetrofitUtils;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Entity.HomeDto;
import com.lance.freebook.MVP.Entity.HttpResult;
import com.lance.freebook.Util.FileUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*
 *所有的请求数据的方法集中地
 * 根据MovieService的定义编写合适的方法
 * 其中observable是获取API数据
 * observableCahce获取缓存数据
 * new EvictDynamicKey(false) false使用缓存  true 加载数据不使用缓存
 */
public class HttpData extends RetrofitUtils {

    private static File cacheDirectory = FileUtil.getcacheDirectory();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory)
            .using(CacheProviders.class);
    protected static final BookService service = getRetrofit().create(BookService.class);

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    //获取单例
    public static HttpData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //获取app书本类别
    public void getBookTypes(Observer<List<BookTypeDto>> observer){
        Observable observable=service.getTypeList().map(new HttpResultFunc<List<BookTypeDto>>());
        Observable observableCahce=providers.getTypeList(observable,new DynamicKey("书本类别"),new EvictDynamicKey(false)).map(new HttpResultFuncCcche<List<BookTypeDto>>());
        setSubscribe(observableCahce,observer);
    }
    //获取app首页配置信息  banner  最新 最热
    public void getHomeInfo(boolean isload,Observer<HomeDto> observer){
        Observable observable=service.getHomeInfo().map(new HttpResultFunc<HomeDto>());;
        Observable observableCache=providers.getHomeInfo(observable,new DynamicKey("首页配置"),new EvictDynamicKey(isload)).map(new HttpResultFuncCcche<HomeDto>());
        setSubscribe(observableCache,observer);
    }
    //获得搜索热门标签
    public void getSearchLable(Observer<List<String>> observer){
        Observable observable=service.getHotLable().map(new HttpResultFunc<List<String>>());;
        Observable observableCache=providers.getHotLable(observable,new DynamicKey("搜索热门标签"), new EvictDynamicKey(false)).map(new HttpResultFuncCcche<List<String>>());
        setSubscribe(observableCache,observer);
    }
    //根据类型获取书籍集合
    public void getBookList(int bookType, int pageIndex, Observer<List<BookInfoListDto>> observer) {
        Observable observable = service.getBookList(bookType,pageIndex).map(new HttpResultFunc<List<BookInfoListDto>>());
        Observable observableCache=providers.getBookList(observable,new DynamicKey("getStackTypeHtml"+bookType+pageIndex), new EvictDynamicKey(false)).map(new HttpResultFuncCcche<List<BookInfoListDto>>());
        setSubscribe(observableCache, observer);
    }
    //根据关键字搜索书籍
    public void getSearchList(String key,Observer<List<BookInfoListDto>> observer){
        try {
            //中文记得转码  不然会乱码  搜索不出想要的效果
            key = URLEncoder.encode(key, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Observable observable=service.getSearchList(key).map(new HttpResultFunc<List<BookInfoListDto>>());
        Observable observableCache=providers.getSearchList(observable,new DynamicKey("getSearchList&"+key), new EvictDynamicKey(false)).map(new HttpResultFuncCcche<List<BookInfoListDto>>());
        setSubscribe(observableCache, observer);
    }
    //获取书籍详情
    public void getBookInfo(int id, Observer<BookInfoDto> observer){
        Observable observable=service.getBookInfo(id).map(new HttpResultFunc<BookInfoDto>());
        Observable observableCache=providers.getBookInfo(observable,new DynamicKey("getBookInfo&"+id), new EvictDynamicKey(false)).map(new HttpResultFuncCcche<BookInfoDto>());
        setSubscribe(observableCache, observer);
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
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private  class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() !=1 ) {
                throw new ApiException(httpResult);
            }
            return httpResult.getData();
        }
    }
    /**
     * 用来统一处理RxCacha的结果
     */
    private  class HttpResultFuncCcche<T> implements Func1<Reply<T>, T> {

        @Override
        public T call(Reply<T> httpResult) {
            return httpResult.getData();
        }
    }

}
