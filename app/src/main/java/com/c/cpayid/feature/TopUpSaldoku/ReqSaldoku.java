package com.c.cpayid.feature.TopUpSaldoku;

public class ReqSaldoku {


    String mac_address, ip_address, user_agent,error,payment_option_id;
    double  amount;
    String code,message;
    mData data;


    public ReqSaldoku(String payment_option_id,String mac_address, String ip_address, String user_agent, double amount) {

        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.payment_option_id = payment_option_id;
        this.user_agent = user_agent;
        this.amount = amount;
    }


    public mData getData() {
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

    public class mData{

        String id,type,amount,va_number,va_admin,va_expired,qris_image;

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getAmount() {
            return amount;
        }

        public String getVa_number() {
            return va_number;
        }

        public String getVa_admin() {
            return va_admin;
        }

        public String getVa_expired() {
            return va_expired;
        }

        public String getQris_image() {
            return qris_image;
        }
    }
}
