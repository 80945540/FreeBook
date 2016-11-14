package com.lance.freebook.MVP.Home.model;

import com.lance.freebook.Data.HttpData.HttpData;
import com.lance.freebook.MVP.Entity.BookInfoListDto;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2016/9/14.
 */
public class StackInfoFragmentModel {

    public void LoadData(int bookType,int page, final OnLoadDataListListener listener){
        HttpData.getInstance().getBookList(bookType,page,new Observer<List<BookInfoListDto>>() {
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
