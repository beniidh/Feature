package com.c.cpayid.feature.Fragment.RekapSaldo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapSaldoActivity extends BaseActivity {
    EditText tanggalstartR, tanggalendR;
    Button ButtonfilterR;
    RecyclerView recyclerView;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar3 = Calendar.getInstance();
    AdapterRekapSaldo adapterRekapSaldo;
    ArrayList<responRekap.Data.Item> data = new ArrayList<>();
    Spinner spinner;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_saldo);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Rekap Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        tanggalstartR = findViewById(R.id.tanggalstartR);
        tanggalendR =  findViewById(R.id.tanggalendR);
        ButtonfilterR =  findViewById(R.id.ButtonfilterR);
        recyclerView = findViewById(R.id.reyRekapSaldo);
        spinner = findViewById(R.id.pilihType);

        ButtonfilterR.setOnClickListener(view -> {
            if (tanggalstartR.getText().toString().isEmpty() || tanggalendR.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                getDataRekap(tanggalstartR.getText().toString(), tanggalendR.getText().toString(), spinner.getSelectedItem().toString());
            }
        });

        adapterRekapSaldo = new AdapterRekapSaldo(getApplicationContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterRekapSaldo);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        DatePickerDialog.OnDateSetListener date2 = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar3.set(Calendar.YEAR, year);
            myCalendar3.set(Calendar.MONTH, monthOfYear);
            myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        };

        tanggalstartR.setOnClickListener(view -> {
            final Calendar myCalendar2 = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(RekapSaldoActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
            datePickerDialog.show();
        });


        tanggalendR.setOnClickListener(view -> {
            final Calendar myCalendar2 = Calendar.getInstance();
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(RekapSaldoActivity.this, date2, myCalendar3
                    .get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH),
                    myCalendar3.get(Calendar.DAY_OF_MONTH));
            datePickerDialog2.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
            datePickerDialog2.show();
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorStroke(tanggalstartR.getBackground(), "green");
        DrawableMap.changeColorStroke(tanggalendR.getBackground(), "green");

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

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tanggalstartR.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat2 = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2);
        tanggalendR.setText(sdf2.format(myCalendar3.getTime()));
    }

    public void getDataRekap(String tanggalStart, String tanggalEnd, String type) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<responRekap> call = api.getSaldoRekap(token, tanggalStart, tanggalEnd, type);
        call.enqueue(new Callback<responRekap>() {
            @Override
            public void onResponse(@NonNull Call<responRekap> call, @NonNull Response<responRekap> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData().getItems();
                } else {
                    data.clear();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

                adapterRekapSaldo = new AdapterRekapSaldo(getApplicationContext(), data);
                recyclerView.setAdapter(adapterRekapSaldo);
            }

            @Override
            public void onFailure(@NonNull Call<responRekap> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}