package com.c.cpayid.feature.menuUtama.DirectLink;

import java.util.ArrayList;

public class mDirectL {
    String code,error;
    ArrayList<mData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<mData> getData() {
        return data;
    }

  public static class mData{
        String name,icon,url,status;

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        public String getUrl() {
            return url;
        }

        public String getStatus() {
            return status;
        }
    }
}
