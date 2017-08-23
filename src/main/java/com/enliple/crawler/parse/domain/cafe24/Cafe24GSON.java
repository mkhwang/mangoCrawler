package com.enliple.crawler.parse.domain.cafe24;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-23.
 */
public class Cafe24GSON {
    RtnData rtn_data;

    public List<Cafe24GSONProduct> getData() throws NullPointerException{
        if( rtn_data.getCafe24ProductList() == null ||
                rtn_data.getCafe24ProductList().size() <= 0)
            throw new NullPointerException();

        return rtn_data.getCafe24ProductList();
    }

    class RtnData{
        ArrayList<Cafe24GSONProduct> data;

        public ArrayList<Cafe24GSONProduct> getCafe24ProductList(){
            return data;
        }

        public void setData(ArrayList<Cafe24GSONProduct> data){
            this.data = data;
        }
    }
}
