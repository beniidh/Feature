package com.c.cpayid.feature.TransferBank;

import java.util.ArrayList;

public class ModelNamaBank {

    String code,error,message;
    mNama data;
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

    public mNama getData() {
        return data;
    }
    public class mNama{
        ArrayList<Product> product;

        public ArrayList<Product> getProduct() {
            return product;
        }
        public class Product{
            //rubah
            String id,buyer_sku_code,product_master_name;
            boolean buyer_product_gangguan;

            public String getId() {
                return id;
            }
            public String getCode() {
                return buyer_sku_code;
            }
            public String getName() {
                return product_master_name;
            }
            public boolean isGangguan() {
                return buyer_product_gangguan;
            }

        }
    }
}
