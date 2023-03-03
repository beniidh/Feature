package com.c.cpayid.feature.SaldoServer;

public class Mpayletter {

    String id,type,mac_address,ip_address,user_agent,status,created_by,approved_by,created_at,updated_at;
    double amount,longitude,latitude;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
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

    public String getStatus() {
        return status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public double getAmount() {
        return amount;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
