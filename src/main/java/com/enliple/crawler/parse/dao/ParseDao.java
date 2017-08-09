package com.enliple.crawler.parse.dao;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.domain.Product;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public interface ParseDao {
    void insertProduct(Product product, SqlSession session);
    void updateProduct(Product product, SqlSession session);
    void insertProductList(List<Product> products, SqlSession session);
    void updateProductList(List<Product> products, SqlSession session);
    ParsePattern getParsePattern(String scCode, SqlSession session);
    void updateShopParseDate(String scCode, SqlSession session);
    List<ParsingInfo> getParsingInfoList(String scCode, SqlSession session);
    Product getProduct(Product product, SqlSession session);
    void updateNoRenewalProduct(Map renewalProductMap, SqlSession session);
    void updateNullImageWidth(String scCode, SqlSession session);
    void updateNullImageHeight(String scCode, SqlSession session);
}
