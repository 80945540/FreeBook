package com.lance.freebook.MVP.Home.view;

import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.HomeDto;

import java.util.List;

/**
 * Created by XY on 2016/9/17.
 */
public interface HomeRecommendFragmentView {
    //显示加载页
    void showProgress();
    //关闭加载页
    void hideProgress();
    //加载新数据
    void newDatas(HomeDto data);
    //显示加载失败
    void showLoadFailMsg();

}
