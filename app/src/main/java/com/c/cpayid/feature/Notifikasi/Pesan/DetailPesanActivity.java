package com.c.cpayid.feature.Notifikasi.Pesan;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesanActivity extends BaseActivity {

    TextView judulpesan, Tanggaljudul, isijudul;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesan);

        if (getIntent().hasExtra("applicationId")) {
            String applicationId = getIntent().getStringExtra("applicationId");
            System.out.println("applicationId : " + applicationId);

            ApplicationVariable.applicationId = applicationId;
        }

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Detail Pesan <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        isijudul = findViewById(R.id.isijudul);
        Tanggaljudul = findViewById(R.id.Tanggaljudul);
        judulpesan = findViewById(R.id.judulpesan);
        getPesanNotifbyID(getIntent().getStringExtra("id"));
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

    private void getPesanNotifbyID(String id) {

        String token = "Bearer " + Preference.getToken(getApplication());
        Api api = RetroClient.getApiServices();
        Call<mPesanDetail> call = api.getHistoriPesanNbyID(token, id);
        call.enqueue(new Callback<mPesanDetail>() {
            @Override
            public void onResponse(@NonNull Call<mPesanDetail> call, @NonNull Response<mPesanDetail> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {

                    judulpesan.setText(response.body().getData().getTitle());
                    Tanggaljudul.setText(response.body().getData().getCreated_at().substring(0,10).replaceAll("-","/"));
                    isijudul.setText(response.body().getData().getMessage());

                } else {

                    Toast.makeText(getApplication(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<mPesanDetail> call, Throwable t) {
                Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}