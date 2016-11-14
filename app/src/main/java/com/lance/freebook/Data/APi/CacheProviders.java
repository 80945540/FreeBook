package com.lance.freebook.Data.APi;

import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Entity.HomeDto;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * 缓存API接口
 * @LifeCache设置缓存过期时间. 如果没有设置@LifeCache , 数据将被永久缓存理除非你使用了 EvictProvider, EvictDynamicKey or EvictDynamicKeyGroup .
 * EvictProvider可以明确地清理清理所有缓存数据.
 * EvictDynamicKey可以明确地清理指定的数据 DynamicKey.
 * EvictDynamicKeyGroup 允许明确地清理一组特定的数据. DynamicKeyGroup.
 * DynamicKey驱逐与一个特定的键使用EvictDynamicKey相关的数据。比如分页，排序或筛选要求
 * DynamicKeyGroup。驱逐一组与key关联的数据，使用EvictDynamicKeyGroup。比如分页，排序或筛选要求
 */
public interface CacheProviders {
    //获取书库对应类别书籍列表  缓存时间 1天
    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<BookInfoListDto>>> getBookList(Observable<List<BookInfoListDto>> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //获取书库分类信息缓存数据 缓存时间 永久
    Observable<Reply<List<BookTypeDto>>> getTypeList(Observable<List<BookTypeDto>> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //获取首页配置数据 banner 最热 最新  缓存时间7天
    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    Observable<Reply<HomeDto>> getHomeInfo(Observable<HomeDto> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //获取搜索标签  缓存时间7天
    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<String>>> getHotLable(Observable<List<String>> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //获取书籍详情  缓存时间7天
    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    Observable<Reply<BookInfoDto>> getBookInfo(Observable<BookInfoDto> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //根据关键词获取搜素列表  缓存时间1天
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Reply<BookInfoListDto>> getSearchList(Observable<BookInfoListDto> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);
}
