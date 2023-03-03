package com.c.cpayid.feature.TopUpSaldoku;

public class mCancel {
    String id,status,mac_address,ip_address,user_agent,code,error;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public mCancel(String id, String status, String mac_address, String ip_address, String user_agent) {
        this.id = id;
        this.status = status;
        this.mac_address = mac_address;
        this.ip_address = ip_address;
        this.user_agent = user_agent;
    }


}
