package com.lance.freebook.MVP.Search.Fragment;

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
import com.lance.freebook.MVP.Search.View.SearchListView;
import com.lance.freebook.MVP.Search.presenter.SearchPresenter;
import com.lance.freebook.R;
import com.lance.freebook.Constant.Constant;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.List;

import butterknife.BindView;

public class SearchListFragment extends BaseFragment implements SearchListView {


    @BindView(R.id.search_list_recyclerview)
    RecyclerView searchListRecyclerview;
    @BindView(R.id.search_list_progress)
    ProgressActivity searchListProgress;
    private BookInfoListAdapter mQuickAdapter;
    private SearchPresenter presenter;
    private String key;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search_list, container, false);
    }

    @Override
    protected void initListener() {
        searchListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //如果Item高度固定  增加该属性能够提高效率
        searchListRecyclerview.setHasFixedSize(true);
        //设置适配器
        mQuickAdapter = new BookInfoListAdapter(R.layout.item_book_info_list_layout,null);
        //设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //将适配器添加到RecyclerView
        searchListRecyclerview.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookid", mQuickAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new SearchPresenter(this);
    }

    @Override
    public void newData(String key) {
        this.key=key;
        presenter.loadData(key);
    }

    @Override
    public void deleteData() {
        key="";
    }

    @Override
    public void showProgress() {
        searchListProgress.showLoading();
    }

    @Override
    public void hideProgress() {
        searchListProgress.showContent();
    }

    @Override
    public void newData(List<BookInfoListDto> newsList) {
        mQuickAdapter.setNewData(newsList);//新增数据
    }

    @Override
    public void showLoadFailMsg() {
        toError();
    }

    @Override
    public void showNoData() {
        toEmpty();
    }

    public void toError(){
        searchListProgress.showError(getResources().getDrawable(R.mipmap.load_error), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchListProgress.showLoading();
                //重试
                presenter.loadData(key);
            }
        });
    }
    public void toEmpty(){
        searchListProgress.showEmpty(getResources().getDrawable(R.mipmap.load_no_data),Constant.EMPTY_TITLE_SEARCH, Constant.EMPTY_CONTEXT_SEARCH);
    }
}
