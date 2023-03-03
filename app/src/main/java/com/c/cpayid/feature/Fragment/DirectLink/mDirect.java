package com.c.cpayid.feature.Fragment.DirectLink;

import java.util.ArrayList;

public class mDirect {

    String code,error;

    ArrayList<Data>data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public static class Data{
        String id,name,url,icon;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getIcon() {
            return icon;
        }
    }
}
