package com.c.cpayid.feature.menuUtama.TV;

import java.util.ArrayList;

public class ResponTV {
    String code,error,message;
    ArrayList<ModelTV> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ModelTV> getData() {
        return data;
    }
}
