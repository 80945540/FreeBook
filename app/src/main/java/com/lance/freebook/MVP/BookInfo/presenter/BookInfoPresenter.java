package com.lance.freebook.MVP.BookInfo.presenter;

import com.lance.freebook.MVP.BookInfo.model.BookInfoModel;
import com.lance.freebook.MVP.BookInfo.view.BookInfoView;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;

/**
 * Created by Administrator on 2016/9/19.
 */
public class BookInfoPresenter implements OnLoadDataListListener<BookInfoDto>{
    private BookInfoView mView;
    private BookInfoModel mModel;

    public BookInfoPresenter(BookInfoView mView) {
        this.mView = mView;
        mModel=new BookInfoModel();
    }

    public void loadData(int id){
        mModel.loadData(id,this);
        mView.showProgress();
    }

    @Override
    public void onSuccess(BookInfoDto data) {
        if(data.getBookName().equals("")){
            mView.showLoadFailMsg();
        }else{
            mView.newData(data);
            mView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mView.showLoadFailMsg();
    }
}
