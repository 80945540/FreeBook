package com.lance.freebook.MVP.Home.presenter;

import com.lance.freebook.MVP.Entity.HomeDto;
import com.lance.freebook.MVP.Home.model.HomeRecommendFragmentModel;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;
import com.lance.freebook.MVP.Home.view.HomeRecommendFragmentView;

/**
 * Created by XY on 2016/9/17.
 */
public class HomeRecommendFragmentPresenter implements OnLoadDataListListener<HomeDto>{
    private HomeRecommendFragmentView mView;
    private HomeRecommendFragmentModel mModel;

    public HomeRecommendFragmentPresenter(HomeRecommendFragmentView mView) {
        this.mView = mView;
        this.mModel=new HomeRecommendFragmentModel();
        mView.showProgress();
    }

    public void LoadData(boolean isload){
        mModel.loadData(isload,this);
    }

    @Override
    public void onSuccess(HomeDto data) {
        mView.newDatas(data);
        mView.hideProgress();
    }

    @Override
    public void onFailure(Throwable e) {
        mView.showLoadFailMsg();
    }
}
