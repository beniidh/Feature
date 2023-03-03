package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
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
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class ActivityHistoryTopUp extends BaseActivity implements ModalCancel.BottomSheetListener {
    RecyclerView recyclerView;
    AdapterGetHistoryTopUp adapterGetHistoryTopUp;
    ArrayList<ResponHistory.mData> data = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    EditText tanggaltopup;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_top_up);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>History TopUp <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        recyclerView = findViewById(R.id.linTopup);
        tanggaltopup = findViewById(R.id.tanggaltopup);

        adapterGetHistoryTopUp = new AdapterGetHistoryTopUp(getApplicationContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterGetHistoryTopUp);
        @SuppressLint("SimpleDateFormat") String datee = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tanggaltopup.setText(datee + " to " + datee);
        getData(datee, datee);

//        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
//            // TODO Auto-generated method stub
//            myCalendar.set(Calendar.YEAR, year);
//            myCalendar.set(Calendar.MONTH, monthOfYear);
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateLabel();
//        };

        SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
            @Override
            public void onCancelled() {
            }

            @Override
            public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {

                if (firstDate == null || secondDate == null) {
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih rentang tanggal", Toast.LENGTH_SHORT).show();
                } else {
                    String TanggalAwal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(firstDate.getTime());
                    String TanggalAkhir = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(secondDate.getTime());
                    getData(TanggalAwal, TanggalAkhir);
                    tanggaltopup.setText(TanggalAwal+" to "+TanggalAkhir);
                }
            }
        };

        tanggaltopup.setOnClickListener(v -> new SlyCalendarDialog()
                .setSingle(false)
                .setHeaderColor(ContextCompat.getColor(getApplicationContext(), R.color.green))
                .setCallback(callback)
                .show(getSupportFragmentManager(), "TAG_SLYCALENDAR"));
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tanggaltopup.setText(sdf.format(myCalendar.getTime()));
        getData(sdf.format(myCalendar.getTime()), sdf.format(myCalendar.getTime()));
    }

    private void getData(String start, String end) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponHistory> call = api.getDataTopup(token, start, end);
        call.enqueue(new Callback<ResponHistory>() {
            @Override
            public void onResponse(@NonNull Call<ResponHistory> call, @NonNull Response<ResponHistory> response) {
                assert response.body() != null;
                if (response.body().code.equals("200")) {
                    data = response.body().data;
                } else {
                    data.clear();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }

                adapterGetHistoryTopUp = new AdapterGetHistoryTopUp(getApplicationContext(), data);
                recyclerView.setAdapter(adapterGetHistoryTopUp);
            }

            @Override
            public void onFailure(Call<ResponHistory> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();

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

    @Override
    public void onButtonClick(String name, String id) {
        @SuppressLint("SimpleDateFormat") String datee = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        getData(datee, datee);
    }

    @Override
    protected void onResume() {
        super.onResume();
//
//        String datee = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        getData(datee, datee);
    }
}