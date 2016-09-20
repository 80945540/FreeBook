package com.lance.freebook.MVP.Entity;

import android.content.ContentValues;

public class TasksManagerModel {
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String IMAGEURL = "imageurl";
    public final static String AUTHOR = "author";
    public final static String PROGRESS = "progress";
    public final static String TYPE = "type";
    public final static String URL = "url";
    public final static String PATH = "path";

    private int id;
    private String name;
    private String imageurl;
    private String author;
    private String type;
    private String progress;
    private String url;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(NAME, name);
        cv.put(IMAGEURL, imageurl);
        cv.put(AUTHOR, author);
        cv.put(TYPE, type);
        cv.put(PROGRESS, progress);
        cv.put(URL, url);
        cv.put(PATH, path);
        return cv;
    }

    @Override
    public String toString() {
        return "TasksManagerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", progress='" + progress + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}