package com.c.cpayid.feature.menuUtama.ListrikPLN;

import java.util.List;

public class ResponListrikPln {

    String code, message;

    List<ModelProdukPln> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelProdukPln> getData() {
        return data;
    }
}
