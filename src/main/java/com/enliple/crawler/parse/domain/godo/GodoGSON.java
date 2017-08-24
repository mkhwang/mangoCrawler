package com.enliple.crawler.parse.domain.godo;

import java.util.ArrayList;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class GodoGSON {
    private ArrayList<GodoGSONProduct> goods_data;

    public ArrayList<GodoGSONProduct> getGoods_data() {
        return goods_data;
    }

    public void setGoods_data(ArrayList<GodoGSONProduct> goods_data) {
        this.goods_data = goods_data;
    }
}
