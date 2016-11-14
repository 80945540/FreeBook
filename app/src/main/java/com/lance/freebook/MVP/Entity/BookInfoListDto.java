package com.lance.freebook.MVP.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/12.
 */
public class BookInfoListDto implements Parcelable {

    /**
     * imageUrl : http://img.txt99.cc/Cover/23/23639.jpg
     * bookName : 庶女有毒
     * author : 作者：秦简
     * introduction : 相府庶女，苦熬八年，一朝为后，凤临天下！孰知世事难料，皇帝夫君竟然对她嫡姐一见钟...
     * id : 23639
     */

    private String imageUrl;
    private String bookName;
    private String author;
    private String introduction;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        dest.writeInt(this.id);
    }

    public BookInfoListDto() {
    }

    protected BookInfoListDto(Parcel in) {
        this.imageUrl = in.readString();
        this.bookName = in.readString();
        this.author = in.readString();
        this.introduction = in.readString();
        this.id = in.readInt();
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
