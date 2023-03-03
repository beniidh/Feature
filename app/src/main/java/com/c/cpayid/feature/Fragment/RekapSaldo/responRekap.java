package com.c.cpayid.feature.Fragment.RekapSaldo;

import java.util.ArrayList;

public class responRekap {

    String code,error;
    Data data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public class Data{


        ArrayList<Item> items;

        public ArrayList<Item> getItems() {
            return items;
        }

        public class Item{

            String type,description,type_nominal,balance,created_at,nominal;

            public String getType() {
                return type;
            }

            public String getDescription() {
                return description;
            }

            public String getType_nominal() {
                return type_nominal;
            }

            public String getBalance() {
                return balance;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getNominal() {
                return nominal;
            }
        }
    }
}
