package com.lance.freebook.MVP.Home.model;

import com.lance.freebook.MVP.Entity.BookInfoListDto;

import java.util.List;

/**
 * Created by XY on 2016/9/17.
 */
public interface OnLoadDataListListener<T> {
    void onSuccess(T data);
    void onFailure(Throwable e);
}
