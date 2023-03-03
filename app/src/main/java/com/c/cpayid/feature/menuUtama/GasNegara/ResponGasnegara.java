package com.c.cpayid.feature.menuUtama.GasNegara;

import java.util.ArrayList;

public class ResponGasnegara {

    String id,error,message;
    ArrayList<mData> data;

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    public class mData{

        String id,name;
        String description,icon,product_category_id,status;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        public String getProduct_category_id() {
            return product_category_id;
        }

        public String getStatus() {
            return status;
        }
    }
}
