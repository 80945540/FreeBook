package com.lance.freebook.MVP.Search.View;

import com.lance.freebook.MVP.Entity.BookInfoListDto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface SearchLabelView {
    //显示加载页
    void showProgress();
    //关闭加载页
    void hideProgress();
    //数据加载成功
    void newData(List<String> data);
    //显示加载失败
    void showLoadFailMsg();
    //显示无数据
    void showNoData();
}
