package com.lance.freebook.MVP.Home.presenter;

import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Home.model.HomeStackFragmentModel;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;
import com.lance.freebook.MVP.Home.view.HomeStackFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class HomeStackFragmentPresenter implements OnLoadDataListListener<List<BookTypeDto>> {

    private HomeStackFragmentModel mModel;
    private HomeStackFragmentView mView;

    public HomeStackFragmentPresenter(HomeStackFragmentView mView) {
        this.mView = mView;
        this.mModel=new HomeStackFragmentModel();
        mView.showProgress();
    }
    public void loadDta(){
        mModel.LoadData(this);
    }

    @Override
    public void onSuccess(List<BookTypeDto> list) {
        if(list.size()==0){
            mView.showNoData();
        }else {
            mView.newData(list);
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mView.showLoadFailMsg();
    }
}
