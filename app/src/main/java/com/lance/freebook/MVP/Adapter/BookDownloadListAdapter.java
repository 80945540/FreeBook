package com.lance.freebook.MVP.Adapter;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.TasksManagerModel;
import com.lance.freebook.MVP.Home.model.TasksManager;
import com.lance.freebook.MyApplication.MyApplication;
import com.lance.freebook.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BookDownloadListAdapter extends BaseQuickAdapter<TasksManagerModel> {

    public BookDownloadListAdapter(int layoutResId, List<TasksManagerModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TasksManagerModel item) {
        Glide.with(mContext)
                .load(item.getImageurl())
                .crossFade()
                .placeholder(R.mipmap.nocover)
                .into((ImageView) helper.getView(R.id.task_image_url));
        helper.setText(R.id.task_name_tv, item.getName());
        helper.setText(R.id.task_author_tv, item.getAuthor());
        helper.setText(R.id.task_type_tv, item.getType());
        helper.setText(R.id.task_progress_tv, item.getProgress());
        helper.setOnClickListener(R.id.task_action_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = ((Button)v).getText().toString();
                if (action.equals(v.getResources().getString(R.string.pause))) {
                    // to pause
                    helper.setText(R.id.task_action_btn, "开始");
                    FileDownloader.getImpl().pause(item.getId());
                } else if (action.equals(v.getResources().getString(R.string.start))) {
                    // to start
                    final BaseDownloadTask task = FileDownloader.getImpl().create(item.getUrl())
                            .setPath(item.getPath())
                            .setCallbackProgressTimes(100)
                            .setListener(new FileDownloadListener() {
                                @Override
                                protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes, helper);
                                    Log.d("pending", "soFarBytes:" + soFarBytes+"     totalBytes:"+totalBytes);
                                }

                                @Override
                                protected void started(BaseDownloadTask task) {
                                    helper.setText(R.id.task_status_tv, "开始下载");
                                }

                                @Override
                                protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                    updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes, helper);
                                    Log.d("connected", "soFarBytes:" + soFarBytes+"     totalBytes:"+totalBytes);
                                }

                                @Override
                                protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes, helper);
                                    Log.d("progress", "soFarBytes:" + soFarBytes+"     totalBytes:"+totalBytes);
                                }

                                @Override
                                protected void error(BaseDownloadTask task, Throwable e) {
                                    updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes(), task.getLargeFileTotalBytes(), helper);
                                    TasksManager.getImpl().removeTaskForViewHolder(task.getId());
                                }

                                @Override
                                protected void warn(BaseDownloadTask task) {

                                }

                                @Override
                                protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                    updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes, helper);
                                    Log.d("paused", "soFarBytes:" + soFarBytes+"     totalBytes:"+totalBytes);
                                    TasksManager.getImpl().removeTaskForViewHolder(task.getId());
                                }

                                @Override
                                protected void completed(BaseDownloadTask task) {
                                    helper.setMax(R.id.task_pb, 1);
                                    helper.setProgress(R.id.task_pb, 1);
                                    helper.setText(R.id.task_status_tv, "下载完成");
                                    helper.setText(R.id.task_action_btn, "删除");
                                    TasksManager.getImpl().removeTaskForViewHolder(task.getId());
                                }
                            });

                    TasksManager.getImpl()
                            .addTaskForViewHolder(task);

                    TasksManager.getImpl()
                            .updateViewHolder(item.getId(), helper);

                    task.start();
                } else if (action.equals(v.getResources().getString(R.string.delete))) {
                    // to delete
                    new File(item.getPath()).delete();
                    updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0, helper);
                }
            }
        });
    }

    private void updateDownloading(final int status,  long sofar,  long total, BaseViewHolder helper) {
        float percent;
        try {
            percent = sofar / (float) total;
        } catch (Exception e) {
            percent=0f;
        }
        helper.setMax(R.id.task_pb, 100);
        helper.setProgress(R.id.task_pb, (int) (percent * 100));
        switch (status) {
            case FileDownloadStatus.pending:
                helper.setText(R.id.task_status_tv, "队列中");
                break;
            case FileDownloadStatus.started:
                helper.setText(R.id.task_status_tv, "开始下载");
                break;
            case FileDownloadStatus.connected:
                helper.setText(R.id.task_status_tv, "已连接上");
                break;
            case FileDownloadStatus.progress:
                helper.setText(R.id.task_status_tv, "下载中..");
                break;
            default:
                helper.setText(R.id.task_status_tv, "正在下载");
                break;
        }
        helper.setText(R.id.task_action_btn, "暂停");
    }

    public void updateNotDownloaded(final int status, final long sofar, final long total, BaseViewHolder helper) {
        if (sofar > 0 && total > 0) {
            final float percent = sofar
                    / (float) total;
            helper.setMax(R.id.task_pb, 100);
            helper.setProgress(R.id.task_pb, (int) (percent * 100));
        } else {
            helper.setMax(R.id.task_pb, 1);
            helper.setProgress(R.id.task_pb, 0);
        }

        switch (status) {
            case FileDownloadStatus.error:
                helper.setText(R.id.task_status_tv, "错误");
                break;
            case FileDownloadStatus.paused:
                helper.setText(R.id.task_status_tv, "暂停");
                break;
            default:
                helper.setText(R.id.task_status_tv, "出错");
                break;
        }
        helper.setText(R.id.task_action_btn, "开始");
    }
}
