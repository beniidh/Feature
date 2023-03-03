package com.c.cpayid.feature.sharePreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.c.cpayid.feature.engine.ApplicationVariable;

public class Preference {

    static final String KEY_USER_ID ="user_id";
    static final String KEY_OTP_ID = "otp_id";
    static final String KEY_USER_CODE = "code_id";
    static final String KEY_PHONE = "phone_id";

    /** Pendlakarasian Shared Preferences yang berdasarkan paramater context */
    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    //user id
    public static void setKeyUserId(Context context, String userid){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_ID, userid);
        editor.apply();
    }

    public static void setNo(Context context, String no){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("no" + ApplicationVariable.applicationId, no);
        editor.apply();
    }
    public static String getNo(Context context){
        return getSharedPreference(context).getString("no" + ApplicationVariable.applicationId,"");
    }

    public static void setPascatype(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("sbk" + ApplicationVariable.applicationId, id);
        editor.apply();
    }

    public static String getPascatype(Context context){
        return getSharedPreference(context).getString("sbk" + ApplicationVariable.applicationId,"");
    }

    public static void setName(Context context, String name){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("nameM" + ApplicationVariable.applicationId, name);
        editor.apply();
    }
    public static String getName(Context context){
        return getSharedPreference(context).getString("nameM" + ApplicationVariable.applicationId,"");
    }

    public static void setID(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("idM" + ApplicationVariable.applicationId, id);
        editor.apply();
    }
    public static String getID(Context context){
        return getSharedPreference(context).getString("idM" + ApplicationVariable.applicationId,"");
    }

    public static void setNilaiNotif(Context context,int id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("Notifikasi" + ApplicationVariable.applicationId,id);
        editor.apply();
    }
    public static int getNilaiNotif(Context context){
        return getSharedPreference(context).getInt("Notifikasi" + ApplicationVariable.applicationId,0);
    }

    //otp id
    public static void setKeyOtpId(Context context, String otpid){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_OTP_ID, otpid);
        editor.apply();
    }

    public static void setKeyUserCode(Context context, String usercode){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USER_CODE, usercode);
        editor.apply();
    }

    public static void setKeyPhone(Context context, String phoneid){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PHONE, phoneid);
        editor.apply();
    }
    /** Mengembalikan nilai dari key KEY_USER_TEREGISTER berupa String */
    public static String getKeyPhone(Context context){
        return getSharedPreference(context).getString(KEY_PHONE,"");
    }

    public static String getKeyUserId(Context context){
        return getSharedPreference(context).getString(KEY_USER_ID,"");
    }

    public static String getKeyUserCode(Context context){
        return getSharedPreference(context).getString(KEY_USER_CODE,"");
    }

    public static String getKeyOtpId(Context context){
        return getSharedPreference(context).getString(KEY_OTP_ID,"");
    }


    //ID provinsi

    public static void setIDProvinsi(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDProvinsi" + ApplicationVariable.applicationId, id);
        editor.apply();
    }
    public static String getIDProvinsi(Context context){
        return getSharedPreference(context).getString("IDProvinsi" + ApplicationVariable.applicationId,"");
    }

    // ID kabupaten

    public static void setIDKabupaten(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDKabupaten" + ApplicationVariable.applicationId, id);
        editor.apply();
    }
    public static String getIDKabupaten(Context context){
        return getSharedPreference(context).getString("IDKabupaten" + ApplicationVariable.applicationId,"");
    }

    // ID Kecamatan

    public static void setIDKecamatan(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDKecamatan" + ApplicationVariable.applicationId, id);
        editor.apply();
    }
    public static String getIDKecamatan(Context context){
        return getSharedPreference(context).getString("IDKecamatan" + ApplicationVariable.applicationId,"");
    }

    // ID Kelurahan

    public static void setIDKelurahan(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDKelurahan" + ApplicationVariable.applicationId, id);
        editor.apply();
    }
    public static String getIDKelurahan(Context context){
        return getSharedPreference(context).getString("IDKelurahan" + ApplicationVariable.applicationId,"");
    }

    //ID postcode

    public static void setIDPost(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("IDPost" + ApplicationVariable.applicationId, id);
        editor.apply();
    }
    public static String getIDPost(Context context){
        return getSharedPreference(context).getString("IDPost" + ApplicationVariable.applicationId,"");
    }

    public static void setkredentials(Context context, String credentials){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("credentials" + ApplicationVariable.applicationId, credentials);
        editor.apply();
    }
    public static String getKredentials(Context context){
        return getSharedPreference(context).getString("credentials" + ApplicationVariable.applicationId,"");
    }
    public static void setPIN(Context context, String pin){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("PIN" + ApplicationVariable.applicationId, pin);
        editor.apply();
    }

    public static void setToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("token" + ApplicationVariable.applicationId, token);
        editor.apply();
    }
    public static String getToken(Context context){
        return getSharedPreference(context).getString("token" + ApplicationVariable.applicationId,"");
    }

    // Set Roles ID

    public static void setSaldoku(Context context, String saldoku){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("saldoku" + ApplicationVariable.applicationId, saldoku);
        editor.apply();
    }

    public static String getSaldoku(Context context){
        return getSharedPreference(context).getString("saldoku" + ApplicationVariable.applicationId,"");
    }

    public static void setUrlIcon(Context context, String iconurl){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("iconurl" + ApplicationVariable.applicationId, iconurl);
        editor.apply();
    }

    public static String getIconUrl(Context context){
        return getSharedPreference(context).getString("iconurl" + ApplicationVariable.applicationId,"");
    }

    public static void setSaldoServer(Context context, String saldo){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("saldoserver" + ApplicationVariable.applicationId, saldo);
        editor.apply();
    }

    public static String getSaldoServer(Context context){
        return getSharedPreference(context).getString("saldoserver" + ApplicationVariable.applicationId,"");
    }

    public static void setIdUPP(Context context, String upp){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("upp" + ApplicationVariable.applicationId, upp);
        editor.apply();
    }

    public static String getIdUPP(Context context){
        return getSharedPreference(context).getString("upp" + ApplicationVariable.applicationId,"");
    }

    public static void setTrackRegister(Context context, String track){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("track" + ApplicationVariable.applicationId, track);
        editor.apply();
    }

    public static String getTrackRegister(Context context){
        return getSharedPreference(context).getString("track" + ApplicationVariable.applicationId,"");
    }

    public static void setNoType(Context context, String no){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("tipe" + ApplicationVariable.applicationId, no);
        editor.apply();
    }
    public static String getNoType(Context context){
        return getSharedPreference(context).getString("tipe" + ApplicationVariable.applicationId,"");
    }

    public static void setLong(Context context, String no){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("long" + ApplicationVariable.applicationId, no);
        editor.apply();
    }
    public static String getLong(Context context){
        return getSharedPreference(context).getString("long" + ApplicationVariable.applicationId, "-71.064544");
    }

    public static void setLang(Context context, String no){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("lang" + ApplicationVariable.applicationId,  no);
        editor.apply();
    }
    public static String getLang(Context context){
        return getSharedPreference(context).getString("lang" + ApplicationVariable.applicationId, "42.28787");
    }

    public static void setServerID(Context context, String no){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("server" + ApplicationVariable.applicationId, no);
        editor.apply();
    }
    public static String getServerID(Context context){
        return getSharedPreference(context).getString("server" + ApplicationVariable.applicationId, "");
    }

    public static void setHeader(Context context, String header){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("header" + ApplicationVariable.applicationId, header);
        editor.apply();
    }
    public static String getHeader(Context context){
        return getSharedPreference(context).getString("header" + ApplicationVariable.applicationId,"");
    }

    public static void setFooter(Context context, String footer){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("footer" + ApplicationVariable.applicationId, footer);
        editor.apply();
    }
    public static String getFooter(Context context){
        return getSharedPreference(context).getString("footer" + ApplicationVariable.applicationId,"");
    }

    public static void setType(Context context, String type){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("type" + ApplicationVariable.applicationId, type);
        editor.apply();
    }
    public static String getTypee(Context context){
        return getSharedPreference(context).getString("type" + ApplicationVariable.applicationId,"");
    }

    public static void setIDsalesProduk(Context context, String type){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("ss" + ApplicationVariable.applicationId, type);
        editor.apply();
    }
    public static String getIDsalesProduk(Context context){
        return getSharedPreference(context).getString("ss" + ApplicationVariable.applicationId,"");
    }


    public static void setJenisProduk(Context context, String type){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString("jp" + ApplicationVariable.applicationId, type);
        editor.apply();
    }
    public static String getJenisProduk(Context context){
        return getSharedPreference(context).getString("jp" + ApplicationVariable.applicationId,"");
    }

}
