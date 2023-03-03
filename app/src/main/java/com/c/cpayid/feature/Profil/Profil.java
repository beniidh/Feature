package com.c.cpayid.feature.Profil;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Profil.Poin.Point;
import com.c.cpayid.feature.ResetPIN;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.SaldoServer.AjukanLimit;
import com.c.cpayid.feature.SaldoServer.TopupSaldoServer;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.c.cpayid.feature.TopUpSaldoku.TopupSaldokuActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil extends BaseActivity {
    TextView namaprofil, phone,saldokuprofil,saldoserverprofil;
    ImageView iconprofile;
    TextView Pubahpin,Pubahprofil,Ppoint,Pdevice;
    LinearLayout KlikSaldoku,kliksaldoserver;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Profil <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        namaprofil = findViewById(R.id.namaprofil);
        phone = findViewById(R.id.nomorprofil);
        iconprofile = findViewById(R.id.iconprofile);
        saldokuprofil = findViewById(R.id.saldokuprofil);
        saldoserverprofil = findViewById(R.id.saldoserverprofil);

        Pubahpin = findViewById(R.id.PubahPin);
        KlikSaldoku = findViewById(R.id.KlikSaldoku);
        kliksaldoserver = findViewById(R.id.kliksaldoserver);

        Pubahprofil = findViewById(R.id.PubahProfil);
        Ppoint = findViewById(R.id.Ppoint);
        Pdevice = findViewById(R.id.Pdevice);
        Typeface type = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
        Pubahpin.setTypeface(type);
        Pubahprofil.setTypeface(type);
        Ppoint.setTypeface(type);
        Pdevice.setTypeface(type);

        getContentProfile();
        KlikSaldoku.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TopupSaldokuActivity.class);
            intent.putExtra("saldoku",saldokuprofil.getText().toString());
            startActivity(intent);
        });

        kliksaldoserver.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TopupSaldoServer.class);
            startActivity(intent);
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvSaldoServerLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvPoinLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvTotalTransaksiLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvMyBalanceLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColorStroke(findViewById(R.id.llContainerBalance).getBackground(), "green");
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

    @Override
    protected void onResume() {
        super.onResume();
        getContentProfile();
    }

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                namaprofil.setText(response.body().getData().getName());
                phone.setText(response.body().getData().getPhone());
                Picasso.get().load(response.body().getData().getAvatar()).into(iconprofile);
                saldokuprofil.setText(utils.ConvertRP(response.body().getData().getWallet().getSaldoku()));
                saldoserverprofil.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylatter()));
                String statuspayletter = response.body().getData().getPaylater_status();

//                if (statuspayletter.equals("0")) {
//                    saldoserverprofil.setText("0");
//                    saldoserverprofil.setOnClickListener(v -> {
//                        Intent intent = new Intent(getApplicationContext(), AjukanLimit.class);
////                            intent.putExtra("saldoku", saldoku.getText().toString());
//                        startActivity(intent);
//                    });
//                } else {
//                    saldoserverprofil.setOnClickListener(v -> {
//                        Intent intent = new Intent(getApplicationContext(), TopupSaldoServer.class);
////                        intent.putExtra("saldoku", saldoku.getText().toString());
//                        startActivity(intent);
//                    });
//                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {

            }
        });
    }

    public void UbahProfil(View view){
        Intent intent = new Intent(Profil.this, UbahProfil.class);
        startActivity(intent);
    }
    public void UbahPin(View view){
        Intent intent = new Intent(Profil.this, ResetPIN.class);
        startActivity(intent);
    }
    public void point(View view){
        Intent intent = new Intent(Profil.this, Point.class);
        startActivity(intent);
    }

    public void Device(View view){
        Intent intent = new Intent(Profil.this, Device.class);
        startActivity(intent);
    }
}



