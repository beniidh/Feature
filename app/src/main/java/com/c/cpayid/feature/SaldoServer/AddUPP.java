package com.c.cpayid.feature.SaldoServer;

public class AddUPP {

    String user_paylater_id,pin,mac_address,ip_address,user_agent,type;
    double longitude,latitude,total_bill;

    public AddUPP(String user_paylater_id, String pin, String mac_address, String ip_address, String user_agent, double longitude, double latitude, String type, double total_bill) {
        this.user_paylater_id = user_paylater_id;
        this.pin = pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.total_bill = total_bill;
    }
}
