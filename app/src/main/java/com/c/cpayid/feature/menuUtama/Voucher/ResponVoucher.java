package com.c.cpayid.feature.menuUtama.Voucher;

import java.util.ArrayList;

public class ResponVoucher {

String code,error,message;
ArrayList<ModelVoucher> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ModelVoucher> getData() {
        return data;
    }
}
