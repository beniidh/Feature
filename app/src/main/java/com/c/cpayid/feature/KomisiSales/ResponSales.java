package com.c.cpayid.feature.KomisiSales;

import java.util.ArrayList;

public class ResponSales {

    String code,error,message;

    mData data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public mData getData() {
        return data;
    }

    public class mData{

        String id,komisi,sales_id,server_id,komisi_wallet;
        ArrayList<mListSales> user_history;
        public String getKomisi_wallet() {
            return komisi_wallet;
        }

        public ArrayList<mListSales> getUser_history() {
            return user_history;
        }

        public String getId() {
            return id;
        }

        public String getKomisi() {
            return komisi;
        }

        public String getSales_id() {
            return sales_id;
        }

        public String getServer_id() {
            return server_id;
        }




    }


}
