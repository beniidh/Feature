package com.c.cpayid.feature.Respon;

public class ResponResetPassword {

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

        String message,url;

        public String getMessage() {
            return message;
        }

        public String getUrl() {
            return url;
        }
    }
}
