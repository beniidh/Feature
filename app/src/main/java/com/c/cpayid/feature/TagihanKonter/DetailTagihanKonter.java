package com.c.cpayid.feature.TagihanKonter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTagihanKonter extends BaseActivity {
    TextView DetailTagihanKonterNominal, DetailTagihanKonterKeterangan;
    Button ButtonSudahBayar,ButtonTolak;
    static Activity detailtagihan;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tagihan_konter);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tagihan Konter<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));
        detailtagihan = this;

        DetailTagihanKonterNominal = findViewById(R.id.DetailTagihanKonterNominal);
        DetailTagihanKonterKeterangan = findViewById(R.id.DetailTagihanKonterKeterangan);
        ButtonSudahBayar = findViewById(R.id.ButtonSudahBayar);
        ButtonTolak = findViewById(R.id.ButtonTolak);

        String tanggal = getIntent().getStringExtra("tanggaltagihan");
        String namakonter = getIntent().getStringExtra("namakonter");
        String idtagihan = getIntent().getStringExtra("idtagihan");
        String tagihan = getIntent().getStringExtra("tagihan");
        DetailTagihanKonterNominal.setText(utils.ConvertRP(tagihan));

        DetailTagihanKonterKeterangan.setText("Tagihan konter " + Html.fromHtml(bold(namakonter)) + " jatuh tempo pada tanggal " + tanggal);

        ButtonSudahBayar.setOnClickListener(v -> {
            ModalPinTagihanKonter modalPinTagihanKonter = new ModalPinTagihanKonter();
            Bundle bundle = new Bundle();
            bundle.putString("idtagihan", idtagihan);
            bundle.putString("aksi","APPROVED");
            modalPinTagihanKonter.setArguments(bundle);
            modalPinTagihanKonter.show(getSupportFragmentManager(), "Tagihan");
        });

        ButtonTolak.setOnClickListener(v -> {
            ModalPinTagihanKonter modalPinTagihanKonter = new ModalPinTagihanKonter();
            Bundle bundle = new Bundle();
            bundle.putString("idtagihan", idtagihan);
            bundle.putString("aksi","REJECT");
            modalPinTagihanKonter.setArguments(bundle);
            modalPinTagihanKonter.show(getSupportFragmentManager(), "Tagihan");
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ButtonSudahBayar.setBackgroundColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvTitleHistory)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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

    public static String bold(String text) {
        return new StringBuffer().append("<b>").append(text).append("</b>").toString();
    }

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}