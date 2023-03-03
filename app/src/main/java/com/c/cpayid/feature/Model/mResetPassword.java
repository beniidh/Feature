package com.c.cpayid.feature.Model;

public class mResetPassword {

    String credentials,ip_address,mac_address,user_agent;
    double longitude,latitude;

    public mResetPassword(String credentials, String ip_address, String mac_address, String user_agent, double longitude, double latitude) {
        this.credentials = credentials;
        this.ip_address = ip_address;
        this.mac_address = mac_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
