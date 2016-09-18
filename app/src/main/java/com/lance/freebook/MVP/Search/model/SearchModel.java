package com.lance.freebook.MVP.Search.model;

import com.lance.freebook.Data.HtmlData.HtmlData;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SearchModel {

    public void loadData(String key, final OnLoadDataListListener listener){
        HtmlData.getInstance().getSearchList(key, new Observer<List<BookInfoListDto>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(List<BookInfoListDto> bookInfoListDtos) {
                listener.onSuccess(bookInfoListDtos);
            }
        });
    }
}
