package com.lance.freebook.Data.HtmlData;

import android.util.Log;

import com.lance.freebook.Data.APi.CacheProviders;
import com.lance.freebook.Data.OnSubscribe.BookInfoHtmlOnSubscribe;
import com.lance.freebook.Data.OnSubscribe.StackTypeHtmlOnSubscribe;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.Util.FileUtil;
import com.lance.freebook.common.Constant;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

/**
 * Created by Administrator on 2016/9/14.
 */
public class HtmlData {
    private static File cacheDirectory = FileUtil.getcacheDirectory();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory)
            .using(CacheProviders.class);

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HtmlData INSTANCE = new HtmlData();
    }

    //获取单例
    public static HtmlData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //根据类型获取书籍集合
    public void getStackTypeHtml(BookTypeDto bookType, int pageIndex, Observer<List<BookInfoListDto>> observer) {
        Observable observable = Observable.create(new StackTypeHtmlOnSubscribe<BookInfoListDto>(bookType.getBookTypeUrl().replace("{Page}",pageIndex+"")));
        Observable observableCache=providers.getStackTypeList(observable,new DynamicKey("getStackTypeHtml"+bookType.getBookTypeName()+pageIndex), new EvictDynamicKey(false)).map(new HttpResultFuncCache<List<BookInfoListDto>>());
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
        Observable observable=Observable.create(new StackTypeHtmlOnSubscribe<BookInfoListDto>(Constant.API_SEARCH.replace("{Key}",key)));
        Observable observableCache=providers.getStackTypeList(observable,new DynamicKey("getSearchList&"+key), new EvictDynamicKey(false)).map(new HttpResultFuncCache<List<BookInfoListDto>>());
        setSubscribe(observableCache, observer);
    }
    //获得书籍的详情
    public void getBookInfo(BookInfoListDto bookinfo, Observer<BookInfoDto> observer){
        Observable observable=Observable.create(new BookInfoHtmlOnSubscribe<BookInfoDto>(bookinfo.getCodeId()));
        Observable observableCache=providers.getBookInfo(observable,new DynamicKey(bookinfo.getBookName()),new EvictDynamicKey(true)).map(new HttpResultFuncCache<BookInfoDto>());
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
    private  class HttpResultFuncCache<T> implements Func1<Reply<T>, T> {

        @Override
        public T call(Reply<T> httpResult) {
            return httpResult.getData();
        }
    }
}
