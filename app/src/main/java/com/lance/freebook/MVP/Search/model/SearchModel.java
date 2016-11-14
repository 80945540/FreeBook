package com.lance.freebook.MVP.Search.model;

import com.lance.freebook.Data.HttpData.HttpData;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SearchModel {

    public void loadData(String key, final OnLoadDataListListener listener){
        HttpData.getInstance().getSearchList(key, new Observer<List<BookInfoListDto>>() {
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

    public void loadSearchLableData(final OnLoadDataListListener listener){
        HttpData.getInstance().getSearchLable(new Observer<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(List<String> strings) {
                listener.onSuccess(strings);
            }
        });
    }
}
