package com.lance.freebook.MVP.Entity;

import java.util.List;

/**
 * Created by XY on 2016/9/17.
 */
public class HomeDto {
    private List<BannerDto> banner;
    private List<BookInfoListDto> hotBook;
    private List<BookInfoListDto> newBook;

    public List<BannerDto> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDto> banner) {
        this.banner = banner;
    }

    public List<BookInfoListDto> getHotBook() {
        return hotBook;
    }

    public void setHotBook(List<BookInfoListDto> hotBook) {
        this.hotBook = hotBook;
    }

    public List<BookInfoListDto> getNewBook() {
        return newBook;
    }

    public void setNewBook(List<BookInfoListDto> newBook) {
        this.newBook = newBook;
    }
}
