package com.lance.freebook.MVP.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.liulishuo.filedownloader.util.FileDownloadUtils;
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
                int action = (int) ((ImageView)v).getTag();
                switch (action){
                    case R.mipmap.download_ok:
//                        new File(item.getPath()).delete();
//                        updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0, helper);
                        break;
                    case R.mipmap.download_start:
                        toStart(item, helper);
                        break;
                    case R.mipmap.download_pause:
                        helper.setImageResource(R.id.task_action_btn,R.mipmap.download_start);
                        helper.setTag(R.id.task_action_btn,R.mipmap.download_start);
                        FileDownloader.getImpl().pause(item.getId());
                        break;
                    case R.mipmap.download_error:

                        break;
                    default:
                        break;
                }
            }
        });
        TasksManager.getImpl()
                .updateViewHolder(item.getId(), helper);
        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(item.getId(), item.getPath());
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                updateDownloading(status, TasksManager.getImpl().getSoFar(item.getId())
                        , TasksManager.getImpl().getTotal(item.getId()),helper);
                toStart(item, helper);
            } else if (!new File(item.getPath()).exists() &&
                    !new File(FileDownloadUtils.getTempPath(item.getPath())).exists()) {
                // not exist file
                updateNotDownloaded(status, 0, 0,helper);
                toStart(item, helper);
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                helper.setMax(R.id.task_pb, 1);
                helper.setProgress(R.id.task_pb, 1);
                helper.setText(R.id.task_status_tv, "下载完成");
                helper.setImageResource(R.id.task_action_btn,R.mipmap.download_ok);
                helper.setTag(R.id.task_action_btn,R.mipmap.download_ok);
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                updateDownloading(status, TasksManager.getImpl().getSoFar(item.getId())
                        , TasksManager.getImpl().getTotal(item.getId()),helper);
                toStart(item, helper);
            } else {
                // not start
                updateNotDownloaded(status, TasksManager.getImpl().getSoFar(item.getId())
                        , TasksManager.getImpl().getTotal(item.getId()),helper);
                toStart(item, helper);
            }
        } else {
            helper.setText(R.id.task_status_tv, "加载中...");
        }
    }

    private void toStart(TasksManagerModel item, final BaseViewHolder helper) {
        final BaseDownloadTask task = FileDownloader.getImpl().create(item.getUrl())
                .setPath(item.getPath())
                .setCallbackProgressTimes(100)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes, helper);
                    }

                    @Override
                    protected void started(BaseDownloadTask task) {
                        helper.setText(R.id.task_status_tv, "开始下载");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes, helper);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes, helper);
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
                        TasksManager.getImpl().removeTaskForViewHolder(task.getId());
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        helper.setMax(R.id.task_pb, 1);
                        helper.setProgress(R.id.task_pb, 1);
                        helper.setText(R.id.task_status_tv, "下载完成");
                        helper.setImageResource(R.id.task_action_btn,R.mipmap.download_ok);
                        helper.setTag(R.id.task_action_btn,R.mipmap.download_ok);
                        TasksManager.getImpl().removeTaskForViewHolder(task.getId());
                    }
                });

        TasksManager.getImpl()
                .addTaskForViewHolder(task);

        TasksManager.getImpl()
                .updateViewHolder(item.getId(), helper);

        item.setId(task.start());
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
        helper.setImageResource(R.id.task_action_btn,R.mipmap.download_pause);
        helper.setTag(R.id.task_action_btn,R.mipmap.download_pause);
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
            case FileDownloadStatus.INVALID_STATUS:
                helper.setText(R.id.task_status_tv, "重试");
                break;
            default:
                helper.setText(R.id.task_status_tv, "出错");
                break;
        }
        helper.setImageResource(R.id.task_action_btn,R.mipmap.download_start);
        helper.setTag(R.id.task_action_btn,R.mipmap.download_start);
    }
}
