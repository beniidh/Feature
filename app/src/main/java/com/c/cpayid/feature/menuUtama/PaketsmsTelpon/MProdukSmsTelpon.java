package com.c.cpayid.feature.menuUtama.PaketsmsTelpon;

public class MProdukSmsTelpon {

    String id,code,name,brand,basic_price,markup_price,status,description,product_subcategory_id,total_price;
    boolean seller_product_status,multi,gangguan;

    public boolean isGangguan() {
        return gangguan;
    }

    public String getId() {
        return id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getBasic_price() {
        return basic_price;
    }

    public String getMarkup_price() {
        return markup_price;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getProduct_subcategory_id() {
        return product_subcategory_id;
    }

    public boolean isSeller_product_status() {
        return seller_product_status;
    }

    public boolean isMulti() {
        return multi;
    }
}
