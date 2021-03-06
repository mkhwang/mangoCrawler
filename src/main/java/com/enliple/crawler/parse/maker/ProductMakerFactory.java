package com.enliple.crawler.parse.maker;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.product.impl.Cafe24GsonProductMaker;
import com.enliple.crawler.parse.maker.product.impl.Cafe24ProductMaker;
import com.enliple.crawler.parse.maker.product.impl.CommonShopProductMaker;
import com.enliple.crawler.parse.maker.product.impl.GodoGsonProductMaker;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ProductMakerFactory {
    public static ProductMaker getProductMaker(ParsePattern parsePattern){
        ProductMaker maker;

        if("cafe24".equals(parsePattern.getShopType()))
            maker = new Cafe24GsonProductMaker();
        else if("godo".equals(parsePattern.getShopType()))
            maker = new GodoGsonProductMaker();
        else
            maker = new CommonShopProductMaker();

        return maker;
    }
}
