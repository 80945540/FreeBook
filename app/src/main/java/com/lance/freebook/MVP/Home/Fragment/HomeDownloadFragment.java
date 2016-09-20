package com.lance.freebook.MVP.Home.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lance.freebook.MVP.Adapter.TaskItemAdapter;
import com.lance.freebook.MVP.Home.model.TasksManager;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;
import com.lance.freebook.common.Constant;
import com.lance.freebook.common.Lists;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;


public class HomeDownloadFragment extends BaseFragment  {

    @BindView(R.id.download_recyclerview)
    RecyclerView downloadRecyclerview;
    @BindView(R.id.download_progress)
    ProgressActivity downloadProgress;
    private TaskItemAdapter mQuickAdapter;
    private DownloadReceiver receiver;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_download, container, false);
    }
    public void postNotifyDataChanged() {
        if (mQuickAdapter != null) {
            if (mQuickAdapter != null) {
                RefreshView();
                mQuickAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected void initListener() {
        receiver = new DownloadReceiver();
        IntentFilter intent=new IntentFilter();
        intent.addAction(Constant.ACTON_DOWNLOAD);
        getActivity().registerReceiver(receiver,intent);
        downloadRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //如果Item高度固定  增加该属性能够提高效率
        downloadRecyclerview.setHasFixedSize(true);
        //设置适配器
        mQuickAdapter = new TaskItemAdapter();
        //将适配器添加到RecyclerView
        downloadRecyclerview.setAdapter(mQuickAdapter);

        TasksManager.getImpl().onCreate(new WeakReference<>(this));
    }

    @Override
    protected void initData() {
        RefreshView();
    }

    private void RefreshView() {
        if(Lists.dowonloadList.size()==0){
            toEmpty();
        }else{
            downloadProgress.showContent();
        }
    }

    public void toEmpty(){
        downloadProgress.showEmpty(getResources().getDrawable(R.mipmap.load_no_data), Constant.EMPTY_TITLE_DOWNLOAD, Constant.EMPTY_CONTEXT_DOWNLOAD);
    }
    public class DownloadReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constant.ACTON_DOWNLOAD.equals(intent.getAction())){
                postNotifyDataChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
