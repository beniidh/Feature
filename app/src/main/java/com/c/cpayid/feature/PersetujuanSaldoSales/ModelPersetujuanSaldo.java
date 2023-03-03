package com.c.cpayid.feature.PersetujuanSaldoSales;

public class ModelPersetujuanSaldo {

    String id,type,amount,mac_address,ip_address,user_agent,longitude,latitude,status,created_by,approved_by,created_at,updated_at;
    ModelUser user;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
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

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
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

    public ModelUser getUser() {
        return user;
    }
}
