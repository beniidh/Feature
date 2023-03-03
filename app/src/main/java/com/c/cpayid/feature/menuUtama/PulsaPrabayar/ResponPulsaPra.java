package com.c.cpayid.feature.menuUtama.PulsaPrabayar;

import java.util.List;

public class ResponPulsaPra {
    String code,message,error;

    List<MPulsaPra> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public List<MPulsaPra> getData() {
        return data;
    }
}
