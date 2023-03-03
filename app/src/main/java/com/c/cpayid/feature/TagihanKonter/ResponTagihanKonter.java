package com.c.cpayid.feature.TagihanKonter;

import java.util.ArrayList;

public class ResponTagihanKonter {
    String code, error, message;
    ArrayList<mData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<mData> getData() {
        return data;
    }

    public class mData {

        String id, user_paylater_id, type, status, total_bill, proof_payment, created_as, updated_at;
        User user;
        UserPaylatter user_paylater;

        public UserPaylatter getUser_paylater() {
            return user_paylater;
        }

        public String getId() {
            return id;
        }

        public String getUser_paylater_id() {
            return user_paylater_id;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getTotal_bill() {
            return total_bill;
        }

        public String getProof_payment() {
            return proof_payment;
        }

        public String getCreated_as() {
            return created_as;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public User getUser() {
            return user;
        }

        public class User {

            String id, code, name, store_name, paylater_status;

            public String getId() {
                return id;
            }

            public String getCode() {
                return code;
            }

            public String getName() {
                return name;
            }

            public String getStore_name() {
                return store_name;
            }

            public String getPaylater_status() {
                return paylater_status;
            }
        }

        public class UserPaylatter{
         String id,user_id,start_date,due_date;

            public String getId() {
                return id;
            }

            public String getUser_id() {
                return user_id;
            }

            public String getStart_date() {
                return start_date;
            }

            public String getDue_date() {
                return due_date;
            }
        }

    }
}
