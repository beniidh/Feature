package com.c.cpayid.feature.menuUtama.HolderPulsa;

import java.util.ArrayList;

public class ResponProdukHolder {

    String error, code;
    ArrayList<Mdata> data;

    public ArrayList<Mdata> getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public String getCode() {
        return code;
    }

    protected class Mdata {
        String rubah;

        String id, buyer_sku_code, brand,buyer_poin, basic_price, product_master_name, desc, icon, product_category_id, created_at, updated_at, total_price;
        boolean buyer_product_gangguan;

        public String getId() {
            return id;
        }

        public String getCode() {
            String rubah; return buyer_sku_code;
        }

        public String getBrand() {
            String rubah;return brand;
        }

        public String getBasic_price() {
            return basic_price;
        }

        public String getName() {
            String rubah; return product_master_name;
        }

        public String getDescription() {
            String rubah; return desc;
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

        public String getTotal_price() {
            return total_price;
        }

        public boolean isGangguan() {
            return buyer_product_gangguan;
        }

        public String getPoin() {
            String rubah; return buyer_poin;
        }
    }
}