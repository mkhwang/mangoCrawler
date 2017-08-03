package com.enliple.crawler.parse.domain;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class Product {
    String gsCd;
    String pCode ;
    String scCode;
    String category;
    String siteName;
    String title;
    String image1;
    String image2;
    String image3;
    String image4;
    String image5;
    String image6;
    long orgPrice = 0;
    long price = 0;
    int dcRate = 0;
    String url;
    String display = "";

    public String getGsCd() {
        return gsCd;
    }

    public void setGsCd(String gsCd) {
        this.gsCd = gsCd;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public long getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(long orgPrice) {
        this.orgPrice = orgPrice;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getDcRate() {
        return dcRate;
    }

    public void setDcRate(int dcRate) {
        this.dcRate = dcRate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setPrice(String price){
        try {
            this.price = Long.parseLong(price);
        } catch (NumberFormatException e) {
            this.price = 0L;
        }
    }

    public void setOrgPrice(String orgPrice){
        try {
            this.orgPrice = Long.parseLong(orgPrice);
        } catch (NumberFormatException e) {
            this.orgPrice = 0L;
        }
    }
}
