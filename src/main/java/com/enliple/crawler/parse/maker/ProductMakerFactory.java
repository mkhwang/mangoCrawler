package com.enliple.crawler.parse.maker;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.product.impl.Cafe24ProductMaker;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ProductMakerFactory {
    public static ProductMaker getProductMaker(ParsePattern parsePattern){
        ProductMaker maker = null;

        if("cafe24".equals(parsePattern.getShopType())){
            maker = new Cafe24ProductMaker();
        }

        return maker;
    }
}
