package com.lance.freebook.Data.APi;

import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Entity.HomeDto;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * API接口
 */
public interface MovieService {

    //获取书库分类信息
    @GET("freebook/typeconfig.json")
    Observable<List<BookTypeDto>> getBookTypes();

    //获得首页banner以及书籍数据
    @GET("freebook/home.json")
    Observable<HomeDto> getHomeInfo();
}
