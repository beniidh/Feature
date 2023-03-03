package com.c.cpayid.feature.menuUtama.VoucherGame;

public class MVoucherGame {

    public MVoucherGame(String id, String name, String description, String product_category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.product_category_id = product_category_id;
    }

    String id,name,description,icon,product_category_id,status;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getStatus() {
        return status;
    }
}
