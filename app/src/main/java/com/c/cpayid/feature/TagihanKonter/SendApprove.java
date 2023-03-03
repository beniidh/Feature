package com.c.cpayid.feature.TagihanKonter;

public class SendApprove {

    String user_paylater_payment_id,pin,status,mac_address,ip_address,user_agent;
    double longitude,latitude;

    public SendApprove(String user_paylater_payment_id, String pin, String status, String mac_address, String ip_address, String user_agent, double longitude, double latitude) {
        this.user_paylater_payment_id = user_paylater_payment_id;
        this.pin = pin;
        this.status = status;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
