package com.enliple.crawler.parse.domain;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class Product {
    private String gsCd;
    private String pCode ;
    private String scCode;
    private String category;
    private String siteName;
    private String title;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private long orgPrice = 0;
    private long price = 0;
    private int dcRate = 0;
    private String url;
    private String display = "";
    private int width;
    private int height;

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

    public void calculateSetDcRate(){

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("title : ").append(this.getTitle()).append("\n");
        builder.append("pcode : ").append(this.getpCode()).append("\t / ");
        builder.append("orgprice : ").append(this.getOrgPrice()).append("\t / ");
        builder.append("price : ").append(this.getPrice()).append("\n");
        builder.append("url : ").append(this.getUrl()).append("\n");
        builder.append("imgurl : ").append(this.getImage1()).append("\t / ");
        builder.append("width : ").append(this.getWidth()).append(" / ");
        builder.append("height : ").append(this.getHeight()).append(" / ");
        builder.append("display : ").append(this.getDisplay()).append("\n");
        builder.append("==============================================================")
                .append("==============================================================");
        return builder.toString();
    }
}
