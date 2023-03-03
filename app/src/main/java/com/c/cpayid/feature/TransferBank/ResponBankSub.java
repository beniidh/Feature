package com.c.cpayid.feature.TransferBank;

import java.util.ArrayList;

public class ResponBankSub {

    String code,error;
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

    public class Data{
        String id;

        public String getId() {
            return id;
        }
    }
}
