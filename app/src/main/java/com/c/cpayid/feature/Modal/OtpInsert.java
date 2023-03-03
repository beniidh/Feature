package com.c.cpayid.feature.Modal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.MOtpVerif;
import com.c.cpayid.feature.Model.MRegisData;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.c.cpayid.feature.SyaratDanKetentuanActivity;
import com.chaos.view.PinView;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpInsert extends BaseActivity {

    PinView otp;
    Button verif;
    TextView otpsend;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_pinsert);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Verifikasi OTP<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        otp = findViewById(R.id.otpid);
        otpsend = findViewById(R.id.otpsend);
        otpsend.setText(getIntent().getStringExtra("otp"));
        verif = findViewById(R.id.VerifikasiOTP);
        otp.setPasswordHidden(true);
        verif.setOnClickListener(v -> {
            if (otp.length() < 6) {
                StyleableToast.makeText(getApplicationContext(), "Lengkapi OTP", Toast.LENGTH_SHORT).show();
            } else {
                String otpid = Objects.requireNonNull(otp.getText()).toString();

                Bundle ekstra = getIntent().getExtras();
                MOtpVerif mOtpVerif = new MOtpVerif(Preference.getKeyUserId(getBaseContext()), Preference.getKeyOtpId(getBaseContext()), otpid);

                Api api = RetroClient.getApiServices();
                Call<MOtpVerif> call = api.verifOTP(mOtpVerif);
                call.enqueue(new Callback<MOtpVerif>() {
                    @Override
                    public void onResponse(@NonNull Call<MOtpVerif> call, @NonNull Response<MOtpVerif> response) {
                        assert response.body() != null;
                        String code = response.body().getCode();
                        if (code.equals("200")) {
                            StyleableToast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            String token = response.body().getData().getToken();
                            Preference.getSharedPreference(getApplicationContext());
                            Preference.setToken(getApplicationContext(), token);
                            Intent intent = new Intent(OtpInsert.this, SyaratDanKetentuanActivity.class);
                            startActivity(intent);
                        } else {
                            StyleableToast.makeText(getApplicationContext(), "OTP Salah", Toast.LENGTH_SHORT, R.style.mytoast).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MOtpVerif> call, @NonNull Throwable t) {

                    }
                });
            }
        });

        TextView timer = findViewById(R.id.timer);
        new CountDownTimer(30000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("Kirim Ulang setelah: " + millisUntilFinished / 1000);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timer.setText("Kirim Ulang");
                timer.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                timer.setOnClickListener(v -> intentOTP());
            }
        }.start();
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        otp.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        otp.setCursorColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));

        DrawableMap.changeColor(verif.getBackground(), "green");
        DrawableMap.changeColorStroke(verif.getBackground(), "green");
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
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Belum dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MRegisData> call, @NonNull Throwable t) {

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