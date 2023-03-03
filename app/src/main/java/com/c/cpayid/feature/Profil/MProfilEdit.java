package com.c.cpayid.feature.Profil;

public class MProfilEdit {
    String name,store_name,address;
    int province_id,regencies_id,districts_id,sub_districts_id,postal_code_id;



    public MProfilEdit(String name, String store_name, String address, int province_id, int regencies_id, int districts_id, int sub_districts_id, int postal_code_id) {
        this.name = name;
        this.store_name = store_name;
        this.address = address;
        this.province_id = province_id;
        this.regencies_id = regencies_id;
        this.districts_id = districts_id;
        this.sub_districts_id = sub_districts_id;
        this.postal_code_id = postal_code_id;
    }

}
