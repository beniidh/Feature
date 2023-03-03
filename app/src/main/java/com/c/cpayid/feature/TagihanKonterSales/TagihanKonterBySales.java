package com.c.cpayid.feature.TagihanKonterSales;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;

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

public class TagihanKonterBySales extends BaseActivity {

    RecyclerView recyclerView;
    AdapterKonterTagihan adapterKonterTagihan;
    ArrayList<ResponTagihanKonterSales.mData> mData = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_user_pay_later);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tagihan Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        recyclerView = findViewById(R.id.ReyTagihanKonterSales);
        adapterKonterTagihan = new AdapterKonterTagihan(getApplicationContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterKonterTagihan);
        getTagihanKonter();
    }

    public void getTagihanKonter(){
        Api api = RetroClient.getApiServices();
        Call<ResponTagihanKonterSales> call = api.getTagihanSalesKonter("Bearer "+ Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponTagihanKonterSales>() {
            @Override
            public void onResponse(@NonNull Call<ResponTagihanKonterSales> call, @NonNull Response<ResponTagihanKonterSales> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mData = response.body().getData();
                    adapterKonterTagihan = new AdapterKonterTagihan(getApplicationContext(), mData);
                    recyclerView.setAdapter(adapterKonterTagihan);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponTagihanKonterSales> call, @NonNull Throwable t) {

            }
        });
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