package com.lance.freebook.MVP.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/14.
 */
public class BookTypeDto implements Parcelable {
    private String BookTypeUrl;
    private String BookTypeName;
    private int StartPage;
    private int EndPage;
    private int pageLength;

    public BookTypeDto(String bookTypeUrl, String bookTypeName, int startPage, int endPage, int pageLength) {
        BookTypeUrl = bookTypeUrl;
        BookTypeName = bookTypeName;
        StartPage = startPage;
        EndPage = endPage;
        this.pageLength = pageLength;
    }

    public String getBookTypeUrl() {
        return BookTypeUrl;
    }

    public void setBookTypeUrl(String bookTypeUrl) {
        BookTypeUrl = bookTypeUrl;
    }

    public String getBookTypeName() {
        return BookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        BookTypeName = bookTypeName;
    }

    public int getStartPage() {
        return StartPage;
    }

    public void setStartPage(int startPage) {
        StartPage = startPage;
    }

    public int getEndPage() {
        return EndPage;
    }

    public void setEndPage(int endPage) {
        EndPage = endPage;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    public static Creator<BookTypeDto> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.BookTypeUrl);
        dest.writeString(this.BookTypeName);
        dest.writeInt(this.StartPage);
        dest.writeInt(this.EndPage);
        dest.writeInt(this.pageLength);
    }

    protected BookTypeDto(Parcel in) {
        this.BookTypeUrl = in.readString();
        this.BookTypeName = in.readString();
        this.StartPage = in.readInt();
        this.EndPage = in.readInt();
        this.pageLength = in.readInt();
    }

    public static final Creator<BookTypeDto> CREATOR = new Creator<BookTypeDto>() {
        @Override
        public BookTypeDto createFromParcel(Parcel source) {
            return new BookTypeDto(source);
        }

        @Override
        public BookTypeDto[] newArray(int size) {
            return new BookTypeDto[size];
        }
    };

    @Override
    public String toString() {
        return "BookTypeDto{" +
                "BookTypeUrl='" + BookTypeUrl + '\'' +
                ", BookTypeName='" + BookTypeName + '\'' +
                ", StartPage=" + StartPage +
                ", EndPage=" + EndPage +
                ", pageLength=" + pageLength +
                '}';
    }
}
