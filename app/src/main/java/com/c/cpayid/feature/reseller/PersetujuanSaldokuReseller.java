package com.c.cpayid.feature.reseller;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

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

public class PersetujuanSaldokuReseller extends BaseActivity implements ModalApprove.BottomSheetListener {

    RecyclerView recyclerView;
    AdapterSaldoReseller adapterSaldoReseller;
    ArrayList<ResponSaldoReseller.Data> data = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan_saldoku_reseller);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Saldoku Reseller <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));
        recyclerView = findViewById(R.id.ReySaldoReseller);

        adapterSaldoReseller = new AdapterSaldoReseller(getApplicationContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterSaldoReseller);

        getData();
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

    private void getData() {
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSaldoReseller> call = api.getSaldoReseller(token);
        call.enqueue(new Callback<ResponSaldoReseller>() {
            @Override
            public void onResponse(@NonNull Call<ResponSaldoReseller> call, @NonNull Response<ResponSaldoReseller> response) {
                assert response.body() != null;
                if (response.body().getCode().equals("200")) {
                    data = response.body().getData();
                    if (!data.isEmpty()) {
                        adapterSaldoReseller = new AdapterSaldoReseller(getApplicationContext(), data);
                        recyclerView.setAdapter(adapterSaldoReseller);
                    } else {
                        Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponSaldoReseller> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onButtonClick() {
        getData();
    }
}