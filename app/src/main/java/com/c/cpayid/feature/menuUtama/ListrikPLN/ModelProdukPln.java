package com.c.cpayid.feature.menuUtama.ListrikPLN;

public class ModelProdukPln {
    String rubah;
    String id,buyer_sku_code,product_master_name,buyer_poin,brand,basic_price,markup_price,status,seller_product_status,multi,desc,product_subcategory_id,start_cut_off,end_cut_off,total_price;
    boolean buyer_product_gangguan;
    public String getId() {
        return id;
    }

    public boolean isGangguan() {
        return buyer_product_gangguan;
    }

    public String getCode() {
        return buyer_sku_code;
    }

    public String getName() {
        return product_master_name;
    }
    //rubah

    public String getPoin() {
        return buyer_poin;
    }
    //rubah

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

    public String getSeller_product_status() {
        return seller_product_status;
    }

    public String getMulti() {
        return multi;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getDescription() {
        return desc;
    }
    //rubah

    public String getProduct_subcategory_id() {
        return product_subcategory_id;
    }

    public String getStart_cut_off() {
        return start_cut_off;
    }

    public String getEnd_cut_off() {
        return end_cut_off;
    }
}
