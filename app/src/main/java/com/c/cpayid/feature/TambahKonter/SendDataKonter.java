package com.c.cpayid.feature.TambahKonter;

public class SendDataKonter {

    String name,store_name,email,phone,address,ip_address,mac_address,user_agent;
    double longitude,latitude;
    int province_id,regencies_id,districts_id,sub_districts_id,postal_code_id;

    public SendDataKonter(String name, String store_name, String email, String phone, String address, String ip_address, String mac_address, String user_agent, int province_id, int regencies_id, int districts_id, int sub_districts_id, int postal_code_id, double longitude, double latitude) {
        this.name = name;
        this.store_name = store_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.ip_address = ip_address;
        this.mac_address = mac_address;
        this.user_agent = user_agent;
        this.province_id = province_id;
        this.regencies_id = regencies_id;
        this.districts_id = districts_id;
        this.sub_districts_id = sub_districts_id;
        this.postal_code_id = postal_code_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
