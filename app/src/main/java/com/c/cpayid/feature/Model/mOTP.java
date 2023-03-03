package com.c.cpayid.feature.Model;

public class mOTP {

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

    String user_id,otp_id,otp,mac_address,ip_address,user_agent,push_notif_id;
    double longitude,latitude;

    public mOTP(String user_id, String otp_id, String otp, String mac_address, String ip_address,
                String user_agent, String push_notif_id, double longitude, double latitude) {

        this.user_id = user_id;
        this.otp_id = otp_id;
        this.otp = otp;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.push_notif_id = push_notif_id;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public class Data{

        String code,name,token;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getToken() {
            return token;
        }
    }
}
