package com.c.cpayid.feature.menuUtama.TV;

import java.util.ArrayList;

public class ResponProdukTV {
    String code,error,message;

    ArrayList<VoucherData> data;

    public ArrayList<VoucherData> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public class VoucherData{

        String id,code,name,brand,basic_price,description,product_subcategory_id,updated_at;
        boolean gangguan;

        public boolean isGangguan() {
            return gangguan;
        }

        public String getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }

        public String getBasic_price() {
            return basic_price;
        }

        public String getDescription() {
            return description;
        }

        public String getProduct_subcategory_id() {
            return product_subcategory_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
