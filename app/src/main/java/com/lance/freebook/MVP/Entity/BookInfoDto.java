package com.lance.freebook.MVP.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/19.
 */
public class BookInfoDto implements Parcelable {
    //图片地址
    private String bookImageUrl;
    //书籍名称
    private String bookName;
    //书籍作者
    private String bookAuthor;
    //书籍类型
    private String bookType;
    //书籍字数
    private String bookLength;
    //书籍进度
    private String bookProgress;
    //最后更新时间
    private String bookUpdateTime;
    //书籍下载地址
    private String bookDownload;
    //书籍简介
    private String bookIntroduction;

    public BookInfoDto(String bookImageUrl, String bookName, String bookAuthor, String bookType, String bookLength, String bookProgress, String bookUpdateTime, String bookDownload, String bookIntroduction) {
        this.bookImageUrl = bookImageUrl;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookType = bookType;
        this.bookLength = bookLength;
        this.bookProgress = bookProgress;
        this.bookUpdateTime = bookUpdateTime;
        this.bookDownload = bookDownload;
        this.bookIntroduction = bookIntroduction;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookLength() {
        return bookLength;
    }

    public void setBookLength(String bookLength) {
        this.bookLength = bookLength;
    }

    public String getBookProgress() {
        return bookProgress;
    }

    public void setBookProgress(String bookProgress) {
        this.bookProgress = bookProgress;
    }

    public String getBookUpdateTime() {
        return bookUpdateTime;
    }

    public void setBookUpdateTime(String bookUpdateTime) {
        this.bookUpdateTime = bookUpdateTime;
    }

    public String getBookDownload() {
        return bookDownload;
    }

    public void setBookDownload(String bookDownload) {
        this.bookDownload = bookDownload;
    }

    public String getBookIntroduction() {
        return bookIntroduction;
    }

    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bookImageUrl);
        dest.writeString(this.bookName);
        dest.writeString(this.bookAuthor);
        dest.writeString(this.bookType);
        dest.writeString(this.bookLength);
        dest.writeString(this.bookProgress);
        dest.writeString(this.bookUpdateTime);
        dest.writeString(this.bookDownload);
        dest.writeString(this.bookIntroduction);
    }

    protected BookInfoDto(Parcel in) {
        this.bookImageUrl = in.readString();
        this.bookName = in.readString();
        this.bookAuthor = in.readString();
        this.bookType = in.readString();
        this.bookLength = in.readString();
        this.bookProgress = in.readString();
        this.bookUpdateTime = in.readString();
        this.bookDownload = in.readString();
        this.bookIntroduction = in.readString();
    }

    public static final Parcelable.Creator<BookInfoDto> CREATOR = new Parcelable.Creator<BookInfoDto>() {
        @Override
        public BookInfoDto createFromParcel(Parcel source) {
            return new BookInfoDto(source);
        }

        @Override
        public BookInfoDto[] newArray(int size) {
            return new BookInfoDto[size];
        }
    };

    @Override
    public String toString() {
        return "BookInfoDto{" +
                "bookImageUrl='" + bookImageUrl + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookType='" + bookType + '\'' +
                ", bookLength='" + bookLength + '\'' +
                ", bookProgress='" + bookProgress + '\'' +
                ", bookUpdateTime='" + bookUpdateTime + '\'' +
                ", bookDownload='" + bookDownload + '\'' +
                ", bookIntroduction='" + bookIntroduction + '\'' +
                '}';
    }
}
