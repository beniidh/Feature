package com.c.cpayid.feature.Fragment.Respon;

import java.util.ArrayList;

public class MRuningText {

    String code,error;
   ArrayList<MData> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<MData> getData() {
        return data;
    }

    public class MData{


        String server_id,name,text_apk,cs_whatsapp,cs_telegram,cs_email;

        public String getServer_id() {
            return server_id;
        }

        public String getName() {
            return name;
        }

        public String getText_apk() {
            return text_apk;
        }

        public String getCs_whatsapp() {
            return cs_whatsapp;
        }

        public String getCs_telegram() {
            return cs_telegram;
        }

        public String getCs_email() {
            return cs_email;
        }
    }
}
