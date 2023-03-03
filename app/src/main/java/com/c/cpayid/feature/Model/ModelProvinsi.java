package com.c.cpayid.feature.Model;

public class ModelProvinsi {
    String id,name,status,location_id;

    int pilih = 0;

    public int getPilih() {
        return pilih;
    }

    public void setPilih(int pilih) {
        this.pilih = pilih;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public ModelProvinsi(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setData(String name,String id){
        this.name = name;
        this.id = id;

    }
}
