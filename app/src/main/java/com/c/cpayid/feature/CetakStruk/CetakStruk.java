package com.c.cpayid.feature.CetakStruk;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.LoadingPrimer;
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

public class CetakStruk extends BaseActivity {

    EditText idCetakStrukEditText;
    RecyclerView recyclerView;
    AdapterCetakStruk adapterCetakStruk;
    SearchView idSearchStruk;
    ArrayList<ResponStruk.DataTransaksi> mdata = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetakstruk);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak Ulang Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        idSearchStruk = findViewById(R.id.idSearchStruk);
        recyclerView = findViewById(R.id.idRecycleCetakStruk);

        adapterCetakStruk = new AdapterCetakStruk(getApplicationContext(), mdata);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterCetakStruk);
        getDataHistory();

        idSearchStruk.setOnClickListener(v -> idSearchStruk.onActionViewExpanded());
        idSearchStruk.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCetakStruk.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorStroke(findViewById(R.id.llSearch).getBackground(), "green");
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

    public void getCetakDetail(View view) {
        Intent intent = new Intent(CetakStruk.this, DetailTransaksiTruk.class);
        startActivity(intent);
    }

    public void getDataHistory() {
        LoadingPrimer loadingPrimer = new LoadingPrimer(CetakStruk.this);
        loadingPrimer.startDialogLoading();
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponStruk> call = api.getHistoriStruk(token);
        call.enqueue(new Callback<ResponStruk>() {
            @Override
            public void onResponse(@NonNull Call<ResponStruk> call, @NonNull Response<ResponStruk> response) {
                ArrayList<ResponStruk.DataTransaksi> mdataa = new ArrayList<>();
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mdata = response.body().getData();
                    for (ResponStruk.DataTransaksi data : mdata) {
                        if (data.getStatus().equals("SUKSES")) {
                            mdataa.add(data);
                        }
                    }

                    adapterCetakStruk = new AdapterCetakStruk(getApplicationContext(), mdataa);
                    recyclerView.setAdapter(adapterCetakStruk);
                    loadingPrimer.dismissDialog();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                    loadingPrimer.dismissDialog();
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponStruk> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}