package com.c.cpayid.feature.PersetujuanSaldoSales;

public class SendDataPersetujuan {
    String id,pin,status,mac_address,ip_address,user_agent;
    double latitude,longitude;

    public SendDataPersetujuan(String id, String pin, String status, String mac_address, String ip_address, String user_agent, double latitude, double longitude) {
        this.id = id;
        this.pin = pin;
        this.status = status;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
