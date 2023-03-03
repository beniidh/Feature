package com.c.cpayid.feature.Transaksi;

import java.util.ArrayList;

public class mBankOption {

    String code, error;

    ArrayList<Data> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public class Data {

        String id, value, name, account_no, account_name,icon,type,bank_code;

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public String getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }

        public String getAccount_no() {
            return account_no;
        }

        public String getAccount_name() {
            return account_name;
        }

        public String getBank_code() {
            return bank_code;
        }

        public String getType() {
            return type;
        }
    }
}
