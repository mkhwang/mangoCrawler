package com.enliple.crawler.parse.service.impl;

import com.enliple.crawler.common.SessionFactory;
import com.enliple.crawler.parse.connect.PageConnection;
import com.enliple.crawler.parse.connect.PageConnectionFactory;
import com.enliple.crawler.parse.dao.ParseDao;
import com.enliple.crawler.parse.dao.impl.ParseDaoImpl;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.maker.PageMaker;
import com.enliple.crawler.parse.maker.ProductListMakerFactory;
import com.enliple.crawler.parse.maker.ProductMakerFactory;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import com.enliple.crawler.parse.service.ParseService;
import com.enliple.crawler.task.domain.ParseTask;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ParseServiceImpl implements ParseService{
    private Logger logger = Logger.getLogger(ParseServiceImpl.class);
    private ParseDao parseDao;
    private List<Product> newProductList;
    private List<Product> updateProductList;

    public ParseServiceImpl(){
        parseDao = new ParseDaoImpl();
        newProductList = new ArrayList<>();
        updateProductList = new ArrayList<>();
    }

    @Override
    public void parseShop(ParseTask parseTask) {
        List<ParsingInfo> parsingInfoList = this.getParsingInfoList(parseTask.getScCode());
        ParsePattern parsePattern = this.getParsePattern(parseTask.getScCode());

        for(ParsingInfo parsingInfo : parsingInfoList){
            try {
                parseCategory(parsingInfo, parsePattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.updateRenewalProductAndParsingDate(parseTask);
    }

    private void parseCategory(ParsingInfo parsingInfo, ParsePattern parsePattern) throws Exception{
        List<String> urlList = PageMaker.getPages(parsingInfo.getUrl(), parsePattern.getPagePattern());
        for(String url : urlList){
            try {
                parsePage(parsingInfo, parsePattern, url);
            } catch(NullPointerException e){
                logger.debug(e.getMessage());
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parsePage(ParsingInfo parsingInfo, ParsePattern parsePattern, String url) throws Exception{
        PageConnection pageConnection = PageConnectionFactory.getPageConnection(parsePattern);
        ProductListMaker productListMaker = ProductListMakerFactory.getProductListMaker(parsePattern);
        Object pageData = pageConnection.getPageData(url);
        List<Object> productList = null;
        productList = productListMaker.getProductList(pageData, parsePattern.getProductListPattern());

        for(Object product : productList){
            this.parseProduct(parsingInfo, parsePattern, product);
        }
        this.insertOrUpdateProductList();
        this.clearProductList();
    }

    private void parseProduct(ParsingInfo parsingInfo, ParsePattern parsePattern, Object product){
        ProductMaker productMaker = ProductMakerFactory.getProductMaker(parsePattern);
        Product parsedProduct = productMaker.getProduct(product, parsePattern);
        this.refineProduct(parsingInfo, parsePattern, parsedProduct);
        this.separateNewOrUpdateProduct(parsedProduct);
    }

    private void refineProduct(ParsingInfo parsingInfo, ParsePattern parsePattern, Product product){
        if(!"".equals(parsingInfo.getSiteEtc()))
            product.setUrl(product.getUrl()+"&"+parsingInfo.getSiteEtc());

        if(!"".equals(parsePattern.getUrlFormat())){
            if(parsePattern.getUrlFormat().contains("{pCode}"))
                product.setUrl(parsePattern.getUrlFormat().replace("{pCode}", product.getpCode()));
            else if(parsePattern.getUrlFormat().contains("{returnUrl}")){
                try {
                    product.setUrl(parsePattern.getUrlFormat().replace("{returnUrl}", URLEncoder.encode(product.getUrl(), "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        product.setScCode(parsingInfo.getScCode());
        product.setCategory(parsingInfo.getCategoryMatchCode());
        product.setSiteName(parsingInfo.getShopName());

        if("true".equals(parsingInfo.getImageTransform())){
            try {
                imageTransform(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            /**
             * Todo getImageFromUrl
             * width height
             */
        }
    }

    private void imageTransform(Product product) throws Exception{
        /**
         * Todo 이미지 변환
         *
         */
    }

    private void separateNewOrUpdateProduct(Product product){
        Product parsedProduct = null;
        SqlSession session = null;
        try{
            session = SessionFactory.getSession();
            parsedProduct = parseDao.getProduct(product, session);
            if( parsedProduct == null){
                newProductList.add(product);
            } else {
                if(parsedProduct.getCategory().contains(product.getCategory()))
                    product.setCategory(parsedProduct.getCategory());
                else
                    product.setCategory(parsedProduct.getCategory()+","+product.getCategory());

                product.setGsCd(parsedProduct.getGsCd());
                updateProductList.add(product);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            this.closeSession(session);
        }
    }

    private void insertOrUpdateProductList(){
        if(newProductList.size() > 0)
            insertProductList();
        if(updateProductList.size() > 0)
            updateProductList();
    }

    private void insertProductList(){
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            parseDao.insertProductList(newProductList, session);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            this.closeSession(session);
        }
    }

    private void updateProductList(){
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            parseDao.updateProductList(updateProductList, session);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            this.closeSession(session);
        }
    }

    private void clearProductList(){
        updateProductList.clear();
        newProductList.clear();
    }

    private List<ParsingInfo> getParsingInfoList(String scCode){
        SqlSession session = null;
        List<ParsingInfo> parsingInfoList = null;
        try {
            session = SessionFactory.getSession();
            parsingInfoList = parseDao.getParsingInfoList(scCode, session);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return parsingInfoList;
    }

    private ParsePattern getParsePattern(String scCode){
        SqlSession session = null;
        ParsePattern parsePattern = null;
        try {
            session = SessionFactory.getSession();
            parsePattern = parseDao.getParsePattern(scCode, session);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return parsePattern;
    }

    private void updateRenewalProductAndParsingDate(ParseTask parseTask){
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            parseDao.updateNoRenewalProduct(parseTask.getScCode(), session);
            parseDao.updateParsingDate(parseTask.getScCode(), session);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            closeSession(session);
        }
    }

    private void closeSession(SqlSession session){
        try{
            if(session != null)
                session.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
