package com.c.cpayid.feature.Model;

public class MRegisData {

    String user_id,user_code,email,phone,otp_id,created_at,updated_at,code,error;

    public String getError() {
        return error;
    }

    public MRegisData() {
    }

    public MRegisData(String user_id, String user_code, String phone, String otp_id) {
        this.user_id = user_id;
        this.user_code = user_code;
        this.phone = phone;
        this.otp_id = otp_id;
    }

    public String getCode() {
        return code;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_code() {
        return user_code;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getOtp_id() {
        return otp_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
