package com.c.cpayid.feature.konter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.konter.DrawSaldo.DrawMyBalance;
import com.c.cpayid.feature.konter.KirimSaldo.TransferSaldo;
import com.c.cpayid.feature.konter.MarkupKonter.MarkupKonter;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonterActivity extends BaseActivity {

    RecyclerView recyclerView;
    ArrayList<Mkonter.mData> data = new ArrayList<>();
    AdapterKonter adapterKonter;
    FloatingActionButton floatButtonAddKonter;
    SearchView Search_konter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konter);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));
        getKonter();

        Search_konter = findViewById(R.id.Search_konter);
        recyclerView = findViewById(R.id.ReyListKonter);
        floatButtonAddKonter = findViewById(R.id.floatButtonAddKonter);
        floatButtonAddKonter.setOnClickListener(v -> {
            Intent intent = new Intent(KonterActivity.this, TambahKonter.class);
            startActivity(intent);
        });

        adapterKonter = new AdapterKonter(getApplicationContext(), data, KonterActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterKonter);

        Search_konter.setOnClickListener(v -> Search_konter.onActionViewExpanded());
        Search_konter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterKonter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        floatButtonAddKonter.setBackgroundTintList(ColorStateList.valueOf(ColorMap.getColor(ApplicationVariable.applicationId, "green")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getKonter();
    }

    public void getKonter(){
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Mkonter> call = api.getKonterSales(token);
        call.enqueue(new Callback<Mkonter>() {
            @Override
            public void onResponse(@NonNull Call<Mkonter> call, @NonNull Response<Mkonter> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if(code.equals("200")){

                    data = response.body().getData();
                    if(data == null){
                        Toast.makeText(getApplicationContext(),"Data tidak ditemukan",Toast.LENGTH_SHORT).show();
                    } else{
                        adapterKonter = new AdapterKonter(getApplicationContext(), data, KonterActivity.this);
                        recyclerView.setAdapter(adapterKonter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Mkonter> call, @NonNull Throwable t) {

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

    @SuppressLint("NonConstantResourceId")
    public void TunjukkanKeberadaanPopupMenu(View view, String id, String nama) {
        PopupMenu pop = new PopupMenu(this, view);
        pop.inflate(R.menu.menukonter);
        pop.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.kirimsaldo:
                    /*menangani saat Share di klik*/
                    Intent intent = new Intent(KonterActivity.this, TransferSaldo.class);
                    intent.putExtra("id", id);
                    intent.putExtra("namakonter",nama);
                    startActivity(intent);
                    break;
                case R.id.markup:
                    /*menangani saat Feedback di klik*/
                    Intent intent2 = new Intent(KonterActivity.this, MarkupKonter.class);
                    intent2.putExtra("namakonter",nama);
                    intent2.putExtra("id", id);
                    startActivity(intent2);
                    break;
                case R.id.tariksaldo:
                    /*menangani saat Feedback di klik*/
                    Intent intent3 = new Intent(KonterActivity.this, DrawMyBalance.class);
                    intent3.putExtra("namakonter",nama);
                    intent3.putExtra("id", id);
                    startActivity(intent3);
                    break;
            }
            return true;
        });
        pop.show();
    }
}