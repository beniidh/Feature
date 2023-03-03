package com.c.cpayid.feature.CetakStruk;

public class ResponCodeSubPS {
    String code,error;
    Data data;
    String rubah;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public class Data{

        Product_subcategory product_group;
        //rubah
        public Product_subcategory getProduct_subcategory() {
            return product_group;
        }

        public class Product_subcategory{
            String subcategory_id;
            //rubah
            public String getId() {
                return subcategory_id;
            }
        }
    }
}
