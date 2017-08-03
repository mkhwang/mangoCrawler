package com.enliple.crawler.parse.dao.impl;

import com.enliple.crawler.parse.dao.ParseDao;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.domain.Product;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ParseDaoImpl implements ParseDao {
    @Override
    public ParsePattern getParsePattern(String scCode, SqlSession session) {
        return null;
    }

    @Override
    public void updateShopParseDate(String scCode, SqlSession session) {

    }

    @Override
    public List<ParsingInfo> getParsingInfoList(String scCode, SqlSession session) {
        return null;
    }

    @Override
    public void insertProduct(Product product, SqlSession session) {

    }

    @Override
    public void updateProduct(Product product, SqlSession session) {

    }

    @Override
    public void insertProductList(List<Product> products, SqlSession session) {

    }

    @Override
    public void updateProductList(List<Product> products, SqlSession session) {

    }

    @Override
    public Product getProduct(Product product, SqlSession session) {
        return null;
    }
}
