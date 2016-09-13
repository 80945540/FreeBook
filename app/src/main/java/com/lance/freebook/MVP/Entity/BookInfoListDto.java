package com.lance.freebook.MVP.Entity;

/**
 * Created by Administrator on 2016/9/12.
 */
public class BookInfoListDto {
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
}
