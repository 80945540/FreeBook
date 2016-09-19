package com.lance.freebook.MVP.Search.presenter;

import android.content.Context;
import android.net.ConnectivityManager;

import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;
import com.lance.freebook.MVP.Search.View.SearchLabelView;
import com.lance.freebook.MVP.Search.model.SearchModel;
import com.lance.freebook.MyApplication.MyApplication;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class SearchLablePresenter implements OnLoadDataListListener<List<String>>{
    private SearchLabelView mView;
    private SearchModel mModel;

    public SearchLablePresenter(SearchLabelView mView) {
        this.mView = mView;
        mModel=new SearchModel();
    }

    public void loadLableData(){
        mModel.loadSearchLableData(this);
        mView.showProgress();
    }

    @Override
    public void onSuccess(List<String> data) {
        mView.hideProgress();
        if(data.size()==0){
            mView.showNoData();
        }else{
            mView.newData(data);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        if(checkNetworkState()){
            mView.showNoData();
        }else{
            mView.showLoadFailMsg();
        }
    }
    public boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }
}
