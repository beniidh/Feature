package com.c.cpayid.feature.menuUtama.Paket;

public class MProdukPaketData {

    String id, code, brand, basic_price, name, description, icon, product_category_id, created_at, updated_at, total_price;
    boolean gangguan;

    public String getId() {
        return id;
    }

    public boolean isGangguan() {
        return gangguan;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getBasic_price() {
        return basic_price;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
