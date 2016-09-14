package com.lance.freebook.Data.APi;

import com.lance.freebook.MVP.Entity.BookTypeDto;

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

}
