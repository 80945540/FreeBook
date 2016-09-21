package com.lance.freebook.Data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lance.freebook.MVP.Entity.TasksManagerModel;

public  class TasksManagerDBOpenHelper extends SQLiteOpenHelper {
        public final static String DATABASE_NAME = "freebook.db";
        public final static int DATABASE_VERSION = 2;

        public TasksManagerDBOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TasksManagerDBController.TABLE_NAME
                    + String.format(
                    "("
                            + "%s INTEGER PRIMARY KEY, " // id, download id
                            + "%s VARCHAR, " // name
                            + "%s VARCHAR, " // imageurl
                            + "%s VARCHAR, " // AUTHOR
                            + "%s VARCHAR, " // PROGRESS
                            + "%s VARCHAR, " // TYPE
                            + "%s VARCHAR, " // url
                            + "%s VARCHAR " // path
                            + ")"
                    , TasksManagerModel.ID
                    , TasksManagerModel.NAME
                    , TasksManagerModel.IMAGEURL
                    , TasksManagerModel.AUTHOR
                    , TasksManagerModel.PROGRESS
                    , TasksManagerModel.TYPE
                    , TasksManagerModel.URL
                    , TasksManagerModel.PATH

            ));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 1 && newVersion == 2) {
                db.delete(TasksManagerDBController.TABLE_NAME, null, null);
            }
        }
    }