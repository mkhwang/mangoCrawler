package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;


/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class JSONProductMaker implements ProductMaker {
    private Logger logger = Logger.getLogger(JSONProductMaker.class);

    @Override
    public Product getProduct(Object data, ParsePattern parsePattern) throws NullPointerException {
        Product resultProduct;
        JSONObject jsonProduct;
        try {
            jsonProduct = (JSONObject) data;
            resultProduct = new Product();
            resultProduct.setTitle(jsonProduct.get(parsePattern.getTitlePattern()).toString());
            resultProduct.setUrl(jsonProduct.get(parsePattern.getUrlPattern()).toString());
            resultProduct.setImage1(jsonProduct.get(parsePattern.getImgUrlPattern()).toString());
            resultProduct.setPrice(jsonProduct.get(parsePattern.getPricePattern()).toString());

            if(parsePattern.getSaleCheckPattern() != null
                    && !"".equals(parsePattern.getSaleCheckPattern())
                    && jsonProduct.containsKey(parsePattern.getSaleCheckPattern()))
                resultProduct.setOrgPrice(jsonProduct.get(parsePattern.getOriginPricePattern()).toString());

            resultProduct.setpCode(jsonProduct.get(parsePattern.getProductCodePattern()).toString());
        } catch (Exception e) {
            e.printStackTrace();
            resultProduct = null;
        }

        if(resultProduct == null)
            throw new NullPointerException();

        return resultProduct;
    }
}
