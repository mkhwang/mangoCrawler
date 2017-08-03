package com.enliple.crawler.parse.domain;

import com.enliple.crawler.parse.connect.PageConnection;
import com.enliple.crawler.parse.connect.impl.JSoupConnection;
import com.enliple.crawler.parse.connect.impl.UrlStreamConnect;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class ParsePattern {
    private String shopType;
    private String scCode;
    private String productListPattern;
    private String titlePattern;
    private String urlPattern;
    private String imgUrlPattern;
    private String pricePattern;
    private String originPricePattern;
    private String filterString;
    private String saleCheckPattern;
    private String productCodePattern;
    private String urlFormat;
    private String pagePattern;
    private String soldOutMsg;
    private String pCodePattern;
    private PageConnection pageConnection;

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    public String getProductListPattern() {
        return productListPattern;
    }

    public void setProductListPattern(String productListPattern) {
        this.productListPattern = productListPattern;
    }

    public String getTitlePattern() {
        return titlePattern;
    }

    public void setTitlePattern(String titlePattern) {
        this.titlePattern = titlePattern;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getImgUrlPattern() {
        return imgUrlPattern;
    }

    public void setImgUrlPattern(String imgUrlPattern) {
        this.imgUrlPattern = imgUrlPattern;
    }

    public String getPricePattern() {
        return pricePattern;
    }

    public void setPricePattern(String pricePattern) {
        this.pricePattern = pricePattern;
    }

    public String getOriginPricePattern() {
        return originPricePattern;
    }

    public void setOriginPricePattern(String originPricePattern) {
        this.originPricePattern = originPricePattern;
    }

    public String getFilterString() {
        return filterString;
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public String getSaleCheckPattern() {
        return saleCheckPattern;
    }

    public void setSaleCheckPattern(String saleCheckPattern) {
        this.saleCheckPattern = saleCheckPattern;
    }

    public String getProductCodePattern() {
        return productCodePattern;
    }

    public void setProductCodePattern(String productCodePattern) {
        this.productCodePattern = productCodePattern;
    }

    public String getUrlFormat() {
        return urlFormat;
    }

    public void setUrlFormat(String urlFormat) {
        this.urlFormat = urlFormat;
    }

    public String getPagePattern() {
        return pagePattern;
    }

    public void setPagePattern(String pagePattern) {
        this.pagePattern = pagePattern;
    }

    public String getSoldOutMsg() {
        return soldOutMsg;
    }

    public void setSoldOutMsg(String soldOutMsg) {
        this.soldOutMsg = soldOutMsg;
    }

    public String getpCodePattern() {
        return pCodePattern;
    }

    public void setpCodePattern(String pCodePattern) {
        this.pCodePattern = pCodePattern;
    }

    public void setPageConnection(){
        if(shopType.toLowerCase().equals("cafe24") || shopType.toLowerCase().equals("makeshop"))
            pageConnection = new UrlStreamConnect();
        else
            pageConnection = new JSoupConnection();
    }

    public Object getPageData(String url) throws Exception {
        return pageConnection.getPageData(url);
    }
}
