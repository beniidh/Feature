package com.c.cpayid.feature.Model;

public class Mlogin {
    String id,code,name,email,phone,message,token,error;
    String credentials,password,ip_address,user_agent,mac_address;
    Double longitude,latitude;
    String push_notif_id;
    com.c.cpayid.feature.Model.data data;
    String server_id;


    public Mlogin(String server_id,String credentials, String password, String push_notif_id, String ip_address,String mac_address, String user_agent, Double longitude, Double latitude) {
        this.credentials = credentials;
        this.password = password;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.mac_address =mac_address;
        this.latitude = latitude;
        this.push_notif_id = push_notif_id;
        this.server_id = server_id;
    }


    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public com.c.cpayid.feature.Model.data getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
