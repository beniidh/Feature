package com.c.cpayid.feature.Transaksi;

import java.util.ArrayList;

public class ModelInquiry {
    String buyer_sku_code,admin_fee,customer_no,customer_name,inquiry_type,ref_id,basic_price,markup_price,total_price,status,description,user_id,sales_id,server_id,mac_address,ip_address,user_agent,created_at;
    double longitude,latitude;

    detaill detail_product;
    public String getAdmin_fee() {
        return admin_fee;
    }

    public detaill getDetail_product() {
        return detail_product;
    }

    public String getBuyer_sku_code() {
        return buyer_sku_code;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getInquiry_type() {
        return inquiry_type;
    }

    public String getRef_id() {
        return ref_id;
    }

    public String getBasic_price() {
        return basic_price;
    }

    public String getMarkup_price() {
        return markup_price;
    }

    public String getSelling_price() {
        return total_price;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSales_id() {
        return sales_id;
    }

    public String getServer_id() {
        return server_id;
    }

    public String getMac_address() {
        return mac_address;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public String getCreated_at() {
        return created_at;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public class detaill{

         String  tarif,daya,lembar_tagihan;
         ArrayList<mDetail> detail;

        public ArrayList<mDetail> getDetail() {
            return detail;
        }

        public  String getTarif() {
            return tarif;
        }

        public String getDaya() {
            return daya;
        }

        public String getLembar_tagihan() {
            return lembar_tagihan;
        }


        public class mDetail{

            String periode,nilai_tagihan,admin,denda;

            public String getPeriode() {
                return periode;
            }

            public String getNilai_tagihan() {
                return nilai_tagihan;
            }

            public String getAdmin() {
                return admin;
            }

            public String getDenda() {
                return denda;
            }
        }

    }
}

