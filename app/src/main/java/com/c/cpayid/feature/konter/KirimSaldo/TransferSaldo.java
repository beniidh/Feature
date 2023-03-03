package com.c.cpayid.feature.konter.KirimSaldo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.NumberChageLive;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

public class TransferSaldo extends BaseActivity {

    EditText nominalkirimSaldo;
    Button buttonTransferSaldo;

    @SuppressLint("StaticFieldLeak")
    static Activity transfersaldo;
    TextView namakirim;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_saldo);
        transfersaldo = this;

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Kirim Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        nominalkirimSaldo = findViewById(R.id.nominalkirimSaldo);
        namakirim = findViewById(R.id.namakirim);
        buttonTransferSaldo = findViewById(R.id.buttonTransferSaldo);
        String id = getIntent().getStringExtra("id");

        namakirim.setText("Kirim saldo ke : " + getIntent().getStringExtra("namakonter"));
        nominalkirimSaldo.addTextChangedListener(new NumberChageLive(nominalkirimSaldo));

        buttonTransferSaldo.setOnClickListener(view -> {

            if (nominalkirimSaldo.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Konter atau nominal tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                ModalPinTransfers modalPinTransfer = new ModalPinTransfers();
                bundle.putString("idkonter", id);
                bundle.putString("nominalkonter", nominalkirimSaldo.getText().toString().replaceAll(",", ""));
                modalPinTransfer.setArguments(bundle);
                modalPinTransfer.show(getSupportFragmentManager(), "transfer");
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(buttonTransferSaldo.getBackground(), "green");
        DrawableMap.changeColorStroke(buttonTransferSaldo.getBackground(), "green");
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