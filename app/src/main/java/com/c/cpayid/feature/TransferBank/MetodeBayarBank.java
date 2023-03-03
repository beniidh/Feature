package com.c.cpayid.feature.TransferBank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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

public class MetodeBayarBank extends BaseActivity {

    ImageView icon;
    Button konfirm;
    String saldo;
    int konfirmasi = 0;
    RelativeLayout server, saldoku;
    TextView saldokuket1, saldokuket2, saldoserverket1, saldoserverket2;
    static Activity metodebayar;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodebayar_bank);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Metode Bayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        getContentProfile();
        saldokuket1 = findViewById(R.id.saldokuket1);
        saldokuket2 = findViewById(R.id.saldokuket2);
        metodebayar=this;

        saldoserverket1 = findViewById(R.id.saldoserverket1);
        saldoserverket2 = findViewById(R.id.saldoserverket2);

        TextView pembayarankonfirmasi = findViewById(R.id.pemayarankonfirasi);
        pembayarankonfirmasi.setText(utils.ConvertRP(getIntent().getStringExtra("Amount")));
        icon = findViewById(R.id.iconkonfirmasi);
        konfirm = findViewById(R.id.konfirmbayar);

        server = findViewById(R.id.LinearSaldoServer);
        saldoku = findViewById(R.id.LinearSaldoku);

        server.setOnClickListener(v -> {
            saldo = "PAYLATTER";
            konfirmasi = 1;
            server.setBackground(getDrawable(R.drawable.bg_search_otppin));
            saldoku.setBackground(getDrawable(R.drawable.bg_edittextlogin));
            saldoserverket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldoserverket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldokuket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
            saldokuket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        });

        saldoku.setOnClickListener(v -> {
            saldo = "SALDOKU";
            konfirmasi = 1;
            saldoku.setBackground(getDrawable(R.drawable.bg_search_otppin));
            server.setBackground(getDrawable(R.drawable.bg_edittextlogin));
            saldokuket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldokuket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            saldoserverket1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
            saldoserverket2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        });

        konfirm.setOnClickListener(v -> {
            if (konfirmasi == 0) {
                StyleableToast.makeText(getApplicationContext(), "Pilih Metode Pembayaran", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            } else {
                Intent intent = getIntent();
                String scu = intent.getStringExtra("sku_code");
                String nomor = intent.getStringExtra("customer_no");
                String jumlah = intent.getStringExtra("Amount");
                String refid = intent.getStringExtra("RefID");

                ModalPinTransferBank modalPinTransferBank = new ModalPinTransferBank();
                Bundle bundle = new Bundle();
                bundle.putString("wallettype", saldo);
                bundle.putString("sku_code",scu);
                bundle.putString("customer_no",nomor);
                bundle.putString("Amount",jumlah);
                bundle.putString("RefID",refid);
                modalPinTransferBank.setArguments(bundle);
                modalPinTransferBank.show(getSupportFragmentManager(), "Transaksi");
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColor(konfirm.getBackground(), "green");
        DrawableMap.changeColorStroke(konfirm.getBackground(), "green");
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

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                if (response.body().getCode().equals("200")) {
                    saldokuket2.setText(utils.ConvertRP(response.body().getData().getWallet().getSaldoku()));
                    saldoserverket2.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylatter()));
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Periksa sambungan internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}