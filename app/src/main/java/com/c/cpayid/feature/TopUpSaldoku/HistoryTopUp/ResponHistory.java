package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp;

import java.util.ArrayList;

public class ResponHistory {
    String code, error;
    ArrayList<mData>data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    class mData {
        dataBank payment_option_server;

        String id,payment_option_id, amount,qris_image, proof_payment,bank_code, status, created_at,type_payment,va_number,va_expired,va_admin;

        public String getId() {
            return id;
        }

        public String getPayment_option_id() {
            return payment_option_id;
        }

        public String getAmount() {
            return amount;
        }

        public String getProof_payment() {
            return proof_payment;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getBank_code() {
            return bank_code;
        }

        public String getType_payment() {
            return type_payment;
        }

        public String getVa_number() {
            return va_number;
        }

        public String getVa_expired() {
            return va_expired;
        }

        public String getVa_admin() {
            return va_admin;
        }

        public String getQris_image() {
            return qris_image;
        }

        public  dataBank getPayment_option_server() {
            return payment_option_server;
        }

        class dataBank{
String name,account_name,account_no,icon;

            public String getName() {
                return name;
            }

            public String getAccount_name() {
                return account_name;
            }

            public String getAccount_no() {
                return account_no;
            }

            public String getIcon() {
                return icon;
            }
        }
    }
}
