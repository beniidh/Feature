package com.c.cpayid.feature;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Modal.OtpInsert;
import com.c.cpayid.feature.Model.MRegisData;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpSend extends BaseActivity {

    Button sendEmail,SendWaOtp;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_psend);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pilih metode OTP <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        sendEmail = findViewById(R.id.sendEmailOtp);
        sendEmail.setOnClickListener(v -> intentOTP());
        SendWaOtp = findViewById(R.id.SendWaOtp);
        SendWaOtp.setOnClickListener(v -> intentOTPWA());
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvTitle)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(sendEmail.getBackground(), "green");
        DrawableMap.changeColorStroke(sendEmail.getBackground(), "green");
        DrawableMap.changeColorStroke(SendWaOtp.getBackground(), "green");
    }

    public void intentOTP() {
        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());

        Api api = RetroClient.getApiServices();
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTP(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(@NonNull Call<MRegisData> call, @NonNull Response<MRegisData> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    Intent otpInsert = new Intent(OtpSend.this, OtpInsert.class);
                    otpInsert.putExtra("user_id", user_id);
                    otpInsert.putExtra("otp_id", otp_id);
                    otpInsert.putExtra("otp","Kode telah dikirim ke Email, silahkan cek Email Anda");
                    Preference.setTrackRegister(getApplicationContext(), "2");
                    startActivity(otpInsert);
                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MRegisData> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public void intentOTPWA() {
        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());

        Api api = RetroClient.getApiServices();
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTPWA(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(@NonNull Call<MRegisData> call, @NonNull Response<MRegisData> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    Intent otpInsert = new Intent(OtpSend.this, OtpInsert.class);
                    otpInsert.putExtra("user_id", user_id);
                    otpInsert.putExtra("otp_id", otp_id);
                    otpInsert.putExtra("otp","Kode telah dikirim ke WA, silahkan cek WA Anda");
                    Preference.setTrackRegister(getApplicationContext(), "2");
                    startActivity(otpInsert);
                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MRegisData> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}