package com.c.cpayid.feature.TransferBank;

public class ResponTransfer {
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

    public class mData {

        String code, customer_no, ref_id, type, date_request, total_price, message, created_at;

        public String getCode() {
            return code;
        }

        public String getCustomer_no() {
            return customer_no;
        }

        public String getRef_id() {
            return ref_id;
        }

        public String getType() {
            return type;
        }

        public String getDate_request() {
            return date_request;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getMessage() {
            return message;
        }

        public String getCreated_at() {
            return created_at;
        }
    }
}
