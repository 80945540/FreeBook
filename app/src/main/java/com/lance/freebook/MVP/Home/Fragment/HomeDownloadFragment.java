package com.lance.freebook.MVP.Home.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lance.freebook.Dialog.MyDialogDownload;
import com.lance.freebook.Dialog.Mydialog_interface;
import com.lance.freebook.MVP.Adapter.BookDownloadListAdapter;
import com.lance.freebook.MVP.Home.model.TasksManager;
import com.lance.freebook.MVP.Base.BaseFragment;
import com.lance.freebook.R;
import com.lance.freebook.Constant.Constant;
import com.lance.freebook.Constant.Lists;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.io.File;

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
            public boolean onItemLongClick(View view, final int position) {
                final MyDialogDownload myDialogDownload=new MyDialogDownload(getContext(),R.style.MyDialog1);
                myDialogDownload.init(mQuickAdapter.getItem(position).getName(), new Mydialog_interface() {
                    @Override
                    public void onMyyes() {
                        try {
                            new File(mQuickAdapter.getItem(position).getPath()).delete();
                            new File(mQuickAdapter.getItem(position).getPath()+".temp").delete();
                        } catch (Exception e) {
                        }
                        TasksManager.getImpl().removeTask(mQuickAdapter.getItem(position).getId());
                        mQuickAdapter.remove(position);
                        RefreshView();
                        myDialogDownload.dismiss();
                    }

                    @Override
                    public void onMyno() {
                        myDialogDownload.dismiss();
                    }
                });
                myDialogDownload.show();
                return true;
            }
        });
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int action = (int) view.findViewById(R.id.task_action_btn).getTag();
                switch (action){
                    case R.mipmap.download_ok:
                        File file = new File(mQuickAdapter.getItem(position).getPath());
                        if (file.exists()) {
                            Uri path = Uri.fromFile(file);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "text/plain");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            try {
                                startActivity(intent);
                            }
                            catch (Exception e) {
                                Toast.makeText(getContext(), "打开失败", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(), "文件不存在或已被删除", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.mipmap.download_start:
                        Toast.makeText(getContext(), "书籍未下载", Toast.LENGTH_SHORT).show();
                        break;
                    case R.mipmap.download_pause:
                        Toast.makeText(getContext(), "书籍下载中", Toast.LENGTH_SHORT).show();
                        break;
                    case R.mipmap.download_error:
                        break;
                    default:
                        break;
                }
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
                mQuickAdapter.notifyDataSetChanged();
            }
            RefreshView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
