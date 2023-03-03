package com.c.cpayid.feature.Model;

public class MsetPIN {
    String pin,confirm_pin,mac_address,ip_address,user_agent;
    double longitude,latitude;

    String code,error;

    public MsetPIN(String pin, String confirm_pin, String mac_address, String ip_address, String user_agent, double longitude, double latitude) {
        this.pin = pin;
        this.confirm_pin = confirm_pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
