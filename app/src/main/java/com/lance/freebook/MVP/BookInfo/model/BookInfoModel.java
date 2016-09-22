package com.lance.freebook.MVP.BookInfo.model;

import android.util.Log;

import com.lance.freebook.Data.HtmlData.HtmlData;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;

import rx.Observer;

/**
 * 获取书籍详情数据
 */
public class BookInfoModel {
    public void loadData(String bookUrl,String bookName, final OnLoadDataListListener listener){
        HtmlData.getInstance().getBookInfo(bookUrl,bookName, new Observer<BookInfoDto>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(BookInfoDto bookInfoDto) {
                listener.onSuccess(bookInfoDto);
            }
        });
    }
}
