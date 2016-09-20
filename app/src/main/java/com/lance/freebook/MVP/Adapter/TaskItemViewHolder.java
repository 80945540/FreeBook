package com.lance.freebook.MVP.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lance.freebook.MyApplication.MyApplication;
import com.lance.freebook.R;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

/**
 * Created by Administrator on 2016/9/20.
 */
public class TaskItemViewHolder extends RecyclerView.ViewHolder {

    public TaskItemViewHolder(View itemView) {
        super(itemView);
        assignViews();
    }
    public View findViewById(final int id) {
        return itemView.findViewById(id);
    }

    /**
     * viewHolder position
     */
    public int position;
    /**
     * download id
     */
    public int id;

    public void update(final int id, final int position) {
        this.id = id;
        this.position = position;
    }


    public void updateDownloaded() {
        taskPb.setMax(1);
        taskPb.setProgress(1);

        taskStatusTv.setText(R.string.tasks_manager_demo_status_completed);
        taskActionBtn.setText(R.string.delete);
    }

    public void updateNotDownloaded(final int status, final long sofar, final long total) {
        if (sofar > 0 && total > 0) {
            final float percent = sofar
                    / (float) total;
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));
        } else {
            taskPb.setMax(1);
            taskPb.setProgress(0);
        }

        switch (status) {
            case FileDownloadStatus.error:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_error);
                break;
            case FileDownloadStatus.paused:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
                break;
            default:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_not_downloaded);
                break;
        }
        taskActionBtn.setText(R.string.start);
    }

    public void updateDownloading(final int status, final long sofar, final long total) {
        final float percent = sofar
                / (float) total;
        taskPb.setMax(100);
        taskPb.setProgress((int) (percent * 100));

        switch (status) {
            case FileDownloadStatus.pending:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
                break;
            case FileDownloadStatus.started:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
                break;
            case FileDownloadStatus.connected:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
                break;
            case FileDownloadStatus.progress:
                taskStatusTv.setText(R.string.tasks_manager_demo_status_progress);
                break;
            default:
                taskStatusTv.setText(MyApplication.getInstance().getString(
                        R.string.tasks_manager_demo_status_downloading, status));
                break;
        }

        taskActionBtn.setText(R.string.pause);
    }

    public ImageView taskImageUrl;
    public TextView taskNameTv;
    public TextView taskAuthorTv;
    public TextView taskTypeTv;
    public TextView taskProgressTv;
    public ProgressBar taskPb;
    public Button taskActionBtn;
    public TextView taskStatusTv;
    public void assignViews() {
        taskImageUrl = (ImageView) findViewById(R.id.task_image_url);
        taskNameTv = (TextView) findViewById(R.id.task_name_tv);
        taskAuthorTv = (TextView) findViewById(R.id.task_author_tv);
        taskTypeTv = (TextView) findViewById(R.id.task_type_tv);
        taskProgressTv = (TextView) findViewById(R.id.task_progress_tv);
        taskPb = (ProgressBar) findViewById(R.id.task_pb);
        taskActionBtn = (Button) findViewById(R.id.task_action_btn);
        taskStatusTv = (TextView) findViewById(R.id.task_status_tv);
    }
}
