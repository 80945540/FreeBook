package com.lance.freebook.MVP.Search.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.MVP.Search.View.SearchLabelView;
import com.lance.freebook.MVP.Search.presenter.SearchLablePresenter;
import com.lance.freebook.R;
import com.lance.freebook.Widget.TagCloudView;
import com.lance.freebook.Constant.Constant;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SearchLabelFragment extends BaseFragment implements SearchLabelView{


    @BindView(R.id.search_lable_tagcloud)
    TagCloudView searchLableTagcloud;

    @BindView(R.id.search_lable_progress)
    ProgressActivity searchLableProgress;
    private Taglistterner taglistterner;
    List<String> stringList = new ArrayList<String>();
    private SearchLablePresenter presenter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search_label, container, false);
    }

    @Override
    protected void initListener() {
        try {
            taglistterner = (Taglistterner) getFragmentManager().getFragments().get(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchLableTagcloud.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                try {
                    taglistterner.toTagClick(stringList.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new SearchLablePresenter(this);
        presenter.loadLableData();

    }
    @Override
    public void showProgress() {
        searchLableProgress.showLoading();
    }

    @Override
    public void hideProgress() {
        searchLableProgress.showContent();
    }

    @Override
    public void newData(List<String> data) {
        stringList=data;
        searchLableTagcloud.setTags(stringList);
    }

    @Override
    public void showLoadFailMsg() {
        toError();
    }

    @Override
    public void showNoData() {
        toEmpty();
    }

    public interface Taglistterner {
        void toTagClick(String key);
    }
    public void toError(){
        searchLableProgress.showError(getResources().getDrawable(R.mipmap.load_error), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLableProgress.showLoading();
                //重试
                presenter.loadLableData();
            }
        });
    }
    public void toEmpty(){
        searchLableProgress.showEmpty(getResources().getDrawable(R.mipmap.load_no_data),Constant.EMPTY_TITLE_SEARCH, Constant.EMPTY_CONTEXT_SEARCH);
    }
}
