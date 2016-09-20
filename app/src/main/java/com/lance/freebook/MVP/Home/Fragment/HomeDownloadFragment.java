package com.lance.freebook.MVP.Home.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.MVP.Adapter.BookDownloadListAdapter;
import com.lance.freebook.MVP.Entity.TasksManagerModel;
import com.lance.freebook.MVP.Home.model.TasksManager;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;
import com.lance.freebook.common.Constant;
import com.lance.freebook.common.Lists;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;


public class HomeDownloadFragment extends BaseFragment  {

    @BindView(R.id.download_recyclerview)
    RecyclerView downloadRecyclerview;
    @BindView(R.id.download_progress)
    ProgressActivity downloadProgress;
    private BookDownloadListAdapter mQuickAdapter;
    private DownloadReceiver receiver;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_download, container, false);
    }

    @Override
    protected void initListener() {
        receiver = new DownloadReceiver();
        IntentFilter intent=new IntentFilter();
        intent.addAction(Constant.ACTON_DOWNLOAD_ADD);
        intent.addAction(Constant.ACTON_DOWNLOAD_NEW);
        getActivity().registerReceiver(receiver,intent);
        downloadRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //如果Item高度固定  增加该属性能够提高效率
        downloadRecyclerview.setHasFixedSize(true);
        //设置适配器
        mQuickAdapter = new BookDownloadListAdapter(R.layout.item_download_layout,null);
        //设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //将适配器添加到RecyclerView
        downloadRecyclerview.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "是否删除"+mQuickAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        TasksManager.getImpl().onCreate();
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
            if(Constant.ACTON_DOWNLOAD_NEW.equals(intent.getAction())){
                mQuickAdapter.setNewData(Lists.dowonloadList);//新增数据
            }
            if(Constant.ACTON_DOWNLOAD_ADD.equals(intent.getAction())){
                TasksManagerModel newModel=intent.getParcelableExtra("newModel");
                mQuickAdapter.add(0,newModel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
