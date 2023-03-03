package com.c.cpayid.feature.Notifikasi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

public class DetailNotifikasi extends BaseActivity {
    TextView statusND, tanggalND, waktuND, transaksiND, SaldoND, ProdukND, notujuanND, TotalND;
    ImageView iconND;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notifikasi);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Detail Notifikasi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        tanggalND = findViewById(R.id.tanggalND);
        waktuND = findViewById(R.id.waktuND);
        transaksiND = findViewById(R.id.transaksiND);
        SaldoND = findViewById(R.id.SaldoND);
        statusND = findViewById(R.id.StatusND);
        iconND = findViewById(R.id.iconND);

        ProdukND = findViewById(R.id.ProdukND);
        notujuanND = findViewById(R.id.notujuanND);

        tanggalND.setText(getIntent().getStringExtra("tanggal"));
        waktuND.setText(getIntent().getStringExtra("waktu"));
        transaksiND.setText(getIntent().getStringExtra("transaksid"));
        SaldoND.setText(utils.ConvertRP(getIntent().getStringExtra("saldo")));
        statusND.setText(getIntent().getStringExtra("status"));
        notujuanND.setText(getIntent().getStringExtra("no"));
        ProdukND.setText(getIntent().getStringExtra("produk"));

        if (getIntent().getStringExtra("status").equals("PENDING")) {
            iconND.setBackground(getDrawable(R.drawable.ic_baseline_access_time_filled_24));
        } else if (getIntent().getStringExtra("status").equals("GAGAL")) {
            iconND.setBackground(getDrawable(R.drawable.failed));
        }
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        statusND.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        SaldoND.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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