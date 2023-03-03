package com.c.cpayid.feature.CetakStruk;

import java.util.ArrayList;

public class ResponStruk {
    String code,error,message;
    ArrayList<DataTransaksi> data;
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

    public ArrayList<DataTransaksi> getData() {
        return data;
    }

    public static class DataTransaksi{

        String id,customer_no,product_master_id,product_code,product_name,basic_price,markup_price,sales_markup,user_markup,sub_total,total_price,status,type,wallet_type,sn,user_id,sales_id,server_id,updated_at;

        public String getId() {
            return id;
        }

        public String getCustomer_no() {
            return customer_no;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getProduct_master_id() {
            return product_master_id;
            //rubah
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getBasic_price() {
            return basic_price;
        }

        public String getMarkup_price() {
            return markup_price;
        }

        public String getSales_markup() {
            return sales_markup;
        }

        public String getUser_markup() {
            return user_markup;
        }

        public String getSub_total() {
            return sub_total;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getStatus() {
            return status;
        }

        public String getType() {
            return type;
        }

        public String getWallet_type() {
            return wallet_type;
        }

        public String getSn() {
            return sn;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getSales_id() {
            return sales_id;
        }

        public String getServer_id() {
            return server_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
