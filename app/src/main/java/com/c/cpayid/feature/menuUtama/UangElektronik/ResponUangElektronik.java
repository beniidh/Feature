package com.c.cpayid.feature.menuUtama.UangElektronik;

import java.util.ArrayList;

public class ResponUangElektronik {

    String code, message,error;
    ArrayList<MUangElektronik> data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MUangElektronik> getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
