package com.lance.freebook.MVP.Home.model;

import android.util.Log;

import com.lance.freebook.Data.HtmlData.HtmlData;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2016/9/14.
 */
public class StackInfoFragmentModel {

    public void LoadData(BookTypeDto bookType,int page, final OnLoadDataListListener listener){
        HtmlData.getInstance().getStackTypeHtml(bookType,page,new Observer<List<BookInfoListDto>>() {
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
