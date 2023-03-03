package com.c.cpayid.feature.Model;

public class ModelKecamatan {

    String id,name,province_id,regencies_id;
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

    public String getRegencies_id() {
        return regencies_id;
    }
}
