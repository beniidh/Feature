package com.c.cpayid.feature.Transaksi;

public class MInquiry {

    String sku_code,customer_no,type,mac_address,ip_address,user_agent;
    Double longitude,latitude;
    String rubah;

    public MInquiry(String buyer_sku_code, String customer_no, String type, String mac_address, String ip_address, String user_agent, Double longitude, Double latitude) {
        this.sku_code = buyer_sku_code;
        String rubah_buyer_sku;
        this.customer_no = customer_no;
        this.type = type;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getBuyer_sku_code() {
        return sku_code;
    }

    public String getCustomer_no() {
        return customer_no;
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

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}

