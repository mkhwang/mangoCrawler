package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.productList.impl.Cafe24ProductListMaker;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class Cafe24ProductMaker implements ProductMaker {
    private Logger logger = Logger.getLogger(Cafe24ProductListMaker.class);

    @Override
    public Product getProduct(Object data, ParsePattern parsePattern) throws NullPointerException {
        Product resultProduct = null;
        JSONObject jsonProduct;

        try {
            jsonProduct = (JSONObject) data;
            if (jsonProduct.get("soldout_icon").toString().contains("품절")
                    || jsonProduct.get("soldout_icon").toString().toLowerCase().contains("update")
                    || jsonProduct.get("product_price").toString().toLowerCase().contains("sold")
                    || "".equals(jsonProduct.get("product_price").toString().replaceAll("[^0-9]", ""))
                    ) {
                throw new NullPointerException();
            }
            resultProduct = new Product();
            resultProduct.setTitle(jsonProduct.get("product_name").toString());
            resultProduct.setpCode(jsonProduct.get("product_no").toString());
            resultProduct.setImage1(jsonProduct.get(parsePattern.getImgUrlPattern()).toString());
            resultProduct.setUrl("/product/detail.html?product_no=" + resultProduct.getpCode());

            if ("product_sale_display".equals(parsePattern.getSaleCheckPattern())
                    &&
                    ("".equals(parsePattern.getSaleCheckPattern())
                            && "".equals(parsePattern.getPricePattern())
                            && "".equals(parsePattern.getOriginPricePattern()))) {
                if (jsonProduct.get("product_sale_display").toString().equals("true")) {
                    resultProduct.setOrgPrice(jsonProduct.get("product_price").toString());
                    resultProduct.setPrice(jsonProduct.get("origin_prd_price_sale").toString());
                } else {
                    resultProduct.setPrice(jsonProduct.get("product_price").toString());
                    resultProduct.setOrgPrice(resultProduct.getPrice());
                }
            } else if ("product_custom".equals(parsePattern.getSaleCheckPattern())) {
                resultProduct.setPrice(jsonProduct.get("product_price").toString());
                if (!"".equals(jsonProduct.get("product_custom").toString()))
                    resultProduct.setOrgPrice(jsonProduct.get("product_custom").toString());
            } else if ("origin_prd_price_sale".equals(parsePattern.getSaleCheckPattern())) {
                resultProduct.setOrgPrice(jsonProduct.get("product_price").toString());
                if (jsonProduct.containsKey(parsePattern.getSaleCheckPattern()))
                    resultProduct.setPrice(jsonProduct.get(parsePattern.getSaleCheckPattern()).toString());
                else
                    resultProduct.setPrice(resultProduct.getOrgPrice());
            } else if ("disp_product_custom".equals(parsePattern.getSaleCheckPattern())) {
                int tempPrice = Integer.parseInt(jsonProduct.get("disp_product_custom").toString().replaceAll("[^0-9]", ""));
                resultProduct.setPrice(jsonProduct.get("product_price").toString());
                if (tempPrice > 0)
                    resultProduct.setOrgPrice(jsonProduct.get("product_cumstom").toString());
            } else if ("summary_desc".equals(parsePattern.getSaleCheckPattern())) {
                resultProduct.setPrice(jsonProduct.get("product_price").toString());
                if (!"".equals(jsonProduct.get(parsePattern.getSaleCheckPattern())))
                    resultProduct.setOrgPrice(jsonProduct.get(parsePattern.getSaleCheckPattern()).toString());
            } else if ("c_dc_price_apply".equals(parsePattern.getSaleCheckPattern())) {
                resultProduct.setOrgPrice(jsonProduct.get("product_price").toString());
                if (jsonProduct.containsKey(parsePattern.getSaleCheckPattern()))
                    resultProduct.setPrice(jsonProduct.get(parsePattern.getSaleCheckPattern()).toString());
                else
                    resultProduct.setPrice(resultProduct.getOrgPrice());
            } else {
                resultProduct.setPrice(jsonProduct.get("product_price").toString());
                resultProduct.setOrgPrice(resultProduct.getPrice());
            }

            if (resultProduct.getOrgPrice() == 0 || "".equals(String.valueOf(resultProduct.getOrgPrice())))
                resultProduct.setOrgPrice(resultProduct.getpCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultProduct;
    }
}
