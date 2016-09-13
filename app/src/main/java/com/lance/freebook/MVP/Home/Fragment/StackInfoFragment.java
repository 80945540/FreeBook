package com.lance.freebook.MVP.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.Date.HttpData.HttpData;
import com.lance.freebook.MVP.Adapter.BookInfoListAdapter;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.R;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.container.DefaultHeader;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;
import com.xiaochao.lcrapiddeveloplibrary.widget.SpringView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;


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
        httpdata();
    }
    public void httpdata(){
        HttpData.getInstance().getHtml(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("HomeTabActivity", e.toString());
            }

            @Override
            public void onNext(String html) {
                List<BookInfoListDto> expertListDtos=new ArrayList<BookInfoListDto>();
                Document doc = Jsoup.parse(html);
                Element content = doc.select("ul").first();
                Elements links = content.select("li");
                for (Element link : links) {
                    Elements temp = link.select("a");
                    Elements temp1 = link.select("img");
                    Elements temp3 = link.select("p:eq(2)");
                    Elements temp4 = link.select("p:eq(3)");
                    String relHref = temp.attr("href");
                    String relHref1 = temp1.attr("src");
                    String relHref2 = temp1.attr("alt");
                    String relHref3 = temp3.text();
                    String relHref4 = temp4.text();
                    Log.d("StackInfoFragment", relHref4);
                    expertListDtos.add(new BookInfoListDto(relHref1,relHref2,relHref3,relHref4,relHref));
                }
                mQuickAdapter.setNewData(expertListDtos);//新增数据
                mQuickAdapter.openLoadMore(30,true);//设置是否可以下拉加载  以及加载条数
                stackInfoSpringview.onFinishFreshAndLoad();//刷新完成
                stackInfoProgress.showContent();
            }
        });
    }
    @Override
    public void onRefresh() {
        httpdata();
    }

    @Override
    public void onLoadmore() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
