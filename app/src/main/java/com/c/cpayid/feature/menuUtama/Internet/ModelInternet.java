package com.c.cpayid.feature.menuUtama.Internet;

public class ModelInternet {
    String id,name;
    String description,icon,product_category_id,status;

    public ModelInternet(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
