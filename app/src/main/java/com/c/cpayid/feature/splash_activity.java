package com.c.cpayid.feature;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.Mlogin;
import com.c.cpayid.feature.databinding.ActivitySplashActivityBinding;
import com.c.cpayid.feature.pinNew.PinNew;
import com.c.cpayid.feature.sharePreference.Preference;
import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splash_activity extends AppCompatActivity {
    private ActivitySplashActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();
        int delay = 2000;

        int versionCodein = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        binding.versionCode.setText(new StringBuilder().append("versi ").append(versionCodein).append(".").append(versionName).toString());
        String coder = Preference.getTrackRegister(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(() -> {

            String token = Preference.getToken(getApplicationContext());

            if(coder.equals("")) {
                if (!token.equals("")) {

                    Api api = RetroClient.getApiServices();
                    Call<Mlogin> call = api.ChekToken("Bearer " + token);
                    call.enqueue(new Callback<Mlogin>() {
                        @Override
                        public void onResponse(Call<Mlogin> call, Response<Mlogin> response) {
                            String code = response.body().getCode();

                            if (code.equals("200")) {
                                Intent home = new Intent(splash_activity.this, DrawerActivity.class);
                                startActivity(home);
                                finish();

                            } else {

                                Intent login = new Intent(splash_activity.this, PinNew.class);
                                startActivity(login);
                                finish();
                                StyleableToast.makeText(getApplicationContext(), "Token sudah berakhir,Silahkan Masukan PIN", Toast.LENGTH_LONG, R.style.mytoast2).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Mlogin> call, Throwable t) {
                            StyleableToast.makeText(getApplicationContext(), " Internet Belum dinyalakan", Toast.LENGTH_LONG, R.style.mytoast2).show();

                        }
                    });

                } else {

                    Intent login = new Intent(splash_activity.this, LoginActivity.class);
                    startActivity(login);
                    finish();

                }
            }else {

                Intent intent = new Intent(splash_activity.this, PendingActivity.class);
                startActivity(intent);
                finish();
            }


        }, delay);
    }


}

