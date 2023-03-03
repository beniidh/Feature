package com.c.cpayid.feature.menuUtama.ListrikPLNPasca;

import java.util.ArrayList;

public class ResponCodeSub {

    String code,error,message;
ArrayList<mData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    public class mData{

        String id;

        public String getId() {
            return id;
        }
    }
}
