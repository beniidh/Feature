package com.c.cpayid.feature.konter;

import java.util.ArrayList;

public class Mkonter {

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

     class mData {
        String id, name, store_name, code,markup;
        Wallet wallet;

        public Wallet getWallet() {
            return wallet;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStore_name() {
            return store_name;
        }

        public String getCode() {
            return code;
        }

         public String getMarkup() {
             return markup;
         }
     }

    protected class Wallet {

        String saldoku, paylater;

        public String getSaldoku() {
            return saldoku;
        }

        public String getPaylatter() {
            return paylater;
        }
    }
}
