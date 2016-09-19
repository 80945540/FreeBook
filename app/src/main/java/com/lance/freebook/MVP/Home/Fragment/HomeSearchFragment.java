package com.lance.freebook.MVP.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.Home.model.OnLoadDataListListener;
import com.lance.freebook.MVP.Search.Fragment.FragmentControllerSearch;
import com.lance.freebook.MVP.Search.Fragment.SearchLabelFragment;
import com.lance.freebook.MVP.Search.View.SearchListView;
import com.lance.freebook.MVP.Search.presenter.SearchPresenter;
import com.lance.freebook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeSearchFragment extends BaseFragment implements SearchLabelFragment.Taglistterner {

    @BindView(R.id.search_edit_content)
    EditText searchEditContent;
    @BindView(R.id.search_image_exit)
    ImageView searchImageExit;
    @BindView(R.id.search_layout_edit)
    LinearLayout searchLayoutEdit;
    @BindView(R.id.search_button_search)
    Button searchButtonSearch;
    @BindView(R.id.search_framelayout_content)
    FrameLayout searchFramelayoutContent;
    private FragmentControllerSearch controller;
    private SearchPresenter presenter;

    public SearchLabelFragment.Taglistterner getTaglistterner(){
        return this;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_search, container, false);
    }

    @Override
    protected void initListener() {
        //输入框焦点监听
        searchEditContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchLayoutEdit.setBackgroundResource(R.drawable.edit_bj_focus);
                } else {
                    searchLayoutEdit.setBackgroundResource(R.drawable.edit_bj_gray);
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchEditContent.getWindowToken(),0);
                }
            }
        });
        //输入框文字监听
        searchEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchEditContent.getText().toString().length() == 0) {
                    searchImageExit.setVisibility(View.GONE);
                } else {
                    searchImageExit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        controller = FragmentControllerSearch.getInstance(getActivity(), R.id.search_framelayout_content);
        controller.showFragment(0);
        presenter = new SearchPresenter((SearchListView) controller.getFragment(1));
    }
    @OnClick({R.id.search_image_exit, R.id.search_layout_edit, R.id.search_button_search,R.id.search_edit_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_image_exit:
                searchEditContent.setText("");
                controller.showFragment(0);
                presenter.hideSearchList();
                break;
            case R.id.search_layout_edit:
                searchEditContent.setFocusable(true);
                searchEditContent.setFocusableInTouchMode(true);
                searchEditContent.requestFocus();
                break;
            case R.id.search_button_search:
                if (searchEditContent.getText().toString().length() > 0) {
                    searchEditContent.setFocusable(false);
                    searchEditContent.clearFocus();//失去焦点
                    controller.showFragment(1);
                    presenter.showSearchList(searchEditContent.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "请输入书名或作者", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_edit_content:
                searchEditContent.setFocusable(true);
                searchEditContent.setFocusableInTouchMode(true);
                searchEditContent.requestFocus();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentControllerSearch.onDestroy();
    }

    @Override
    public void toTagClick(String key) {
        Log.d("HomeSearchFragment", key);
        searchEditContent.setFocusable(false);
        searchEditContent.clearFocus();//失去焦点
        controller.showFragment(1);
        searchEditContent.setText(key);
        presenter.showSearchList(searchEditContent.getText().toString());
    }
}
