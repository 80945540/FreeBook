package com.lance.freebook.MVP.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/14.
 */
public class BookTypeDto implements Parcelable {

    /**
     * id : 1
     * bookTypeid : 1
     * bookTypeImageUrl : http://odog3v89f.bkt.clouddn.com/image/wxxs.png
     * bookTypeName : 武侠仙侠
     * startPage : 1
     * endPage : 99
     * pageLength : 30
     */

    private int id;
    private int bookTypeid;
    private String bookTypeImageUrl;
    private String bookTypeName;
    private int startPage;
    private int endPage;
    private int pageLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookTypeid() {
        return bookTypeid;
    }

    public void setBookTypeid(int bookTypeid) {
        this.bookTypeid = bookTypeid;
    }

    public String getBookTypeImageUrl() {
        return bookTypeImageUrl;
    }

    public void setBookTypeImageUrl(String bookTypeImageUrl) {
        this.bookTypeImageUrl = bookTypeImageUrl;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.bookTypeid);
        dest.writeString(this.bookTypeImageUrl);
        dest.writeString(this.bookTypeName);
        dest.writeInt(this.startPage);
        dest.writeInt(this.endPage);
        dest.writeInt(this.pageLength);
    }

    public BookTypeDto() {
    }

    protected BookTypeDto(Parcel in) {
        this.id = in.readInt();
        this.bookTypeid = in.readInt();
        this.bookTypeImageUrl = in.readString();
        this.bookTypeName = in.readString();
        this.startPage = in.readInt();
        this.endPage = in.readInt();
        this.pageLength = in.readInt();
    }

    public static final Parcelable.Creator<BookTypeDto> CREATOR = new Parcelable.Creator<BookTypeDto>() {
        @Override
        public BookTypeDto createFromParcel(Parcel source) {
            return new BookTypeDto(source);
        }

        @Override
        public BookTypeDto[] newArray(int size) {
            return new BookTypeDto[size];
        }
    };
}
