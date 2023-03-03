package com.c.cpayid.feature.Inquiry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.LoginActivity;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

public class InquiryPasca extends BaseActivity {

    private Button btnPay;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_pasca);

        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#4AB84E'><b>Transaksi<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        btnPay = findViewById(R.id.btnPay);
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColor(btnPay.getBackground(), "green");
        DrawableMap.changeColorStroke(btnPay.getBackground(), "green");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}