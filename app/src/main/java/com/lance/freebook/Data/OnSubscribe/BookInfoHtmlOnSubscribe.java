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
 * Created by Administrator on 2016/9/19.
 */
public class BookInfoHtmlOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private String url;

    public BookInfoHtmlOnSubscribe(String url) {
        this.url = url;
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
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
