package com.lance.freebook.MVP.BookInfo.model;

import com.lance.freebook.Data.HttpData.HttpData;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;

import rx.Observer;

/**
 * 获取书籍详情数据
 */
public class BookInfoModel {
    public void loadData(int id, final OnLoadDataListListener listener){
        HttpData.getInstance().getBookInfo(id, new Observer<BookInfoDto>() {
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
