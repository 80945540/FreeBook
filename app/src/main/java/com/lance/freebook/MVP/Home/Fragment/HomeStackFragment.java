package com.lance.freebook.MVP.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Home.BookClassActivity;
import com.lance.freebook.MVP.Home.presenter.HomeStackFragmentPresenter;
import com.lance.freebook.MVP.Home.view.HomeStackFragmentView;
import com.lance.freebook.R;
import com.lance.freebook.Constant.Constant;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.SmartTabLayout;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItems;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 书库
* */

public class HomeStackFragment extends BaseFragment implements HomeStackFragmentView{

    @BindView(R.id.stack_tab)
    FrameLayout stackTab;
    @BindView(R.id.stack_category)
    LinearLayout stackCategory;
    @BindView(R.id.stack_header)
    LinearLayout stackHeader;
    @BindView(R.id.stack_viewpager)
    ViewPager stackViewpager;
    @BindView(R.id.stack_progress)
    ProgressActivity stackProgress;
    private SmartTabLayout viewPagerTab;
    private FragmentPagerItems pages;
    private HomeStackFragmentPresenter mPresenter;
    private final int BOOK_CLASS_TYPE=1;
    private List<BookTypeDto> bookTypeList=new ArrayList<BookTypeDto>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_stack, container, false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter = new HomeStackFragmentPresenter(this);
        stackTab.addView(LayoutInflater.from(getActivity()).inflate(R.layout.tab_top_layout, stackTab, false));
        viewPagerTab = (SmartTabLayout) getActivity().findViewById(R.id.viewpagertab);
        pages = new FragmentPagerItems(getActivity());
        stackViewpager.setOffscreenPageLimit(3);
        mPresenter.loadDta();
    }

    @OnClick(R.id.stack_category)
    public void onClick() {
        Intent intent=new Intent(getActivity(), BookClassActivity.class);
        intent.putParcelableArrayListExtra("bookTypeList", (ArrayList<? extends Parcelable>) bookTypeList);
        startActivityForResult(intent,BOOK_CLASS_TYPE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case BOOK_CLASS_TYPE:
                if(resultCode!=-1){
                    stackViewpager.setCurrentItem(resultCode);
                }
                break;
        }
    }

    public void toError(){
        stackProgress.showError(getResources().getDrawable(R.mipmap.load_error), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackProgress.showLoading();
                //重试
                mPresenter.loadDta();
            }
        });
    }
    public void toEmpty(){
        stackProgress.showEmpty(getResources().getDrawable(R.mipmap.load_no_data),Constant.EMPTY_TITLE_BOOKS,Constant.EMPTY_CONTEXT_BOOKS);
    }

    @Override
    public void showProgress() {
        stackProgress.showLoading();
    }

    @Override
    public void hideProgress() {
        stackProgress.showContent();
    }

    @Override
    public void newData(List<BookTypeDto> bookTypeDtoList) {
        bookTypeList=bookTypeDtoList;
        for (int i = 0; i < bookTypeDtoList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("BookType", bookTypeDtoList.get(i));
            pages.add(FragmentPagerItem.of(bookTypeDtoList.get(i).getBookTypeName(), StackInfoFragment.class, bundle));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), pages);
        stackViewpager.setAdapter(adapter);
        viewPagerTab.setViewPager(stackViewpager);
    }

    @Override
    public void showLoadFailMsg() {
        toError();
    }

    @Override
    public void showNoData() {
        toEmpty();
    }
}
