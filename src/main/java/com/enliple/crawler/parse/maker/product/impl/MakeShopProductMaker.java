package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.common.util.JSoupUtil;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.maker.PcodeMaker;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import org.jsoup.nodes.Element;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class MakeShopProductMaker implements ProductMaker{
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

                if(parsePattern.getSaleCheckPattern() != null && !"".equals(parsePattern.getSaleCheckPattern())){
                    try {
                        String tempOriginalPrice = JSoupUtil.getElements(data, parsePattern.getSaleCheckPattern()).html();
                        if(!"".equals(tempOriginalPrice))
                            resultProduct.setOrgPrice(JSoupUtil.getElements(data, parsePattern.getOriginPricePattern()).text());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                resultProduct.setpCode(PcodeMaker.getPcodeFromUrl(resultProduct.getUrl(), parsePattern.getProductCodePattern()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultProduct = null;
        }

        if(resultProduct == null)
            throw new NullPointerException();

        return resultProduct;
    }
}
