package com.c.cpayid.feature.TarikKomisi;

public class mKomisi {

    String pin,type,mac_address,ip_address,user_agent;
    double amount,longitude,latitude;

    public mKomisi(String pin, String type, String mac_address, String ip_address, String user_agent, double amount, double longitude, double latitude) {
        this.pin = pin;
        this.type = type;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.amount = amount;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
