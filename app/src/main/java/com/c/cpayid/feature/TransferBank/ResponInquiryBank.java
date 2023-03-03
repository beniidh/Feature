package com.c.cpayid.feature.TransferBank;

public class ResponInquiryBank {
    String code, error, message;
    mData data;


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
    String rubah;


    public class mData {

        String buyer_sku_code, customer_no, customer_name, ref_id,status, basic_price, admin_fee, total_price, description;

        public String getBuyer_sku_code() {
            return buyer_sku_code;
        }

        public String getCustomer_no() {
            return customer_no;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public String getRef_id() {
            return ref_id;
        }

        public String getBasic_price() {
            return basic_price;
        }

        public String getAdmin_fee() {
            return admin_fee;
        }

        public String getSelling_price() {
            return total_price;
        }
        //rubah
    }
}
