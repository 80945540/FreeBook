package com.lance.freebook.MVP.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.MVP.Adapter.BookInfoListAdapter;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.R;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultHeader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;
import com.xiaochao.lcrapiddeveloplibrary.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StackInfoFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener ,SpringView.OnFreshListener{


    @BindView(R.id.stack_info_list)
    RecyclerView stackInfoList;
    @BindView(R.id.stack_info_springview)
    SpringView stackInfoSpringview;
    @BindView(R.id.stack_info_progress)
    ProgressActivity stackInfoProgress;
    private String type;
    private BookInfoListAdapter mQuickAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_stack_info, container, false);
    }

    @Override
    protected void initData() {
        type = getArguments().getString("type");
        stackInfoSpringview.setListener(this);
        stackInfoSpringview.setHeader(new DefaultHeader(getContext()));
        stackInfoList.setLayoutManager(new LinearLayoutManager(getContext()));
        //如果Item高度固定  增加该属性能够提高效率
        stackInfoList.setHasFixedSize(true);
        //设置页面为加载中..
        stackInfoProgress.showLoading();
        //设置适配器
        mQuickAdapter = new BookInfoListAdapter(R.layout.item_book_info_list_layout,null);
        //设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(10,true);
        //将适配器添加到RecyclerView
        stackInfoList.setAdapter(mQuickAdapter);
        //设置自动加载监听
        mQuickAdapter.setOnLoadMoreListener(this);

        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
            }
        });
        List<BookInfoListDto> expertListDtos=new ArrayList<BookInfoListDto>();
        for(int i=0;i<30;i++){
            expertListDtos.add(new BookInfoListDto("http://img.txt99.cc/Cover/38/38848.jpg","美人犹记","七和香","上一辈子，韩元蝶就没有喜欢过程安澜，重生之后，她高兴坏了，这次终于可以和程安澜毫无关系了吧！..."));
        }
        mQuickAdapter.setNewData(expertListDtos);//新增数据
        mQuickAdapter.openLoadMore(30,true);//设置是否可以下拉加载  以及加载条数
        stackInfoSpringview.onFinishFreshAndLoad();//刷新完成
        stackInfoProgress.showContent();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
