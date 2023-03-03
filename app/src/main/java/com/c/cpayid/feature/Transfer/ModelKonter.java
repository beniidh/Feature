package com.c.cpayid.feature.Transfer;

import java.util.ArrayList;

public class ModelKonter {
    String code,error,message;
    ArrayList<data> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ModelKonter.data> getData() {
        return data;
    }

    class data{

        String id,code,name,store_name;

        public String getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getStore_name() {
            return store_name;
        }
    }
}
