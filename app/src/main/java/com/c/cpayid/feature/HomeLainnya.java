package com.c.cpayid.feature;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.cpayid.feature.Adapter.AdapterMenuUtamaLain;
import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.ModelMenuUtama;
import com.c.cpayid.feature.Respon.ResponMenuUtama;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.facebook.shimmer.ShimmerFrameLayout;
import io.github.muddz.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeLainnya extends BaseActivity {

    AdapterMenuUtamaLain adapterMenuUtamalain;
    RecyclerView reymenulain;
    ArrayList<ModelMenuUtama> menuUtamas = new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipelainnya;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelainnya);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Lainnya <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        getAllMenu();
        reymenulain = findViewById(R.id.reyLainnya);

        int numberOfColumns = 5;
        reymenulain.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapterMenuUtamalain = new AdapterMenuUtamaLain(getApplicationContext(), menuUtamas);
//        reymenulain.setAdapter(adapterMenuUtama);

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();

        swipelainnya = findViewById(R.id.swipelainnya);
        swipelainnya.setOnRefreshListener(() -> {
            getAllMenu();
            swipelainnya.setRefreshing(false);
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

    public void getAllMenu() {
        Api api = RetroClient.getApiServices();
        Call<ResponMenuUtama> call = api.getAllMenu2("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponMenuUtama>() {
            @Override
            public void onResponse(@NonNull Call<ResponMenuUtama> call, @NonNull Response<ResponMenuUtama> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    menuUtamas = (ArrayList<ModelMenuUtama>) response.body().getData();
                    adapterMenuUtamalain = new AdapterMenuUtamaLain(getApplicationContext(), menuUtamas);
                    reymenulain.setAdapter(adapterMenuUtamalain);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponMenuUtama> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}