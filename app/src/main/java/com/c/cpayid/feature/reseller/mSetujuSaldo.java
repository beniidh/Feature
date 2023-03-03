package com.c.cpayid.feature.reseller;

public class mSetujuSaldo {

    String id,pin,status,type_wallet,mac_address,ip_address,user_agent;
    double longitude,latitude;

    public mSetujuSaldo(String id, String status, String type_wallet,
                        String mac_address, String ip_address, String user_agent,
                        double longitude, double latitude) {

        this.id = id;
        this.status = status;
        this.type_wallet = type_wallet;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public String getStatus() {
        return status;
    }

    public String getType_wallet() {
        return type_wallet;
    }

    public String getMac_address() {
        return mac_address;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
