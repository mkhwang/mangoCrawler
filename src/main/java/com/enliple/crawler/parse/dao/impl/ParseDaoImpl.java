package com.enliple.crawler.parse.dao.impl;

import com.enliple.crawler.parse.dao.ParseDao;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.domain.Product;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ParseDaoImpl implements ParseDao {
    @Override
    public ParsePattern getParsePattern(String scCode, SqlSession session) {
        return session.selectOne("parse.getParsePattern", scCode);
    }

    @Override
    public void updateShopParseDate(String scCode, SqlSession session) {
        session.update("parse.updateShopParseDate", scCode);
    }

    @Override
    public List<ParsingInfo> getParsingInfoList(String scCode, SqlSession session) {
        return session.selectList("parse.getParsingInfoList", scCode);
    }

    @Override
    public void insertProduct(Product product, SqlSession session) {
        session.insert("parse.insertProduct", product);
    }

    @Override
    public void updateProduct(Product product, SqlSession session) {
        session.update("parse.updateProduct", product);
    }

    @Override
    public void insertProductList(List<Product> products, SqlSession session) {
        session.insert("parse.insertProductList", products);
    }

    @Override
    public void updateProductList(List<Product> products, SqlSession session) {
        session.update("parse.updateProductList", products);
    }

    @Override
    public Product getProduct(Product product, SqlSession session) {
        return session.selectOne("parse.getProduct", product);
    }

    @Override
    public void updateNoRenewalProduct(Map renewalProductMap, SqlSession session) {
        session.update("parse.updateNoRenewalProduct", renewalProductMap);
    }
}
