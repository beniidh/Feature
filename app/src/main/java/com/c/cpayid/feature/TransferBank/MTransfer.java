package com.c.cpayid.feature.TransferBank;

public class MTransfer {
    String sku_code,customer_no,type,wallet_type,ref_id,pin,mac_address,ip_address,user_agent;
    double longitude,latitude;
    int amount;

    public MTransfer(String sku_code, String customer_no, int amount, String type, String wallet_type, String ref_id, String pin, String mac_address,
                     String ip_address, String user_agent, double longitude,
                     double latitude) {
        this.sku_code = sku_code;
        this.customer_no = customer_no;
        this.amount = amount;
        this.type = type;
        this.wallet_type = wallet_type;
        this.ref_id = ref_id;
        this.pin = pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
