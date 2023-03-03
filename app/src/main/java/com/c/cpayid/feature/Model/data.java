package com.c.cpayid.feature.Model;

import java.util.ArrayList;

public class data {

    String id,code,name,email,phone;
    String token,icon,type_login,otp_id;
    String store_name,address,status,roles,parent,province_id,regencies_id,districts_id,sub_districts_id,postal_code_id,mac_address,ip_address,user_agent,refresh_token,longitude,latitude,paylater_status,referal_code,server_code,server_id,avatar,logo,login_at,id_card,selfie,id_card_selfie,is_deleted,created_at,updated_at;

    ArrayList<MSubMenu> menu;
   MWallet wallet;

   DataSales sales;
   Point user_poin;

    public Point getUser_poin() {
        return user_poin;
    }

    public DataSales getSales() {
        return sales;
    }

    public MWallet getWallet() {
        return wallet;
    }

    public ArrayList<MSubMenu> getMenu() {
        return menu;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getRoles() {
        return roles;
    }

    public String getParent() {
        return parent;
    }

    public String getOtp_id() {
        return otp_id;
    }

    public String getType_login() {
        return type_login;
    }

    public String getProvince_id() {
        return province_id;
    }

    public String getRegencies_id() {
        return regencies_id;
    }

    public String getDistricts_id() {
        return districts_id;
    }

    public String getSub_districts_id() {
        return sub_districts_id;
    }

    public String getPostal_code_id() {
        return postal_code_id;
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

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPaylater_status() {
        return paylater_status;
    }

    public String getReferal_code() {
        return referal_code;
    }

    public String getServer_code() {
        return server_code;
    }

    public String getServer_id() {
        return server_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLogo() {
        return logo;
    }

    public String getLogin_at() {
        return login_at;
    }

    public String getId_card() {
        return id_card;
    }

    public String getSelfie() {
        return selfie;
    }

    public String getId_card_selfie() {
        return id_card_selfie;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getToken() {
        return token;
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

    public String getIcon() {
        return icon;
    }

    //sales

    public class DataSales{


        String code,name,phone;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }

    public class Point{

        String id,user_id;
        int poin;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public int getPoin() {
            return poin;
        }
    }
}
