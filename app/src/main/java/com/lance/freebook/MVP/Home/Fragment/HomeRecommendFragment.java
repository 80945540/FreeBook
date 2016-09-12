package com.lance.freebook.MVP.Home.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;


public class HomeRecommendFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_recommend, container, false);
    }

    @Override
    protected void initData() {

    }

}
