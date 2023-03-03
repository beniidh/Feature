package com.c.cpayid.feature.Api;

import android.content.Context;
import android.webkit.WebView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Model.data;

public class Value {

    String credentials;
    String password;
    com.c.cpayid.feature.Model.data data;
    String message;
//    public static String BASE_URL = "https://api-mobile.c-software.id/";   //https://api-mobile-staging.abatapulsa.com/
    public static String BASE_URL = "https://api-mobile.c-flazz.id/";

    public String getMessage() {
        return message;
    }

    public Value() {
    }

    public com.c.cpayid.feature.Model.data getData() {
        return data;
    }

    public Value(String credentials, String password) {
        this.credentials = credentials;
        this.password = password;
    }

    public static String getMacAddress(Context context) {
        return utils.getMacAddr();
    }

    public static String getUserAgent(Context context) {

        String ua = new WebView(context).getSettings().getUserAgentString();
        return ua;
    }

    public static String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }


}
