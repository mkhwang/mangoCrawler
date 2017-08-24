package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.common.exception.SoldOutException;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.domain.cafe24.Cafe24GSONProduct;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import org.apache.log4j.Logger;

/**
 * Created by MinKi Hwang on 2017-08-23.
 */
public class Cafe24GsonProductMaker implements ProductMaker {
    private Logger logger = Logger.getLogger(Cafe24GsonProductMaker.class);

    @Override
    public Product getProduct(Object data, ParsePattern parsePattern) throws NullPointerException {
        Product resultProduct = null;
        Cafe24GSONProduct cafe24GSONProduct;

        try {
            cafe24GSONProduct = (Cafe24GSONProduct) data;

            String soldOutMsg = cafe24GSONProduct.getSoldout_icon();
            if (soldOutMsg != null && !"".equals(soldOutMsg)){
                if( soldOutMsg.contains("품절")
                        || soldOutMsg.toLowerCase().contains("update")
                        || soldOutMsg.toLowerCase().contains("sold")
                        || "".equals(soldOutMsg.replaceAll("[^0-9]", ""))
                        ){
                    throw new SoldOutException();
                }
            }
            resultProduct = new Product();
            
            resultProduct.setTitle(cafe24GSONProduct.getProduct_name());
            resultProduct.setpCode(cafe24GSONProduct.getProduct_no());
            resultProduct.setUrl("/product/detail.html?product_no=" + resultProduct.getpCode());

            String imgUrl ="";
            if(parsePattern.getImgUrlPattern().contains("big")){
                imgUrl = cafe24GSONProduct.getImage_big();
            } else if(parsePattern.getImgUrlPattern().contains("medium")){
                imgUrl = cafe24GSONProduct.getImage_medium();
            } else if(parsePattern.getImgUrlPattern().contains("small")){
                imgUrl = cafe24GSONProduct.getImage_small();
            } else if(parsePattern.getImgUrlPattern().contains("tiny")){
                imgUrl = cafe24GSONProduct.getImage_tiny();
            } else {
                if(!cafe24GSONProduct.getImage_big().contains("cafe24")){
                    imgUrl = cafe24GSONProduct.getImage_big();
                }else{
                    imgUrl = cafe24GSONProduct.getImage_medium();
                }
            }
            resultProduct.setImage1("http:"+imgUrl);

            setPrice(resultProduct, parsePattern, cafe24GSONProduct);

            if (resultProduct.getOrgPrice() == 0 || "".equals(String.valueOf(resultProduct.getOrgPrice())))
                resultProduct.setOrgPrice(resultProduct.getPrice());

        } catch (SoldOutException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(resultProduct == null)
            throw new NullPointerException();

        return resultProduct;
    }

    private void setPrice(Product resultProduct, ParsePattern parsePattern, Cafe24GSONProduct cafe24GSONProduct){
        if ("product_sale_display".equals(parsePattern.getSaleCheckPattern())
                ||
                ("".equals(parsePattern.getSaleCheckPattern())
                        && "".equals(parsePattern.getPricePattern())
                        && "".equals(parsePattern.getOriginPricePattern()))) {
            if (cafe24GSONProduct.isProduct_sale_display()) {
                resultProduct.setOrgPrice(cafe24GSONProduct.getProduct_price());
                resultProduct.setPrice(cafe24GSONProduct.getOrigin_prd_price_sale());
            } else {
                resultProduct.setPrice(cafe24GSONProduct.getProduct_price());
                resultProduct.setOrgPrice(resultProduct.getPrice());
            }

        } else if ("product_custom".equals(parsePattern.getSaleCheckPattern())) {
            resultProduct.setPrice(cafe24GSONProduct.getProduct_price());
            if (!"".equals(cafe24GSONProduct.getProduct_custom()))
                resultProduct.setOrgPrice(cafe24GSONProduct.getProduct_custom());

        } else if ("origin_prd_price_sale".equals(parsePattern.getSaleCheckPattern())) {
            resultProduct.setOrgPrice(cafe24GSONProduct.getProduct_price());
            if (!"".equals(cafe24GSONProduct.getOrigin_prd_price_sale()))
                resultProduct.setPrice(cafe24GSONProduct.getOrigin_prd_price_sale());
            else
                resultProduct.setPrice(resultProduct.getOrgPrice());

        } else if ("disp_product_custom".equals(parsePattern.getSaleCheckPattern())) {

            int tempPrice = Integer.parseInt(cafe24GSONProduct.getDisp_product_custom().replaceAll("[^0-9]", ""));
            resultProduct.setPrice(cafe24GSONProduct.getProduct_price());
            if (tempPrice > 0)
                resultProduct.setOrgPrice(cafe24GSONProduct.getProduct_custom());

        } else if ("summary_desc".equals(parsePattern.getSaleCheckPattern())) {
            resultProduct.setPrice(cafe24GSONProduct.getProduct_price());
            if (!"".equals(cafe24GSONProduct.getSummary_desc()))
                resultProduct.setOrgPrice(cafe24GSONProduct.getSummary_desc());

        } else if ("c_dc_price_apply".equals(parsePattern.getSaleCheckPattern())) {
            resultProduct.setOrgPrice(cafe24GSONProduct.getProduct_price());
            if (!"".equals(cafe24GSONProduct.getC_dc_price_apply()))
                resultProduct.setPrice(cafe24GSONProduct.getC_dc_price_apply());
            else
                resultProduct.setPrice(resultProduct.getOrgPrice());
        } else {
            resultProduct.setPrice(cafe24GSONProduct.getProduct_price());
        }
    }
}
