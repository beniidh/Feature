package com.c.cpayid.feature.SaldoServer;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjukanLimit extends BaseActivity {

    Button idAjukanLimitServerButton;
    EditText idPengajuanLimitServerEditText;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukan_limit);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pengajuan Limit <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        idAjukanLimitServerButton = findViewById(R.id.idAjukanLimitServerButton);
        idPengajuanLimitServerEditText = findViewById(R.id.idPengajuanLimitServerEditText);
        getStatusPayLatter();

        idAjukanLimitServerButton.setOnClickListener(v -> {
            if (idPengajuanLimitServerEditText.getText().toString().equals("")) {
                StyleableToast.makeText(getApplicationContext(), "Limit tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("saldo", idPengajuanLimitServerEditText.getText().toString());
                ModalPinPengajuanServer modalPinPengajuanServer = new ModalPinPengajuanServer(AjukanLimit.this);
                modalPinPengajuanServer.setArguments(bundle);
                modalPinPengajuanServer.show(getSupportFragmentManager(), "Pin Server");
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvTitle)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(idAjukanLimitServerButton.getBackground(), "green");
        DrawableMap.changeColorStroke(idAjukanLimitServerButton.getBackground(), "green");
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

    public void openDialog() {
        PopupPengajuan popupPengajuan = new PopupPengajuan();
        popupPengajuan.show(getSupportFragmentManager(), "PopUp dialog");
    }

    public void getStatusPayLatter() {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Responn> call = api.GetPayLetter(token);
        call.enqueue(new Callback<Responn>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Responn> call, @NonNull Response<Responn> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    String status = response.body().getData().get(0).getStatus();

                    if(status.equals("PENDING SALES")){
                        idAjukanLimitServerButton.setText("Menunggu Persetujuan");
                        idAjukanLimitServerButton.setEnabled(false);
                        openDialog();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<Responn> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}