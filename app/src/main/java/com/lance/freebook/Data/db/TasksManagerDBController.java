package com.lance.freebook.Data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;

import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.TasksManagerModel;
import com.lance.freebook.MyApplication.MyApplication;
import com.lance.freebook.Constant.Lists;

import java.util.ArrayList;
import java.util.List;

public class TasksManagerDBController {
    public final static String TABLE_NAME = "freebook";
    public final SQLiteDatabase db;

    public TasksManagerDBController() {
        TasksManagerDBOpenHelper openHelper = new TasksManagerDBOpenHelper(MyApplication.getInstance());

        db = openHelper.getWritableDatabase();
    }

    public List<TasksManagerModel> getAllTasks() {
        final Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        final List<TasksManagerModel> list = new ArrayList<>();
        try {
            if (!c.moveToLast()) {
                return list;
            }

            do {
                TasksManagerModel model = new TasksManagerModel();
                model.setId(c.getInt(c.getColumnIndex(TasksManagerModel.ID)));
                model.setName(c.getString(c.getColumnIndex(TasksManagerModel.NAME)));
                model.setImageurl(c.getString(c.getColumnIndex(TasksManagerModel.IMAGEURL)));
                model.setAuthor(c.getString(c.getColumnIndex(TasksManagerModel.AUTHOR)));
                model.setProgress(c.getString(c.getColumnIndex(TasksManagerModel.PROGRESS)));
                model.setType(c.getString(c.getColumnIndex(TasksManagerModel.TYPE)));
                model.setUrl(c.getString(c.getColumnIndex(TasksManagerModel.URL)));
                model.setPath(c.getString(c.getColumnIndex(TasksManagerModel.PATH)));
                list.add(model);
            } while (c.moveToPrevious());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        Lists.dowonloadList=list;
        return list;
    }

    public TasksManagerModel addTask(BookInfoDto bookInfoDto) {
        String path = patch(bookInfoDto.getBookName() + ".txt");
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
//        final int id = FileDownloadUtils.generateId(bookInfoDto.getBookDownload(), path);
        final int id =(int) System.currentTimeMillis();
        TasksManagerModel model = new TasksManagerModel();
        model.setId(id);
        model.setName(bookInfoDto.getBookName());
        model.setImageurl(bookInfoDto.getBookImageUrl());
        model.setAuthor(bookInfoDto.getBookAuthor());
        model.setProgress(bookInfoDto.getBookProgress());
        model.setType(bookInfoDto.getBookType());
        model.setUrl(bookInfoDto.getBookDownload());
        model.setPath(path);
        final boolean succeed = db.insert(TABLE_NAME, null, model.toContentValues()) != -1;
        if(succeed){
            Lists.dowonloadList.add(model);
        }
        return succeed ? model : null;
    }

    public void removeTask(int id){
        int dbType=db.delete(TABLE_NAME,"id=?",new String[]{id+""});
    }

    public String patch(String flie) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //取得SD卡文件路径
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/freeBookDownload/"+flie;
        }else{
            return Environment.getDownloadCacheDirectory().getAbsolutePath() + "/freeBookDownload/"+flie;
        }
    }
}