package com.c.cpayid.feature.Model;

public class MResestPIN {

    String old_pin,new_pin,confirm_new_pin,mac_address,ip_address,user_agent;
    double longitude,latitude;

    public MResestPIN(String old_pin, String new_pin, String confirm_new_pin, String mac_address, String ip_address, String user_agent, double longitude, double latitude) {
        this.old_pin = old_pin;
        this.new_pin = new_pin;
        this.confirm_new_pin = confirm_new_pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
