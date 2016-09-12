package com.lance.freebook.MVP.Home.Fragment;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.SmartTabLayout;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.xiaochao.lcrapiddeveloplibrary.SmartTab.UtilsV4.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 书库
* */

public class HomeStackFragment extends BaseFragment {

    @BindView(R.id.stack_tab)
    FrameLayout stackTab;
    @BindView(R.id.stack_category)
    LinearLayout stackCategory;
    @BindView(R.id.stack_header)
    LinearLayout stackHeader;
    @BindView(R.id.stack_viewpager)
    ViewPager stackViewpager;
    private SmartTabLayout viewPagerTab;
    private FragmentPagerItems pages;
    private String[] stacks={"武侠仙侠","言情小说","玄幻小说","现代都市","穿越小说","科幻小说","网游竞技","美文同人","历史军事","惊悚悬疑","管理哲学","学习资料","文学经典","耽美百合"};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_stack, container, false);
    }

    @Override
    protected void initData() {
        stackTab.addView(LayoutInflater.from(getActivity()).inflate(R.layout.tab_top_layout, stackTab, false));
        viewPagerTab = (SmartTabLayout) getActivity().findViewById(R.id.viewpagertab);
        pages = new FragmentPagerItems(getActivity());
        for(int i=0;i<stacks.length;i++){
            Bundle bundle=new Bundle();
            bundle.putString("type",stacks[i]);
            pages.add(FragmentPagerItem.of(stacks[i], StackInfoFragment.class,bundle));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), pages);
        stackViewpager.setAdapter(adapter);
        viewPagerTab.setViewPager(stackViewpager);
        stackViewpager.setOffscreenPageLimit(3);
    }

    @OnClick(R.id.stack_category)
    public void onClick() {
        Toast.makeText(getActivity(), "更多分类", Toast.LENGTH_SHORT).show();
    }
}
