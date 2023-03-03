package com.c.cpayid.feature.TransferBank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.NumberChageLive;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferBank extends BaseActivity implements ModalNamaBank.BottomSheetListener {

    EditText pilihBank, Norekening, Nominal;
    Button KonfirmasiTransfer, transfer;
    TextView BnamaCustomer, Bhargadasar, Bhargaadmin, BhargaTotal, BhargaTotal2, BkodeBank, Brefid, Nominal2, KeteranganBank;
    LinearLayout LinearTransferBank;
    View lottieAnimasiBank;
    String codesub;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_bank);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Transfer Bank<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        KonfirmasiTransfer = findViewById(R.id.KonfirmasiTransfer);
        transfer = findViewById(R.id.TransferTransfer);
        BkodeBank = findViewById(R.id.BkodeBank);
        KeteranganBank = findViewById(R.id.KeteranganBank);
        LinearTransferBank = findViewById(R.id.LinearTransferBank);
        lottieAnimasiBank = findViewById(R.id.lottieAnimasiBank);

//        String id = getIntent().getStringExtra("id");
//        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();

        Norekening = findViewById(R.id.BNomorRekening);
        Nominal = findViewById(R.id.Bnominal);
        Nominal2 = findViewById(R.id.Bnominal2);

        Brefid = findViewById(R.id.BRefid);
        BnamaCustomer = findViewById(R.id.BnamaCustomer);
        Bhargadasar = findViewById(R.id.Bhargadasar);
        Bhargaadmin = findViewById(R.id.Bhargaadmin);
        BhargaTotal = findViewById(R.id.BhargaTotal);
        BhargaTotal2 = findViewById(R.id.BhargaTotal2);

        KonfirmasiTransfer.setOnClickListener(view -> {
            if (BkodeBank.getText().toString().isEmpty() || Norekening.getText().toString().isEmpty() || Nominal.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Bank / Nominal / Rekening tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Inquiry(BkodeBank.getText().toString(), Norekening.getText().toString());
            }
        });

        Nominal.addTextChangedListener( new NumberChageLive(Nominal));

        pilihBank = findViewById(R.id.pilihBank);
        pilihBank.setOnClickListener(view -> {
            String idd = getIntent().getStringExtra("id");

            Bundle bundle = new Bundle();
            ModalNamaBank modalNamaBank = new ModalNamaBank();
            bundle.putString("id", idd);
            modalNamaBank.setArguments(bundle);
            modalNamaBank.show(getSupportFragmentManager(), "modal nama bank");

        });

        transfer.setOnClickListener(view -> {
            Intent intent = new Intent(TransferBank.this, MetodeBayarBank.class);
            intent.putExtra("sku_code", BkodeBank.getText().toString());
            intent.putExtra("customer_no", Norekening.getText().toString());
            intent.putExtra("Amount", BhargaTotal2.getText().toString());
            intent.putExtra("RefID", Brefid.getText().toString());
            startActivity(intent);
        });

    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColor(KonfirmasiTransfer.getBackground(), "green");
        DrawableMap.changeColorStroke(KonfirmasiTransfer.getBackground(), "green");
        DrawableMap.changeColor(transfer.getBackground(), "green2");
        DrawableMap.changeColor(transfer.getBackground(), "green2");
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

    private void Inquiry(String kodebank, String noCustomer) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(TransferBank.this);
        loadingPrimer.startDialogLoading();
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        MinquiryBank minquiryBank = new MinquiryBank(kodebank, Integer.parseInt(Nominal.getText().toString().replaceAll(",","")), noCustomer, "TRANSFER",
                Value.getMacAddress(getApplicationContext()), Value.getIPaddress(),
                Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());

        Api api = RetroClient.getApiServices();
        Call<ResponInquiryBank> call = api.getInquiryBank("Bearer " + Preference.getToken(getApplicationContext()), minquiryBank);
        call.enqueue(new Callback<ResponInquiryBank>() {
            @Override
            public void onResponse(@NonNull Call<ResponInquiryBank> call, @NonNull Response<ResponInquiryBank> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {

                    Brefid.setText(response.body().getData().getRef_id());
                    KeteranganBank.setText(response.body().getData().getDescription());
                    loadingPrimer.dismissDialog();
                    if (response.body().getData().getStatus().equals("Gagal")) {
                        transfer.setVisibility(View.GONE);
                    } else {
                        BnamaCustomer.setText(response.body().getData().getCustomer_name());
                        Bhargadasar.setText(utils.ConvertRP(response.body().getData().getBasic_price()));
                        Bhargaadmin.setText(utils.ConvertRP(response.body().getData().getAdmin_fee()));
                        BhargaTotal.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
                        BhargaTotal2.setText(response.body().getData().getBasic_price());
                        transfer.setVisibility(View.VISIBLE);
                    }

                    LinearTransferBank.setVisibility(View.VISIBLE);
                    lottieAnimasiBank.setVisibility(View.GONE);
                } else {
                    loadingPrimer.dismissDialog();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponInquiryBank> call, @NonNull Throwable t) {
                loadingPrimer.dismissDialog();
                Toast.makeText(getApplicationContext(), "Periksa sambungan internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onButtonClick(String name, String id) {
        pilihBank.setText(name);
        BkodeBank.setText(id);
    }

    public String getCodesub() {
        return codesub;
    }

    public void setCodesub(String codesub) {
        this.codesub = codesub;
    }
}