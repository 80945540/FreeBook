package com.lance.freebook.MVP.Search.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;
import com.lance.freebook.Widget.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchLabelFragment extends BaseFragment {


    @BindView(R.id.search_lable_tagcloud)
    TagCloudView searchLableTagcloud;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_search_label, container, false);
    }

    @Override
    protected void initListener() {
        searchLableTagcloud.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        List<String> stringList=new ArrayList<String>();
        stringList.add("唐家三少");
        stringList.add("天蚕土豆");
        stringList.add("鱼人二代");
        stringList.add("斗罗大陆");
        stringList.add("太古神王");
        stringList.add("军婚");
        stringList.add("校花的贴身高手");
        stringList.add("翻译官");
        stringList.add("大主宰");
        stringList.add("余罪");
        stringList.add("废材");
        stringList.add("完美世界");
        stringList.add("异能");
        stringList.add("总裁");
        stringList.add("豪门");
        stringList.add("种田");
        stringList.add("系统");
        stringList.add("南派三叔");
        searchLableTagcloud.setTags(stringList);

    }
    public interface Taglistterner{
        void toTagClick(int position);
    }
}
