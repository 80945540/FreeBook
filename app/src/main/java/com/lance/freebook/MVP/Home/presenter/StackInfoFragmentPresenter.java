package com.lance.freebook.MVP.Home.presenter;

import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;
import com.lance.freebook.MVP.Home.model.StackInfoFragmentModel;
import com.lance.freebook.MVP.Home.view.StackInfoFragmentView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class StackInfoFragmentPresenter implements OnLoadDataListListener<List<BookInfoListDto>> {
    private StackInfoFragmentView mView;
    private StackInfoFragmentModel mModel;
    private boolean isLoad=false;
    private BookTypeDto bookTypeDto;
    private int page;
    public StackInfoFragmentPresenter(StackInfoFragmentView stackInfoFragmentView) {
        this.mView = stackInfoFragmentView;
        this.mModel = new StackInfoFragmentModel();
        mView.showProgress();
    }

    public void LaodData(BookTypeDto bookTypeDto,int page,boolean isLoad){
        this.isLoad=isLoad;
        this.bookTypeDto=bookTypeDto;
        this.page=page;
        mModel.LoadData(bookTypeDto.getBookTypeid(),page,this);

    }

    @Override
    public void onSuccess(List<BookInfoListDto> list) {
        if(isLoad){
            if(page<=bookTypeDto.getEndPage()){
                if(list.size()==0){
                    mView.showLoadCompleteAllData();
                }else{
                    mView.addBooks(list);
                }
            }else{
                mView.showLoadCompleteAllData();
            }
        }else{
            if(list.size()==0){
                mView.showNoData();
            }else{
                mView.newBooks(list);
                mView.hideProgress();
            }
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mView.showLoadFailMsg();
    }
}
