package com.lance.freebook.MVP.Home.model;

import com.lance.freebook.Data.HttpData.HttpData;
import com.lance.freebook.MVP.Entity.HomeDto;

import rx.Observer;

/**
 * Created by XY on 2016/9/17.
 */
public class HomeRecommendFragmentModel {

    public void loadData(boolean isload,final OnLoadDataListListener listener){
        HttpData.getInstance().getHomeInfo(isload,new Observer<HomeDto>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(HomeDto homeDto) {
                listener.onSuccess(homeDto);
            }
        });
    }
}
