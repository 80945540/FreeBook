package com.lance.freebook.MVP.Home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.MVP.Adapter.BGABannerAdapter;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;


public class HomeDownloadFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_download, container, false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
