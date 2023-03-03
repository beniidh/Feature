package com.c.cpayid.feature.SaldoServer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Modal.ModalMetodePemayaran;
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

public class TopupSaldoServer extends BaseActivity {

    TextView tagihanSaldoServer, tanggalTagihan, TotalSS, PengembalianSS, limitsaldoserverr;
    RelativeLayout LinearBayartagihan;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldo_server);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Top Up Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        tagihanSaldoServer = findViewById(R.id.tagihanSaldoServer);
        LinearBayartagihan = findViewById(R.id.LinearBayartagihan);
        limitsaldoserverr = findViewById(R.id.limitsaldoserverr);

        TotalSS = findViewById(R.id.TotalSS);
        PengembalianSS = findViewById(R.id.PengembalianSS);
        getContentProfile();

        tanggalTagihan = findViewById(R.id.tanggalTagihan);
        getTagihan();
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        ((TextView) findViewById(R.id.tvDetailLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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

    public void RiwayatTagihan(View view) {
        Intent intent = new Intent(TopupSaldoServer.this, RiwayatTagihan.class);
        startActivity(intent);
    }

    public void BayarTagihan(View view) {
        String codebayar = "saldoserver";
        Bundle bundle = new Bundle();
        bundle.putString("saldotipe", codebayar);
        ModalMetodePemayaran modalPembayaran = new ModalMetodePemayaran();
        modalPembayaran.setArguments(bundle);
        modalPembayaran.show(getSupportFragmentManager(), "ModalPebayaran");
    }

    public void getTagihan() {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponTagihanPayLatter> call = api.getTagihan(token);
        call.enqueue(new Callback<ResponTagihanPayLatter>() {
            @Override
            public void onResponse(@NonNull Call<ResponTagihanPayLatter> call, @NonNull Response<ResponTagihanPayLatter> response) {
                assert response.body() != null;
                if (response.body().getCode().equals("200")) {
                    tagihanSaldoServer.setText(utils.ConvertRP(response.body().getData().getTotal_bill()));
                    Preference.setSaldoServer(getApplicationContext(), response.body().getData().getTotal_bill());
                    String tanggal = response.body().getData().getDue_date();
                    tanggalTagihan.setText(tanggal.substring(0, 10));
                    PengembalianSS.setText(utils.ConvertRP(response.body().getData().getTotal_bill()));
                    TotalSS.setText(utils.ConvertRP(response.body().getData().getTotal_bill()));
                    Preference.setIdUPP(getApplicationContext(), response.body().getData().getId());
                } else {
                    LinearBayartagihan.setEnabled(false);
                    Toast.makeText(TopupSaldoServer.this, response.body().getError() + " Belum ada tagihan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponTagihanPayLatter> call, @NonNull Throwable t) {

            }
        });
    }

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                String limit = response.body().getData().getWallet().getPaylater_limit();
                if (limit == null) {
                    limitsaldoserverr.setText(utils.ConvertRP("0"));
                } else {
                    limitsaldoserverr.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylater_limit()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}