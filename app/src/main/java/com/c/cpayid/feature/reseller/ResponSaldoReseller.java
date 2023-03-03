package com.c.cpayid.feature.reseller;

import java.util.ArrayList;

public class ResponSaldoReseller {

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

    public class Data{

        String id,type,amount,updated_at,status;
        User user;

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getAmount() {
            return amount;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public User getUser() {
            return user;
        }

        public class User{
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
}
