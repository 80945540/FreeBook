package com.lance.freebook.MVP.Home.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;

/*
* 书库
* */

public class HomeStackFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_stack, container, false);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
