package com.c.cpayid.feature.DaftarHarga;

import java.util.ArrayList;

public class ResponProdukList {
    String code, error, message;
    mData data;
    String rubah;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public mData getData() {
        return data;
    }

    public class mData {
        ArrayList<Product> product;
        //rubah

        public ArrayList<Product> getDataProduct() {
            return product;
        }

        public  class Product {

            String product_master_name, basic_price, total_price, buyer_sku_code;
            //rubah

            public String getName() {
                return product_master_name;
            }

            public String getCode() {
                return buyer_sku_code;
            }

            public String getTotal_price() {
                return total_price;
            }

            public String getBasic_price() {
                return basic_price;
            }

        }

    }
}

