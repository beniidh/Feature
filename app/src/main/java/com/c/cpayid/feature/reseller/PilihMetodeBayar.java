package com.c.cpayid.feature.reseller;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihMetodeBayar extends BaseActivity {

    ImageView icon;
    Button konfirm;
    String saldo;
    int konfirmasi = 0;
    RelativeLayout server, saldoku;
    TextView saldokuket1, saldokuket2, saldoserverket1, saldoserverket2;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_metode_bayar);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Metode Approve <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        getContentProfile();

        Intent intent = getIntent();
        String totalharga = intent.getStringExtra("hargatotal");
        String iconn = intent.getStringExtra("urll");
        String id = intent.getStringExtra("ID");
        String harga = intent.getStringExtra("Harga");

        saldokuket1 = findViewById(R.id.saldokuket1);
        saldokuket2 = findViewById(R.id.saldokuket2);

        saldoserverket1 = findViewById(R.id.saldoserverket1);
        saldoserverket2 = findViewById(R.id.saldoserverket2);

        TextView pembayarankonfirmasi = findViewById(R.id.pemayarankonfirasi);
        pembayarankonfirmasi.setText(utils.ConvertRP(harga));
        icon = findViewById(R.id.iconkonfirmasi);
        Picasso.get().load(iconn).into(icon);
        konfirm = findViewById(R.id.konfirmbayar);

        server = findViewById(R.id.LinearSaldoServer);
        saldoku = findViewById(R.id.LinearSaldoku);

        server.setOnClickListener(v -> {
            saldo = "PAYLATTER";
            konfirmasi = 1;
            server.setBackground(getDrawable(R.drawable.bg_search_otppin));
            saldoku.setBackground(getDrawable(R.drawable.bg_edittextlogin));
            saldoserverket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldoserverket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldokuket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
            saldokuket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        });

        saldoku.setOnClickListener(v -> {
            saldo = "SALDOKU";
            konfirmasi = 1;
            saldoku.setBackground(getDrawable(R.drawable.bg_search_otppin));
            server.setBackground(getDrawable(R.drawable.bg_edittextlogin));
            saldokuket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldokuket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldoserverket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
            saldoserverket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        });

        konfirm.setOnClickListener(v -> {
            if (saldo  != null) {
                GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
                String token = "Bearer " + Preference.getToken(getApplicationContext());
                mSetujuSaldo mSetuju = new mSetujuSaldo(id, "APPROVE", saldo,
                        Value.getMacAddress(getApplicationContext()), Value.getIPaddress(),
                        Value.getUserAgent(getApplicationContext()),
                        gpsTracker.getLongitude(),
                        gpsTracker.getLatitude());

                Api api = RetroClient.getApiServices();
                Call<ResponApproveSaldoR> call = api.ApproveSaldokuReselesser(token, mSetuju);
                call.enqueue(new Callback<ResponApproveSaldoR>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponApproveSaldoR> call, @NonNull Response<ResponApproveSaldoR> response) {
                        assert response.body() != null;
                        String code = response.body().getCode();
                        if (code.equals("200")) {
                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponApproveSaldoR> call, @NonNull Throwable t) {

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Metode tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(konfirm.getBackground(), "green");
        DrawableMap.changeColorStroke(konfirm.getBackground(), "green");
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

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                if (response.body().getCode().equals("200")) {
                    saldokuket2.setText(utils.ConvertRP(response.body().getData().getWallet().getSaldoku()));
                    saldoserverket2.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylatter()));
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}