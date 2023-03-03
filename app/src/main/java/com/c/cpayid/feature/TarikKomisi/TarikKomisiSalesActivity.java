package com.c.cpayid.feature.TarikKomisi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.TopUpSaldoku.NumberChageLive;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarikKomisiSalesActivity extends BaseActivity implements ModalTarikKomisi.callback {

    EditText jumlahWd;
    TextView jumlahKomisi;
    Button ButtonWd;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarik_komisi_sales);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tarik Komisi<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        jumlahWd = findViewById(R.id.jumlahWd);
        jumlahKomisi = findViewById(R.id.jumlahKomisi);
        ButtonWd = findViewById(R.id.ButtonWd);
        getContentProfile();
        jumlahWd.addTextChangedListener(new NumberChageLive(jumlahWd));

        ButtonWd.setOnClickListener(v -> {
            if (jumlahWd.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Jumlah tidak boleh kosong",Toast.LENGTH_SHORT).show();
            } else {
                ModalTarikKomisi modalTarikKomisi = new ModalTarikKomisi();
                Bundle bundle = new Bundle();
                bundle.putString("jumlah",jumlahWd.getText().toString().replaceAll(",",""));
                modalTarikKomisi.setArguments(bundle);
                modalTarikKomisi.show(getSupportFragmentManager(),"PIN");
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(ButtonWd.getBackground(), "green");
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
                    jumlahKomisi.setText(utils.ConvertRP(response.body().getData().getWallet().getKomisi()));
                } else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    @Override
    public void onClick(String hasil) {
        getContentProfile();
        jumlahWd.setText("");
    }
}