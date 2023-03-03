package com.c.cpayid.feature.Model;

public class ModelKabupaten {

    String id,name,province_id;
    Boolean pilihan = false;

    public Boolean getPilihan() {
        return pilihan;
    }

    public void setPilihan(Boolean pilihan) {
        this.pilihan = pilihan;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProvince_id() {
        return province_id;
    }
}
