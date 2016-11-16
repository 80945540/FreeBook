package com.lance.freebook.MVP.Home.Fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lance.freebook.MVP.Adapter.BookInfoListAdapter;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.BookInfo.BookInfoActivity;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.MVP.Home.presenter.StackInfoFragmentPresenter;
import com.lance.freebook.MVP.Home.view.StackInfoFragmentView;
import com.lance.freebook.R;
import com.lance.freebook.Constant.Constant;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultHeader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;
import com.xiaochao.lcrapiddeveloplibrary.widget.SpringView;

import java.util.List;

import butterknife.BindView;


public class StackInfoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener ,SpringView.OnFreshListener,StackInfoFragmentView{


    @BindView(R.id.stack_info_list)
    RecyclerView stackInfoList;
    @BindView(R.id.stack_info_springview)
    SpringView stackInfoSpringview;
    @BindView(R.id.stack_info_progress)
    ProgressActivity stackInfoProgress;
    private BookTypeDto bookType;
    private BookInfoListAdapter mQuickAdapter;
    private int startPage;
    private StackInfoFragmentPresenter mpPresenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_stack_info, container, false);
    }

    @Override
    protected void initListener() {
        bookType = getArguments().getParcelable("BookType");
        stackInfoSpringview.setListener(this);
        stackInfoSpringview.setHeader(new DefaultHeader(getContext()));
        stackInfoList.setLayoutManager(new LinearLayoutManager(getContext()));
        //如果Item高度固定  增加该属性能够提高效率
        stackInfoList.setHasFixedSize(true);
        //设置适配器
        mQuickAdapter = new BookInfoListAdapter(R.layout.item_book_info_list_layout,null);
        //设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(bookType.getPageLength(),true);
        //将适配器添加到RecyclerView
        stackInfoList.setAdapter(mQuickAdapter);
        //设置自动加载监听
        mQuickAdapter.setOnLoadMoreListener(this);

        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookid", mQuickAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        startPage=bookType.getStartPage();
    }

    @Override
    protected void initData() {
        mpPresenter = new StackInfoFragmentPresenter(this);
        mpPresenter.LaodData(bookType,startPage,false);
    }
    @Override
    public void onRefresh() {
        startPage=bookType.getStartPage();
        mpPresenter.LaodData(bookType,startPage,false);
    }

    @Override
    public void onLoadmore() {

    }

    @Override
    public void onLoadMoreRequested() {
        if(startPage<=bookType.getEndPage()){
            startPage++;
        }
        mpPresenter.LaodData(bookType,startPage,true);
    }

    @Override
    public void showProgress() {
        stackInfoProgress.showLoading();
    }

    @Override
    public void hideProgress() {
        stackInfoProgress.showContent();
    }

    @Override
    public void newBooks(List<BookInfoListDto> newsList) {
        mQuickAdapter.setNewData(newsList);//新增数据
        mQuickAdapter.openLoadMore(bookType.getPageLength(),true);//设置是否可以下拉加载  以及加载条数
        stackInfoSpringview.onFinishFreshAndLoad();//刷新完成
    }

    @Override
    public void addBooks(List<BookInfoListDto> addList) {
        mQuickAdapter.notifyDataChangedAfterLoadMore(addList, true);
    }

    @Override
    public void showLoadFailMsg() {
        toError();
    }

    @Override
    public void showLoadCompleteAllData() {
        //所有数据加载完成后显示
        try {
            mQuickAdapter.notifyDataChangedAfterLoadMore(false);
            View view = getActivity().getLayoutInflater().inflate(R.layout.progress_loading, (ViewGroup) stackInfoList.getParent(), false);
            mQuickAdapter.addFooterView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showNoData() {
        toEmpty();
    }

    public void toError(){
        try {
            mQuickAdapter.notifyDataChangedAfterLoadMore(false);
            View view = getActivity().getLayoutInflater().inflate(R.layout.progress_loading, (ViewGroup) stackInfoList.getParent(), false);
            mQuickAdapter.addFooterView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stackInfoProgress.showError(getResources().getDrawable(R.mipmap.load_error), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackInfoProgress.showLoading();
                //重试
                startPage=bookType.getStartPage();
                mpPresenter.LaodData(bookType,startPage,false);
            }
        });
    }
    public void toEmpty(){
        stackInfoProgress.showEmpty(getResources().getDrawable(R.mipmap.load_no_data),Constant.EMPTY_TITLE_BOOKS, Constant.EMPTY_CONTEXT_BOOKS);
    }
}
