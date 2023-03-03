package com.c.cpayid.feature.menuUtama.ListrikPLNPasca;

import java.util.List;

public class ResponListrikPlnPasca {

    String code, message;

    List<ModelProdukPlnPasca> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ModelProdukPlnPasca> getData() {
        return data;
    }
}
