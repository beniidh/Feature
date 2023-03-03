package com.c.cpayid.feature.konter;

public class MRegisKonter {

    String name;
    String email;
    String phone;
    String store_name,mac_address,ip_address;
    String address,password,confirm_password,user_agent;
    int province_id,regencies_id,districts_id,sub_districts_id,postal_code_id;
    Double latitude,longitude;

    String code,error;

    public MRegisKonter(String name, String email, String phone, String store_name, String mac_address, String ip_address, String address, String password, String confirm_password, String user_agent, int province_id, int regencies_id, int districts_id, int sub_districts_id, int postal_code_id, Double latitude, Double longitude) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.store_name = store_name;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.address = address;
        this.password = password;
        this.confirm_password = confirm_password;
        this.user_agent = user_agent;
        this.province_id = province_id;
        this.regencies_id = regencies_id;
        this.districts_id = districts_id;
        this.sub_districts_id = sub_districts_id;
        this.postal_code_id = postal_code_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
