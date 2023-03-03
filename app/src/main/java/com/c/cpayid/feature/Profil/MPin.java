package com.c.cpayid.feature.Profil;

public class MPin {

    String pin,confirm_pin, mac_address, ip_address, user_agent;
    double latitude, longitude;
    String code,message;

    public MPin(String pin, String confirm_pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude) {
        this.pin = pin;
        this.confirm_pin = confirm_pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
