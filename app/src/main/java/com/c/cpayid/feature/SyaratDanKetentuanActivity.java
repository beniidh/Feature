package com.c.cpayid.feature;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

public class SyaratDanKetentuanActivity extends BaseActivity {

    CheckBox setuju;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syaratdanketentuan_activity);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Syarat & Ketentuan <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        setuju = findViewById(R.id.checksetuju);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void settingLayout() {
        super.settingLayout();

        String applicationName = ApplicationVariable.nameChanger();
        ((TextView) findViewById(R.id.tvSyaratKetentuan)).setText(getResources().getString(R.string.syaratketentuan, applicationName, applicationName, applicationName, applicationName, applicationName, applicationName));
        ((TextView) findViewById(R.id.tvHeader)).setText(getResources().getString(R.string.selamat_datang_di_dompet_abata, applicationName));

        Button setujuu = findViewById(R.id.setujuu);
        DrawableMap.changeColor(setujuu.getBackground(), "green");
        DrawableMap.changeColorStroke(setujuu.getBackground(), "green");
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

    public void SendOTP(View view){
        Intent intent = new Intent(SyaratDanKetentuanActivity.this, InsertPinActivity.class);
        startActivity(intent);
    }
}