package com.c.cpayid.feature.TransferBank;

public class MinquiryBank {
    String sku_code,customer_no,type,mac_address,ip_address,user_agent;
    String rubah;
    double longitude,latitude;
    int amount;

    public MinquiryBank(String buyer_sku_code, int amount, String customer_no, String type, String mac_address, String ip_address, String user_agent, double longitude, double latitude) {
        this.sku_code = buyer_sku_code;
        //rubah
        this.customer_no = customer_no;
        this.type = type;
        this.mac_address = mac_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.amount = amount;
        this.latitude = latitude;
        this.ip_address = ip_address;
    }
}

