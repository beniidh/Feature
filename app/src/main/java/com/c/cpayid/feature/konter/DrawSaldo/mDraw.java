package com.c.cpayid.feature.konter.DrawSaldo;

public class mDraw {
    String mac_address, ip_address, user_agent,pin,receiver,code,error,message;
    double latitude, longitude, amount;

    public mDraw(String mac_address, String ip_address, String user_agent, String pin, String receiver, double latitude, double longitude, double amount) {
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.pin = pin;
        this.receiver = receiver;
        this.latitude = latitude;
        this.longitude = longitude;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
