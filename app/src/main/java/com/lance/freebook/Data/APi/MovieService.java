package com.lance.freebook.Data.APi;

import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Entity.HomeDto;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * API接口
 * 因为使用RxCache作为缓存策略 所以这里不需要写缓存信息
 */
public interface MovieService {

    //获取书库分类信息
    @GET("freebook/typeconfigs.json")
    Observable<List<BookTypeDto>> getBookTypes();

    //获得首页banner以及书籍数据
    @GET("freebook/home.json")
    Observable<HomeDto> getHomeInfo();

    //获得搜索标签
    @GET("freebook/search_lable.json")
    Observable<List<String>> getSearchLable();
}
