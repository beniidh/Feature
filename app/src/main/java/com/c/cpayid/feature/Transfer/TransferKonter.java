package com.c.cpayid.feature.Transfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

public class TransferKonter extends BaseActivity implements ModalTransfer.BottomSheetListenerKabupaten{

    Button buttonTransfer;
    EditText konterid,nominalkonter;
    String id;
    TextView jumlahTransferK;

     @SuppressLint("StaticFieldLeak")
     static Activity transfer;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_konter);
        transfer = this;

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Kirim Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        buttonTransfer = findViewById(R.id.buttonTransfer);
        konterid = findViewById(R.id.konterid);
        jumlahTransferK = findViewById(R.id.jumlahTransferK);
        nominalkonter = findViewById(R.id.nominalkirim);
        buttonTransfer.setOnClickListener(view -> {
            if (konterid.getText().toString().isEmpty() || nominalkonter.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),"Konter atau nominal tidak boleh kosong",Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                ModalPinTransfer modalPinTransfer = new ModalPinTransfer();
                bundle.putString("idkonter",getId());
                bundle.putString("nominalkonter",nominalkonter.getText().toString());
                modalPinTransfer.setArguments(bundle);
                modalPinTransfer.show(getSupportFragmentManager(),"transfer");
            }
        });

        nominalkonter.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (nominalkonter.length() > 1) {
                            jumlahTransferK.setText(utils.ConvertRP(nominalkonter.getText().toString()));
                        } else {
                            jumlahTransferK.setText("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        konterid.setOnClickListener(view -> {
            ModalTransfer modalTransfer = new ModalTransfer();
            modalTransfer.show(getSupportFragmentManager(),"transfer");
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(buttonTransfer.getBackground(), "green");
        DrawableMap.changeColorStroke(buttonTransfer.getBackground(), "green");
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
    public void onButtonClickKabupaten(String name, String id) {
        konterid.setText(name);
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}