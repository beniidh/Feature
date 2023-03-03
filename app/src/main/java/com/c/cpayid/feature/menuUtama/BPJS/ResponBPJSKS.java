package com.c.cpayid.feature.menuUtama.BPJS;

import java.util.ArrayList;

public class ResponBPJSKS {
    String rubah;

    String code,error;
    ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public class Data{
        String id;

        public String getId() {
            return id;
        }
    }

}
