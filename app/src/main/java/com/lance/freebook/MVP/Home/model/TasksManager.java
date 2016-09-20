package com.lance.freebook.MVP.Home.model;

import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.lance.freebook.Data.db.TasksManagerDBController;
import com.lance.freebook.MVP.Adapter.TaskItemViewHolder;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.TasksManagerModel;
import com.lance.freebook.MVP.Home.Fragment.HomeDownloadFragment;
import com.lance.freebook.MyApplication.MyApplication;
import com.lance.freebook.common.Constant;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import java.lang.ref.WeakReference;
import java.util.List;

public class TasksManager {

    private final static class HolderClass {
        private final static TasksManager INSTANCE
                = new TasksManager();
    }
    public static TasksManager getImpl() {
        return HolderClass.INSTANCE;
    }

    private TasksManagerDBController dbController;

    private List<TasksManagerModel> modelList;

    private TasksManager() {
        dbController = new TasksManagerDBController();
        modelList = dbController.getAllTasks();
    }

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    public void updateViewHolder(final int id, final TaskItemViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }

        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }

    private FileDownloadConnectListener listener;

    private void registerServiceConnectionListener(final WeakReference<HomeDownloadFragment>
                                                           activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {

            @Override
            public void connected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }
        };

        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    public void onCreate(final WeakReference<HomeDownloadFragment> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);

        }
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    public TasksManagerModel get(final int position) {
        return modelList.get(position);
    }

    public TasksManagerModel getById(final int id) {
        for (TasksManagerModel model : modelList) {
            if (model.getId() == id) {
                return model;
            }
        }

        return null;
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

    public int getTaskCounts() {
        return modelList.size();
    }

    public TasksManagerModel addTask(BookInfoDto bookInfoDto) {
        String path=patch(bookInfoDto.getBookName()+".txt");
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        final int id =(int) System.currentTimeMillis();
        TasksManagerModel model = getById(id);
        if (model != null) {
            return model;
        }
        final TasksManagerModel newModel = dbController.addTask(bookInfoDto);
        if (newModel != null) {
            modelList.add(newModel);
        }
        Intent intent=new Intent(Constant.ACTON_DOWNLOAD);
        MyApplication.getInstance().sendBroadcast(intent);
        return newModel;
    }
    public String patch(String flie){
//        if (FileDownloadHelper.getAppContext().getExternalCacheDir() == null) {
//            return Environment.getDownloadCacheDirectory().getAbsolutePath() + "/download/"+flie;
//        } else {
//            //noinspection ConstantConditions
//            return FileDownloadHelper.getAppContext().getExternalCacheDir().getAbsolutePath() +"/download/"+flie;
//        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //取得SD卡文件路径
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/freeBookDownload/"+flie;
        }else{
            return Environment.getDownloadCacheDirectory().getAbsolutePath() + "/freeBookDownload/"+flie;
        }
    }
}