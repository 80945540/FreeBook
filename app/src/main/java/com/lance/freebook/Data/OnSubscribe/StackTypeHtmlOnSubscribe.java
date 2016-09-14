package com.lance.freebook.Data.OnSubscribe;

import com.lance.freebook.Data.Retrofit.ApiException;
import com.lance.freebook.MVP.Entity.BookInfoListDto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/14.
 */
public class StackTypeHtmlOnSubscribe<T> implements Observable.OnSubscribe<List<T>> {
    private String url;

    public StackTypeHtmlOnSubscribe(String url) {
        this.url = url;
    }

    @Override
    public void call(Subscriber<? super List<T>> subscriber) {
        List<T> expertListDtos=new ArrayList<T>();
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.select("ul").first();
            Elements links = content.select("li");
            for (Element link : links) {
                Elements temp = link.select("a");
                Elements temp1 = link.select("img");
                Elements temp3 = link.select("p:eq(2)");
                Elements temp4 = link.select("p:eq(3)");
                String relHref = temp.attr("href");
                String relHref1 = temp1.attr("src");
                String relHref2 = temp1.attr("alt");
                String relHref3 = temp3.text();
                String relHref4 = temp4.text();
                if(!relHref.equals("")) {
                    expertListDtos.add((T) new BookInfoListDto(relHref1, relHref2, relHref3, relHref4, relHref));
                }
            }
            subscriber.onNext(expertListDtos);
            subscriber.onCompleted();
        } catch (Exception e) {
            throw new ApiException("ERROR:数据解析错误");
        }
    }
}
