package com.c.cpayid.feature.menuUtama.PulsaPrabayar;

public class MTransaksiPraPulsa {

    String sku_code,customer_no,ref_id,type,wallet_type,mac_address,ip_address ,user_agent,pin;
    double longitude,latitude;

    public MTransaksiPraPulsa(String sku_code, String customer_no, String ref_id, String type, String wallet_type, String mac_address, String ip_address, String user_agent, String pin, double longitude, double latitude) {

        this.sku_code = sku_code;
        this.customer_no = customer_no;
        this.ref_id = ref_id;
        this.type = type;
        this.wallet_type = wallet_type;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.pin = pin;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
