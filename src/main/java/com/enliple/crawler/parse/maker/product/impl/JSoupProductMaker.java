package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.common.util.JSoupUtil;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import org.jsoup.nodes.Element;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class JSoupProductMaker implements ProductMaker {
    @Override
    public Product getProduct(Object data, ParsePattern parsePattern) throws NullPointerException {
        Product resultProduct = null;
        try {
            if(data instanceof Element){
                resultProduct = new Product();
                resultProduct.setTitle(JSoupUtil.getElements(data, parsePattern.getTitlePattern()).html());
                resultProduct.setUrl(JSoupUtil.getAttribute(data, parsePattern.getUrlPattern()));
                resultProduct.setImage1(JSoupUtil.getAttribute(data, parsePattern.getImgUrlPattern()));
                resultProduct.setPrice(JSoupUtil.getElements(data, parsePattern.getPricePattern()).text());
                resultProduct.setOrgPrice(JSoupUtil.getElements(data, parsePattern.getOriginPricePattern()).text());
                resultProduct.calculateSetDcRate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultProduct;
    }
}