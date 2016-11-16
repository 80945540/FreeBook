package com.lance.freebook.MVP.BookInfo.view;

import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface BookInfoView {
    //显示加载页
    void showProgress();
    //关闭加载页
    void hideProgress();
    //数据加载成功
    void newData(BookInfoDto data);
    //显示加载失败
    void showLoadFailMsg();
}
