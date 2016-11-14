package com.lance.freebook.MVP.Home.model;

import com.lance.freebook.Data.HttpData.HttpData;
import com.lance.freebook.MVP.Entity.BookTypeDto;

import java.util.List;

import rx.Observer;

/**
 * 获得类别数据
 */
public class HomeStackFragmentModel {

    public void LoadData(final OnLoadDataListListener listener){
        HttpData.getInstance().getBookTypes(new Observer<List<BookTypeDto>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(List<BookTypeDto> bookTypeDtos) {
                listener.onSuccess(bookTypeDtos);
            }
        });
    }
}
