package com.lance.freebook.MVP.BookInfo.model;

import android.util.Log;

import com.lance.freebook.Data.HtmlData.HtmlData;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;

import rx.Observer;

/**
 * Created by Administrator on 2016/9/19.
 */
public class BookInfoModel {
    public void loadData(BookInfoListDto bookinfo, final OnLoadDataListListener listener){
        HtmlData.getInstance().getBookInfo(bookinfo, new Observer<BookInfoDto>() {
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
