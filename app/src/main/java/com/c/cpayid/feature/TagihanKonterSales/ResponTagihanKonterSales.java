package com.c.cpayid.feature.TagihanKonterSales;

import java.util.ArrayList;

public class ResponTagihanKonterSales {
    String code, error;

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

    public class mData {

        String due_date,total_bill;
        mUser user;

        public String getDue_date() {
            return due_date;
        }

        public String getTotal_bill() {
            return total_bill;
        }

        public mUser getUser() {
            return user;
        }



        public class mUser {

            String store_name;

            public String getStore_name() {
                return store_name;
            }
        }



    }
}
