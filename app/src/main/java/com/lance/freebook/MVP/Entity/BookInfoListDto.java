package com.lance.freebook.MVP.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/12.
 */
public class BookInfoListDto implements Parcelable {
    private String imageUrl;
    private String bookName;
    private String author;
    private String introduction;
    private String codeId;

    public BookInfoListDto(String imageUrl, String bookName, String author, String introduction, String codeId) {
        this.imageUrl = imageUrl;
        this.bookName = bookName;
        this.author = author;
        this.introduction = introduction;
        this.codeId = codeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"imageUrl\"=\"" + imageUrl + '\"' +
                ", \"bookName\"=\"" + bookName + '\"' +
                ", \"author\"=\"" + author + '\"' +
                ", \"introduction\"=\"" + introduction + '\"' +
                ", \"codeId\"=\"" + codeId + '\"' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.bookName);
        dest.writeString(this.author);
        dest.writeString(this.introduction);
        dest.writeString(this.codeId);
    }

    protected BookInfoListDto(Parcel in) {
        this.imageUrl = in.readString();
        this.bookName = in.readString();
        this.author = in.readString();
        this.introduction = in.readString();
        this.codeId = in.readString();
    }

    public static final Parcelable.Creator<BookInfoListDto> CREATOR = new Parcelable.Creator<BookInfoListDto>() {
        @Override
        public BookInfoListDto createFromParcel(Parcel source) {
            return new BookInfoListDto(source);
        }

        @Override
        public BookInfoListDto[] newArray(int size) {
            return new BookInfoListDto[size];
        }
    };
}
