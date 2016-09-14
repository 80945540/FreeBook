package com.lance.freebook.MVP.Home.view;

import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface HomeStackFragmentView {
    //显示加载页
    void showProgress();
    //关闭加载页
    void hideProgress();
    //数据加载成功
    void newData(List<BookTypeDto> newsList);
    //显示加载失败
    void showLoadFailMsg();
    //显示无数据
    void showNoData();
}
