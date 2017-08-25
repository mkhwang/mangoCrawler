package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.common.exception.SoldOutException;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.domain.godo.GodoGSONProduct;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import org.apache.log4j.Logger;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class GodoGsonProductMaker implements ProductMaker {
    private Logger logger = Logger.getLogger(GodoGsonProductMaker.class);

    @Override
    public Product getProduct(Object data, ParsePattern parsePattern) throws NullPointerException {
        Product resultProduct = null;
        GodoGSONProduct godoGSONProduct;

        try{
            godoGSONProduct = (GodoGSONProduct) data;

            if(isSoldOut(godoGSONProduct))
                throw new SoldOutException();

            resultProduct = new Product();
            resultProduct.setTitle(godoGSONProduct.getGoodsnm());
            resultProduct.setImage1(getImageData(godoGSONProduct, parsePattern.getImgUrlPattern()));
            resultProduct.setpCode(godoGSONProduct.getGoodsno());
            resultProduct.setPrice(godoGSONProduct.getGoods_price());

            if("0".equals(godoGSONProduct.getConsumer()) || godoGSONProduct.getConsumer() == null)
                resultProduct.setOrgPrice(resultProduct.getPrice());
            else
                resultProduct.setOrgPrice(godoGSONProduct.getConsumer());

        } catch (SoldOutException e) {

        } catch(Exception e){
            e.printStackTrace();
        }

        if(resultProduct == null)
            throw new NullPointerException();

        return resultProduct;
    }

    private String getImageData(GodoGSONProduct godoGSONProduct, String imageUrlPattern){
        String result = "";
        if("img_i".equals(imageUrlPattern))
            result = godoGSONProduct.getImg_i();
        else if("img_s".equals(imageUrlPattern))
            result = godoGSONProduct.getImg_s();
        else if("img_m".equals(imageUrlPattern))
            result = godoGSONProduct.getImg_m();
        else if("img_l".equals(imageUrlPattern))
            result = godoGSONProduct.getImg_l();

        if(result == "" ){
            if(!"".equals(godoGSONProduct.getImg_m()))
                result = godoGSONProduct.getImg_m();
            else
                result = godoGSONProduct.getImg_s();
        }

        if(result.contains("|")){
            result = result.split("\\|")[0].trim();
        }

        return  result;
    }

    private boolean isSoldOut(GodoGSONProduct godoGSONProduct){
        boolean cssResult = false;
        boolean optionResult = true;
        if(godoGSONProduct.getCss_selector() != null && !"".equals(godoGSONProduct.getCss_selector()))
            cssResult = true;

        String[] options = godoGSONProduct.getOption_value().split(",");
        for(String option : options){
            if(!option.contains("품절"))
                optionResult = false;
        }

        return cssResult || optionResult;
    }
}
