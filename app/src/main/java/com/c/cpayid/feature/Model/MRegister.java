package com.c.cpayid.feature.Model;

public class MRegister {
    String name;
    String email;
    String phone;
    String push_notif_id;
    String message,error;
    String code,store_name,mac_address,ip_address;
    String address,parent,user_agent,server_id;
    int province_id,regencies_id,districts_id,sub_districts_id,postal_code_id;
    Double latitude,longitude;
    MRegisData data;

    public MRegister(String name, String push_notif_id, String email, String phone, String store_name, String mac_address, String ip_address, String address, String parent, String user_agent, int province_id, int regencies_id, int districts_id, int sub_districts_id, int postal_code_id, double latitude, double longitude,String server_id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.store_name = store_name;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.address = address;
        this.parent = parent;
        this.server_id = server_id;
        this.user_agent = user_agent;
        this.province_id = province_id;
        this.regencies_id = regencies_id;
        this.districts_id = districts_id;
        this.sub_districts_id = sub_districts_id;
        this.postal_code_id = postal_code_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.push_notif_id = push_notif_id;
    }

    public String getCode() {
        return code;
    }

    public MRegisData getData() {
        return data;
    }

    protected String getName() {
        return name;
    }

    protected String getEmail() {
        return email;
    }

    protected String getPhone() {
        return phone;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
