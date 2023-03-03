package com.c.cpayid.feature;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.c.cpayid.feature.Modal.OtpInsert;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

public class PendingActivity extends BaseActivity {

    Button lanjut, keluar;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Register<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        lanjut = findViewById(R.id.LanjutRegister);
        keluar = findViewById(R.id.KeluarRegister);

        lanjut.setOnClickListener(v -> {
            String code = Preference.getTrackRegister(getApplicationContext());
            switch (code) {
                case "1": {
                    Intent intent = new Intent(PendingActivity.this, OtpSend.class);
                    startActivity(intent);

                    break;
                }
                case "2": {
                    Intent intent = new Intent(PendingActivity.this, OtpInsert.class);
                    startActivity(intent);
                    break;
                }
                case "3": {
                    Intent intent = new Intent(PendingActivity.this, RegisterFotoActivity.class);
                    startActivity(intent);
                    break;
                }
                case "4": {
                    Intent intent = new Intent(PendingActivity.this, SyaratDanKetentuanActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        });

        keluar.setOnClickListener(v -> {
            Preference.setTrackRegister(getApplicationContext(), "");
            Intent intent = new Intent(PendingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvDesc)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(lanjut.getBackground(), "green");
        DrawableMap.changeColorStroke(lanjut.getBackground(), "green");
        DrawableMap.changeColor(keluar.getBackground(), "green2");
        DrawableMap.changeColorStroke(keluar.getBackground(), "green");
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