package com.c.cpayid.feature.menuUtama.PaketsmsTelpon;

public class MsmsTelpon {

    String id,name,description,icon,product_category_id,status;

    public MsmsTelpon(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MsmsTelpon(String id, String name, String description, String icon, String product_category_id, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.product_category_id = product_category_id;
        this.status = status;
    }

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
