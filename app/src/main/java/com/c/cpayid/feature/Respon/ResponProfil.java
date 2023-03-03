package com.c.cpayid.feature.Respon;

import com.c.cpayid.feature.Model.MSubMenu;
import com.c.cpayid.feature.Model.data;

import java.util.ArrayList;

public class ResponProfil {
    String code, error, message;
    com.c.cpayid.feature.Model.data data;
    ArrayList<MSubMenu> menu;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public com.c.cpayid.feature.Model.data getData() {
        return data;
    }


}
