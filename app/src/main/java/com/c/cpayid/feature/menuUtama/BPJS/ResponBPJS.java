package com.c.cpayid.feature.menuUtama.BPJS;

import java.util.ArrayList;

public class ResponBPJS {

    String code, message, error;
    String rubah;
    //copas semua

    mData data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public mData getData() {
        return data;
    }

    public class mData {
        String id;

        public String getId() {
            return id;
        }

        ArrayList<Product> product;

        public ArrayList<Product> getProduct() {
            return product;
        }

        public class Product {
            String id, product_master_name, description, product_category_id, buyer_sku_code;

            public String getId() {
                return id;
            }

            public String getCode() {
                return buyer_sku_code;
            }

            public String getName() {
                return product_master_name;
            }

            public String getDescription() {
                return description;
            }

            public String getProduct_category_id() {
                return product_category_id;
            }

        }
    }
}
