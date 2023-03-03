package com.c.cpayid.feature.menuUtama.BPJS;

import java.util.ArrayList;

public class ResponProdukBPJS {

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
        String id,code,name,brand;

        public String getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getBrand() {
            return brand;
        }
    }
}
