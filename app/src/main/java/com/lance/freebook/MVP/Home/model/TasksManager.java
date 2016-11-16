package com.lance.freebook.MVP.Home.model;

import android.content.Intent;
import android.util.SparseArray;

import com.lance.freebook.Data.db.TasksManagerDBController;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.TasksManagerModel;
import com.lance.freebook.MyApplication.MyApplication;
import com.lance.freebook.Constant.Constant;
import com.lance.freebook.Constant.Lists;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

public class TasksManager {

    private static TasksManager instance;

    public static void init(){
        instance=new TasksManager();
    }

    public static TasksManager getImpl() {
        return instance;
    }

    private TasksManagerDBController dbController;

    private TasksManager() {
        dbController = new TasksManagerDBController();
        Lists.dowonloadList = dbController.getAllTasks();
    }

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    public void updateViewHolder(final int id, final BaseViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }

        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }


    public void onCreate() {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            if(Lists.dowonloadList.size()>0){
                Intent intent=new Intent(Constant.ACTON_DOWNLOAD_NEW);
                MyApplication.getInstance().sendBroadcast(intent);
            }
        }
    }
    public void onDestroy() {
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    /**
     * @param status Download Status
     * @return has already downloaded
     * @see FileDownloadStatus
     */
    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    public long getSoFar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }
    public void removeTask(int id){
        dbController.removeTask(id);
    }
    public TasksManagerModel addTask(BookInfoDto bookInfoDto) {
        final TasksManagerModel newModel = dbController.addTask(bookInfoDto);
        if (newModel != null) {
            if(Lists.dowonloadList.size()==1){
                Intent intent=new Intent(Constant.ACTON_DOWNLOAD_NEW);
                MyApplication.getInstance().sendBroadcast(intent);
            }else{
                Intent intent=new Intent(Constant.ACTON_DOWNLOAD_ADD);
                MyApplication.getInstance().sendBroadcast(intent);
            }
        }
        return newModel;
    }
}