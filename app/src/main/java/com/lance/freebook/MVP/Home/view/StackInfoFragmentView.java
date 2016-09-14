package com.lance.freebook.MVP.Home.view;

import com.lance.freebook.MVP.Entity.BookInfoListDto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface StackInfoFragmentView {
    //显示加载页
    void showProgress();
    //关闭加载页
    void hideProgress();
    //加载新数据
    void newBooks(List<BookInfoListDto> newsList);
    //添加更多数据
    void addBooks(List<BookInfoListDto> addList);
    //显示加载失败
    void showLoadFailMsg();
    //显示已加载所有数据
    void showLoadCompleteAllData();
    //显示无数据
    void showNoData();
}
