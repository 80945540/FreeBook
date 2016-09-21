package com.lance.freebook.MVP.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.MVP.Adapter.BGABannerAdapter;
import com.lance.freebook.MVP.Adapter.BookInfoGridAdapter;
import com.lance.freebook.MVP.Adapter.BookInfoListAdapter;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.BookInfo.BookInfoActivity;
import com.lance.freebook.MVP.Entity.BannerDto;
import com.lance.freebook.MVP.Entity.HomeDto;
import com.lance.freebook.MVP.Home.presenter.HomeRecommendFragmentPresenter;
import com.lance.freebook.MVP.Home.view.HomeRecommendFragmentView;
import com.lance.freebook.R;
import com.lance.freebook.common.Constant;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;


public class HomeRecommendFragment extends BaseFragment implements HomeRecommendFragmentView{

    @BindView(R.id.home_recommend_banner)
    BGABanner homeRecommendBanner;
    @BindView(R.id.home_downlaod_recommend_hot)
    RecyclerView homeDownlaodRecommendHot;
    @BindView(R.id.home_downlaod_recommend_new)
    RecyclerView homeDownlaodRecommendNew;
    @BindView(R.id.home_recommend_progress)
    ProgressActivity homeRecommendProgress;
    private HomeRecommendFragmentPresenter presenter;
    private BookInfoGridAdapter mQuickAdapterHot;
    private BookInfoGridAdapter mQuickAdapterNew;
    private List<BannerDto> bannerList=new ArrayList<BannerDto>();
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_recommend, container, false);
    }

    @Override
    protected void initListener() {
        //首页Banner点击事件
        homeRecommendBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                Intent intent=new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookurl", bannerList.get(position).getUrl());
                intent.putExtra("bookname", bannerList.get(position).getBannerTitle());
                startActivity(intent);
            }
        });
        homeDownlaodRecommendHot.setLayoutManager(new GridLayoutManager(getActivity(),4));
        homeDownlaodRecommendNew.setLayoutManager(new GridLayoutManager(getActivity(),4));
        //如果Item高度固定  增加该属性能够提高效率
        homeDownlaodRecommendHot.setHasFixedSize(true);
        homeDownlaodRecommendNew.setHasFixedSize(true);
        mQuickAdapterHot = new BookInfoGridAdapter(R.layout.item_home_books_layout,null);
        mQuickAdapterNew = new BookInfoGridAdapter(R.layout.item_home_books_layout,null);
        //设置加载动画
        mQuickAdapterHot.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mQuickAdapterNew.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        homeDownlaodRecommendHot.setAdapter(mQuickAdapterHot);
        homeDownlaodRecommendNew.setAdapter(mQuickAdapterNew);

        mQuickAdapterHot.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookurl", mQuickAdapterHot.getItem(position).getCodeId());
                intent.putExtra("bookname", mQuickAdapterHot.getItem(position).getBookName());
                startActivity(intent);
            }
        });
        mQuickAdapterNew.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookurl", mQuickAdapterNew.getItem(position).getCodeId());
                intent.putExtra("bookname", mQuickAdapterNew.getItem(position).getBookName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new HomeRecommendFragmentPresenter(this);
        presenter.LoadData();
    }

    @Override
    public void showProgress() {
        homeRecommendProgress.showLoading();
    }

    @Override
    public void hideProgress() {
        homeRecommendProgress.showContent();
    }

    @Override
    public void newDatas(HomeDto data) {
        bannerList=data.getBanner();
        List<String> bannerTitle = new ArrayList<String>();
        List<String> bannerImage = new ArrayList<String>();
        for(int i=0;i<data.getBanner().size();i++){
            bannerTitle.add(data.getBanner().get(i).getBannerTitle());
            bannerImage.add(data.getBanner().get(i).getImageUrl());
        }
        homeRecommendBanner.setAdapter(new BGABannerAdapter(getActivity()));
        homeRecommendBanner.setData(bannerImage, bannerTitle);

        mQuickAdapterHot.setNewData(data.getHotBook());//新增数据
        mQuickAdapterNew.setNewData(data.getNewBook());//新增数据
    }

    @Override
    public void showLoadFailMsg() {
        toError();
    }
    public void toError(){
        homeRecommendProgress.showError(getResources().getDrawable(R.mipmap.load_error), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeRecommendProgress.showLoading();
                //重试
                presenter.LoadData();
            }
        });
    }
}
