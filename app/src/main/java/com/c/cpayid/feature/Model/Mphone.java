package com.c.cpayid.feature.Model;

public class Mphone {
    String code;
    String error;
    String phone;

    public Mphone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
