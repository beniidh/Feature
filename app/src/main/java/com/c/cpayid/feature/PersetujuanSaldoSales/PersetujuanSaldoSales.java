package com.c.cpayid.feature.PersetujuanSaldoSales;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersetujuanSaldoSales extends BaseActivity {

    RecyclerView recyclerView;

    AdapterPersetujuanSaldoServer adapterPersetujuanSaldoServer;
    ArrayList<ModelPersetujuanSaldo> modelPersetujuanSaldos = new ArrayList<>();
    TextView nominal, tanggal, status;
    LinearLayout LinearKlikPersetujuanSaldo;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan_saldo_sales);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Persetujuan Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        recyclerView = findViewById(R.id.ReyPersetujuanSaldoServer);
        adapterPersetujuanSaldoServer = new AdapterPersetujuanSaldoServer(getApplicationContext(), modelPersetujuanSaldos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterPersetujuanSaldoServer);

        getDataPayletter();
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

    public void getDataPayletter(){
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponPersetujuanSaldo> call = api.getDataAprroval(token);
        call.enqueue(new Callback<ResponPersetujuanSaldo>() {
            @Override
            public void onResponse(@NonNull Call<ResponPersetujuanSaldo> call, @NonNull Response<ResponPersetujuanSaldo> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")){
                    modelPersetujuanSaldos = response.body().getData();
                    adapterPersetujuanSaldoServer = new AdapterPersetujuanSaldoServer(getApplicationContext(), modelPersetujuanSaldos);
                    recyclerView.setAdapter(adapterPersetujuanSaldoServer);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponPersetujuanSaldo> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataPayletter();
    }
}