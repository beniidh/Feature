package com.c.cpayid.feature.Respon;

import com.c.cpayid.feature.Model.MSubPLN;

import java.util.ArrayList;

public class ResponSubP {
    String code ;
    String error;
    ArrayList<MSubPLN> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ArrayList<MSubPLN> getData() {
        return data;
    }
}
