package com.enliple.crawler.parse.maker;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import com.enliple.crawler.parse.maker.productList.impl.*;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ProductListMakerFactory {
    public static ProductListMaker getProductListMaker(ParsePattern parsePattern){
        ProductListMaker maker;
        if("cafe24".equals(parsePattern.getShopType()))
            maker = new Cafe24ProductListMaker();
        else if("makeShop".equals(parsePattern.getShopType()))
            maker = new MakeShopProductListMaker();
        else if(parsePattern.getProductListPattern().contains("&"))
            maker = new ElementsFromJSONProductListMaker();
        else if("json".equals(parsePattern.getShopType()))
            maker = new JSONProductListMaker();
        else
            maker = new CommonProductListMaker();
        return maker;
    }
}
