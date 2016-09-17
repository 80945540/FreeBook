package com.lance.freebook.MVP.Entity;

/**
 * Created by XY on 2016/9/17.
 */
public class BannerDto {

    /**
     * imageUrl : http://img1.youzy.cn/content2/thumbs/p00393573.jpeg
     * bannerTitle : banner测试
     * Url :
     */

    private String imageUrl;
    private String bannerTitle;
    private String Url;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
}
