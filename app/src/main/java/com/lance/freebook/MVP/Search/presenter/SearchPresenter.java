package com.lance.freebook.MVP.Search.presenter;

import android.content.Context;
import android.net.ConnectivityManager;

import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;
import com.lance.freebook.MVP.Search.View.SearchListView;
import com.lance.freebook.MVP.Search.model.SearchModel;
import com.lance.freebook.MyApplication.MyApplication;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SearchPresenter implements OnLoadDataListListener<List<BookInfoListDto>>{
    SearchListView mView;
    SearchModel mModel;
    public SearchPresenter(SearchListView mView) {
        this.mView = mView;
        mModel=new SearchModel();
    }
    public void showSearchList(String key){
        mView.newData(key);
    }
    public void hideSearchList(){
        mView.deleteData();
    }
    public void loadData(String key){
        mView.showProgress();
        mModel.loadData(key,this);
    }

    @Override
    public void onSuccess(List<BookInfoListDto> data) {
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
