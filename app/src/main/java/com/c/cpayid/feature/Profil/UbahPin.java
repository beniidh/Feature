package com.c.cpayid.feature.Profil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.TextView;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Modal.ModalPinBaru;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.oakkub.android.PinEditText;

import java.util.Objects;

public class UbahPin extends BaseActivity {

    PinEditText ubahpin;
    GpsTracker gpsTracker;
    public static Activity pin;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pin);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Ubah PIN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        pin = this;
        ubahpin = findViewById(R.id.ubahpin);
        ubahpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ubahpin.length() == 6){
                    Bundle bundle = new Bundle();
                    ModalPinBaru modalPinBaru = new ModalPinBaru();
                    bundle.putString("PINedit", Objects.requireNonNull(ubahpin.getText()).toString());
                    modalPinBaru.setArguments(bundle);
                    modalPinBaru.show(getSupportFragmentManager(),"pin baru");
                }
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        ((TextView) findViewById(R.id.tvNewPinLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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