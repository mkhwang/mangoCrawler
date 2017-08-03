package com.enliple.crawler.parse.domain;

import com.enliple.crawler.parse.connect.PageConnection;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class ParsingInfo {
    private String url;
    private String categoryCode;
    private String mobileUrl;
    private String pCode;
    private String scCode;
    private String shopName;
    private String categoryMatchCode;
    private String siteEtc;
    private String imageTransform;
    private String shopType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCategoryMatchCode() {
        return categoryMatchCode;
    }

    public void setCategoryMatchCode(String categoryMatchCode) {
        this.categoryMatchCode = categoryMatchCode;
    }

    public String getSiteEtc() {
        return siteEtc;
    }

    public void setSiteEtc(String siteEtc) {
        this.siteEtc = siteEtc;
    }

    public String getImageTransform() {
        return imageTransform;
    }

    public void setImageTransform(String imageTransform) {
        this.imageTransform = imageTransform;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
