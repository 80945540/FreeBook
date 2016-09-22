package com.lance.freebook.Data.OnSubscribe;

import android.util.Log;

import com.lance.freebook.Data.Retrofit.ApiException;
import com.lance.freebook.MVP.Entity.BookInfoDto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * 其实这里面的玩法还很多
 * 这是jsop的中文文档 http://www.open-open.com/jsoup/  再牛逼的数据都能抓取
 * 其实doc.select(".bookcover h1:eq(1)");  ()里面的数据完全可以通过接口定义  达到完全控制的效果
 * 我是懒得写了  但是这个需求还是提一下  很nice的  装逼必备啊
 */
public class BookInfoHtmlOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private String url;

    public BookInfoHtmlOnSubscribe(String url) {
        //获取到需要解析html地址
        this.url = url;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
            //开始疯狂的数据抓取啦 这个我就不解释了  大家去看看文档  http://www.open-open.com/jsoup/
            Document doc = Jsoup.connect(url).get();
            Elements bookIntroduction = doc.select(".con");
            Elements bookname = doc.select(".bookcover h1:eq(1)");
            Elements bookImageUrl = doc.select(".bookcover img");
            Elements bookAuthor = doc.select(".bookcover p:eq(2)");
            Elements bookType = doc.select(".bookcover p:eq(3)");
            Elements bookLength = doc.select(".bookcover p:eq(4)");
            Elements bookProgress = doc.select(".bookcover p:eq(5)");
            Elements bookUpdateTime = doc.select(".bookcover p:eq(6)");
            String[] strs=url.split("/");
            String bookDownload="http://www.txt99.cc/home/down/txt/id/"+((strs[strs.length-1]));
            T bookInfoDto= (T) new BookInfoDto(bookImageUrl.attr("src"),bookname.text(),bookAuthor.text(),bookType.text(),bookLength.text(),bookProgress.text(),bookUpdateTime.text(),bookDownload,bookIntroduction.html());
            subscriber.onNext(bookInfoDto);
            subscriber.onCompleted();
        } catch (IOException e) {
            throw new ApiException("ERROR:数据解析错误");
        }
    }
}
