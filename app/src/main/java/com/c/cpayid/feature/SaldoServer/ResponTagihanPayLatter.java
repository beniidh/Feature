package com.c.cpayid.feature.SaldoServer;

public class ResponTagihanPayLatter {

    String code,message,error;
    mData data;

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public String getError() {
        return error;
    }
    public mData getData() {
        return data;
    }

    public class mData{

        String id,user_id,start_date,due_date,due_day,total_bill;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getStart_date() {
            return start_date;
        }

        public String getDue_date() {
            return due_date;
        }

        public String getDue_day() {
            return due_day;
        }

        public String getTotal_bill() {
            return total_bill;
        }
    }
}
