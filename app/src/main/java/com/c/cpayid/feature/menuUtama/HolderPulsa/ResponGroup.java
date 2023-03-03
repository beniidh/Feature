package com.c.cpayid.feature.menuUtama.HolderPulsa;

import java.util.ArrayList;

public class ResponGroup {

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

        String id,subcategory_id,name;

        public String getId() {
            return id;
        }

        public String getSubcategory_id() {
            return subcategory_id;
        }

        public String getName() {
            return name;
        }
    }
}
