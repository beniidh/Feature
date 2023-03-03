package com.c.cpayid.feature.menuUtama.PulsaPascaBayar;

import java.util.ArrayList;

public class ResponProdukSubPPasca {
    String code,error,message;

    ArrayList<MProdukPPasca> data;

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<MProdukPPasca> getData() {
        return data;
    }
}
