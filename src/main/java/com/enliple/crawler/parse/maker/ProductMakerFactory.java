package com.enliple.crawler.parse.maker;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.product.impl.Cafe24ProductMaker;
import com.enliple.crawler.parse.maker.product.impl.JSoupProductMaker;
import com.enliple.crawler.parse.maker.product.impl.MakeShopProductMaker;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ProductMakerFactory {
    public static ProductMaker getProductMaker(ParsePattern parsePattern){
        ProductMaker maker;

        if("cafe24".equals(parsePattern.getShopType()))
            maker = new Cafe24ProductMaker();
        else if("makeshop".equals(parsePattern.getShopType()))
            maker = new MakeShopProductMaker();
        else
            maker = new JSoupProductMaker();

        return maker;
    }
}
