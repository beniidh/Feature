package com.c.cpayid.feature.konter.DrawSaldo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.NumberChageLive;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

public class DrawMyBalance extends BaseActivity {

    EditText nominaltarikSaldo;
    Button buttonTarikSaldo;
    TextView namatarik;

    @SuppressLint("StaticFieldLeak")
    static Activity drawsaldo;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawsaldoku);
        drawsaldo = this;

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Tarik Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        nominaltarikSaldo = findViewById(R.id.nominaltarikSaldo);
        namatarik = findViewById(R.id.namatarik);
        namatarik.setText("Tarik saldo : " + getIntent().getStringExtra("namakonter"));
        buttonTarikSaldo = findViewById(R.id.buttonTarikSaldo);
        nominaltarikSaldo.addTextChangedListener(new NumberChageLive(nominaltarikSaldo));

        buttonTarikSaldo.setOnClickListener(v -> {
            if (nominaltarikSaldo.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),"Nominal tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                modalPinDraw modalPinDraw = new modalPinDraw();
                bundle.putString("nominal",nominaltarikSaldo.getText().toString().replaceAll(",",""));
                bundle.putString("id",getIntent().getStringExtra("id"));
                modalPinDraw.setArguments(bundle);
                modalPinDraw.show(getSupportFragmentManager(),"Modal draw");
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(buttonTarikSaldo.getBackground(), "green");
        DrawableMap.changeColorStroke(buttonTarikSaldo.getBackground(), "green");
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