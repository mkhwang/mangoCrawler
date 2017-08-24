package com.enliple.crawler.parse.service.impl;

import com.enliple.crawler.common.DateUtil;
import com.enliple.crawler.common.SessionFactory;
import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.image.MangoImageService;
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
import com.enliple.crawler.parse.maker.UrlMakerFactory;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import com.enliple.crawler.parse.maker.url.UrlMaker;
import com.enliple.crawler.parse.maker.url.impl.Cafe24UrlMaker;
import com.enliple.crawler.parse.service.ParseService;
import com.enliple.crawler.task.domain.ParseTask;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ParseServiceImpl implements ParseService{
    private Logger logger = Logger.getLogger(ParseServiceImpl.class);
    private ParseDao parseDao;
    private List<Product> newProductList;
    private List<Product> updateProductList;
    private MangoImageService imageService;

    public ParseServiceImpl(){
        parseDao = new ParseDaoImpl();
        imageService = new MangoImageService();
        newProductList = new ArrayList<>();
        updateProductList = new ArrayList<>();
    }

    @Override
    public void parseShop(ParseTask parseTask) {
        List<ParsingInfo> parsingInfoList = this.getParsingInfoList(parseTask.getScCode());
        ParsePattern parsePattern = this.getParsePattern(parseTask.getScCode());
        System.out.println(parseTask.getScCode() + " / parsingInfoList size " + parsingInfoList.size());
        logger.debug(parseTask.getScCode() + " / parsingInfoList size " + parsingInfoList.size());
        for(ParsingInfo parsingInfo : parsingInfoList){
            try {
                parseCategory(parsingInfo, parsePattern);
            } catch (NullPointerException e) {
                break;
            }
        }
        this.updateNullImageWidth(parseTask);
        this.updateRenewalProductAndParsingDate(parseTask);
    }

    private void parseCategory(ParsingInfo parsingInfo, ParsePattern parsePattern) throws NullPointerException{
        UrlMaker urlMaker = UrlMakerFactory.getUrlMaker(parsingInfo);
        List<String> urlList = PageMaker.getPages(urlMaker.getUrl(parsingInfo), parsePattern.getPagePattern());
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
        List<Object> productList;
        productList = productListMaker.getProductList(pageData, parsePattern.getProductListPattern());

        System.out.println(DateUtil.getCurrentTime("MM-dd HH:mm:ss:SSS")+" : "+  parsingInfo.getShopName() + " / " + url + " [ " + productList.size() + " ]");
        logger.debug(parsingInfo.getShopName() + " / " + url + " [ " + productList.size() + " ]");
        for(Object product : productList){
            try {
                this.parseProduct(parsingInfo, parsePattern, product);
            } catch (NullPointerException e) {
                continue;
            }
        }
        this.insertOrUpdateProductList();
        this.clearProductList();
    }

    private void parseProduct(ParsingInfo parsingInfo, ParsePattern parsePattern, Object product) throws NullPointerException{
        ProductMaker productMaker = ProductMakerFactory.getProductMaker(parsePattern);
        Product parsedProduct = productMaker.getProduct(product, parsePattern);

        if(parsePattern.getFilterString() != null && !"".equals(parsePattern.getFilterString()))
            this.executeTitleFilter(parsedProduct, parsePattern);

        if(parsedProduct.getPrice() <= 0 || String.valueOf(parsedProduct.getPrice()) == ""){
            //logger.error(product.toString()+"\n"+parsedProduct.toString());
            //System.err.println("priceNull : "+product.toString()+"\n"+parsedProduct.toString());
            throw new NullPointerException();
        }

        if("".equals(parsedProduct.getImage1()) || parsedProduct.getImage1() == null){
            System.err.println("imgaeNull : "+product.toString()+"\n"+parsedProduct.toString());
            throw new NullPointerException();
        }

        this.refineProduct(parsingInfo, parsePattern, parsedProduct);
        //System.out.println(parsedProduct.toString());
        logger.debug(parsedProduct.toString());
        this.separateNewOrUpdateProduct(parsedProduct);
    }

    private void refineProduct(ParsingInfo parsingInfo, ParsePattern parsePattern, Product product) throws NullPointerException{
        if(parsePattern.getUrlFormat() != null && !"".equals(parsePattern.getUrlFormat())){
            if(parsePattern.getUrlFormat().contains("{pCode}"))
                product.setUrl(parsePattern.getUrlFormat().replace("{pCode}", product.getpCode()));

            if(parsePattern.getUrlFormat().contains("{returnUrl}")){
                try {
                    product.setUrl(parsePattern.getUrlFormat().replace("{returnUrl}", URLEncoder.encode(product.getUrl(), "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        if(parsePattern.getImgUrlFormat() != null && !"".equals(parsePattern.getImgUrlFormat())){
            if(parsePattern.getImgUrlFormat().contains("{imgUrl}"))
                product.setImage1(parsePattern.getImgUrlFormat().replace("{imgUrl}", product.getImage1()));
        }

        /*
        if(!"".equals(parsingInfo.getSiteEtc()))
            product.setUrl(product.getUrl()+"&"+parsingInfo.getSiteEtc());
         */
        product.calculateSetDcRate();
        product.setScCode(parsingInfo.getScCode());
        product.setCategory(parsingInfo.getCategoryMatchCode());
        product.setSiteName(parsingInfo.getShopName());
        imageService.setImageInformation(product, parsingInfo);
    }

    private void executeTitleFilter(Product product, ParsePattern parsePattern) throws NullPointerException{
        String[] filterStrings = parsePattern.getFilterString().split("\\^");
        for(String filterString : filterStrings){
            if(product.getTitle().contains(filterString))
                throw new NullPointerException();
        }
    }

    private void separateNewOrUpdateProduct(Product product){
        Product parsedProduct;
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
            this.rollBack(session);
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
            this.rollBack(session);
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
        System.out.println(parseTask.getScCode() + "  updateRenewalProductAndParsingDate");
        logger.debug(parseTask.getScCode() + "  updateRenewalProductAndParsingDate");
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            Map<String, String> renewalProductMap = new HashMap<>();
            renewalProductMap.put("scCode", parseTask.getScCode());
            renewalProductMap.put("soldOutPeriod",  LoadProperties.getSoldOutPeriod());
            parseDao.updateNoRenewalProduct(renewalProductMap, session);
            parseDao.updateShopParseDate(parseTask.getScCode(), session);
            session.commit();
        } catch (Exception e) {
            this.rollBack(session);
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    private void updateNullImageWidth(ParseTask parseTask){
        System.out.println(parseTask.getScCode() + "  updateNullImageWidth");
        logger.debug(parseTask.getScCode() + "  updateNullImageWidth");
        SqlSession session = null;
        try {
            session = SessionFactory.getSession();
            parseDao.updateNullImageWidth(parseTask.getScCode(), session);
            parseDao.updateNullImageHeight(parseTask.getScCode(), session);
            session.commit();
        } catch (Exception e) {
            this.rollBack(session);
            e.printStackTrace();
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

    private void rollBack(SqlSession session){
        try{
            session.rollback();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
}
