package com.c.cpayid.feature.Profil.Poin;

public class mExcange {

    String code,error;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    String poin_reward_id,pin,mac_address,ip_address,user_agent;
    double longitude,latitude;

    public mExcange(String poin_reward_id, String pin,
                    String mac_address, String ip_address,
                    String user_agent, double longitude,
                    double latitude) {
        this.poin_reward_id = poin_reward_id;
        this.pin = pin;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
