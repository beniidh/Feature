package com.c.cpayid.feature.SaldoServer;

public class ResponUPP {

    String code,error;
    mData data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public mData getData() {
        return data;
    }

    public class mData {

        String id;

        public String getId() {
            return id;
        }

        String status;

        public String getStatus() {
            return status;
        }
    }
}
