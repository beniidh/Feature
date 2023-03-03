package com.c.cpayid.feature;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.mOTP;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.chaos.view.PinView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerify extends BaseActivity {

    PinView otpverify;
    Button VerifikasiOTP;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpvery);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Insert OTP <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        otpverify = findViewById(R.id.otpverify);
        VerifikasiOTP = findViewById(R.id.VerifikasiOTP);

        VerifikasiOTP.setOnClickListener(v -> {
            if (otpverify.length() == 6) {
                verify(Objects.requireNonNull(otpverify.getText()).toString());
            } else {
                Toast.makeText(getApplicationContext(), "Pin tidak boleh kurang dari 6", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        otpverify.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        otpverify.setCursorColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));

        DrawableMap.changeColor(VerifikasiOTP.getBackground(), "green");
        DrawableMap.changeColorStroke(VerifikasiOTP.getBackground(), "green");
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

    private void verify(String numberOTP) {
        String id = getIntent().getStringExtra("userid");
        String otp = getIntent().getStringExtra("otp");
        String push = getIntent().getStringExtra("pushid");
        Api api = RetroClient.getApiServices();
        mOTP motp = new mOTP(id, otp, numberOTP, Value.getMacAddress(getApplicationContext()),
                Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), push, Double.parseDouble(Preference.getLang(
                getApplicationContext())), Double.parseDouble(Preference.getLong(getApplicationContext())));

        Call<mOTP> call = api.SetverifyOtp(motp);
        call.enqueue(new Callback<mOTP>() {
            @Override
            public void onResponse(@NonNull Call<mOTP> call, @NonNull Response<mOTP> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    Intent home = new Intent(OtpVerify.this, DrawerActivity.class);
                    startActivity(home);
                    String token = response.body().getData().getToken();
                    Preference.getSharedPreference(getApplicationContext());
                    Preference.setToken(getApplicationContext(), token);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<mOTP> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}