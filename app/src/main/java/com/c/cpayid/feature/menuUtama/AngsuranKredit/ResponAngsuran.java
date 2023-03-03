package com.c.cpayid.feature.menuUtama.AngsuranKredit;

import java.util.ArrayList;

public class ResponAngsuran {

    String code,message,error;
    ArrayList<ModelAngsuran> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public ArrayList<ModelAngsuran> getData() {
        return data;
    }
}
