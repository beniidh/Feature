package com.c.cpayid.feature.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.Fragment.RekapSaldo.RekapSaldoActivity;
import com.c.cpayid.feature.Fragment.RiwayatTransaksi.FragmentSaldoServer;
import com.c.cpayid.feature.Fragment.RiwayatTransaksi.FragmentSaldoku;
import com.c.cpayid.feature.Fragment.RiwayatTransaksi.ResponTransaksi;
import com.c.cpayid.feature.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiFragment extends BaseFragment {

    TabLayout tablayoutnotifikasi;
    private ViewPager viewPager;
    TabAdapter tabAdapter;
    Button RekapSaldo;
    EditText idtransaksiTanggalEditText, idTransaksiTanggalSelesai;
    ArrayList<ResponTransaksi.DataTransaksi> datahistory = new ArrayList<>();
    ArrayList<ResponTransaksi.DataTransaksi> datasaldoserver = new ArrayList<>();
    ArrayList<ResponTransaksi.DataTransaksi> datasaldoku = new ArrayList<>();
    TextView idTotalTransaksiTextView, idTransaksiSuksesTextView, idTotalPengeluaranTextView;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transaksi_fragment, container, false);

        idTransaksiTanggalSelesai = v.findViewById(R.id.idTransaksiTanggalSelesai);
        tablayoutnotifikasi = v.findViewById(R.id.tablayoutransaksi);
        viewPager = v.findViewById(R.id.ViewPagerlayoutTransaksi);
        idtransaksiTanggalEditText = v.findViewById(R.id.idTransaksiTanggalEditText);
        idTotalTransaksiTextView = v.findViewById(R.id.idTotalTransaksiTextView);
        idTransaksiSuksesTextView = v.findViewById(R.id.idTransaksiSuksesTextView);
        idTotalPengeluaranTextView = v.findViewById(R.id.idTotalPengeluaranTextView);
        RekapSaldo = v.findViewById(R.id.RekapSaldo);

        RekapSaldo.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), RekapSaldoActivity.class);
            startActivity(intent);
        });

        idtransaksiTanggalEditText.setOnClickListener(v1 -> showDateDialog("start"));
        idTransaksiTanggalSelesai.setOnClickListener(v1 -> showDateDialog("end"));

        return v;
    }

    @Override
    public void settingLayout(View view) {
        super.settingLayout(view);

        DrawableMap.changeColor(RekapSaldo.getBackground(), "green");
        DrawableMap.changeColorStroke(RekapSaldo.getBackground(), "green");

        tablayoutnotifikasi.setTabTextColors(ColorMap.getColor(ApplicationVariable.applicationId, "gray4"), ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        tablayoutnotifikasi.setSelectedTabIndicatorColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    public void getDataHistory(String start, String end) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(getActivity());
        loadingPrimer.startDialogLoading();
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponTransaksi> call = api.getHistoriTransaksi(token, start, end);
        call.enqueue(new Callback<ResponTransaksi>() {
            @Override
            public void onResponse(@NonNull Call<ResponTransaksi> call, @NonNull Response<ResponTransaksi> response) {
                int totaltransaksi = 0;
                int totaltransaksisukses = 0;
                double totalpengeluaran = 0;
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    datahistory = response.body().getData();
                    datasaldoku.clear();
                    datasaldoserver.clear();

                    for (ResponTransaksi.DataTransaksi dataa : datahistory) {
                        totaltransaksi += 1;

                        if (dataa.getStatus().equals("SUKSES")) {
                            totaltransaksisukses += 1;
                            double totalpengeluarann = Double.parseDouble(dataa.getTotal_price());
                            totalpengeluaran += totalpengeluarann;
                        }
                        if (dataa.getWallet_type().equals("PAYLATTER")) {
                            datasaldoserver.add(dataa);
                        }
                        if (dataa.getWallet_type().equals("SALDOKU")) {
                            datasaldoku.add(dataa);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                    datahistory.clear();
                    datasaldoku.clear();
                    datasaldoserver.clear();
                }

                loadingPrimer.dismissDialog();

                if (!isAdded()) return;

                tabAdapter = new TabAdapter(getChildFragmentManager());
                tabAdapter.addFragment(new FragmentSaldoku(datasaldoku), "Tab 1");
                tabAdapter.addFragment(new FragmentSaldoServer(datasaldoserver), "Tab 2");
                viewPager.setAdapter(tabAdapter);

                tablayoutnotifikasi.setupWithViewPager(viewPager);
                tablayoutnotifikasi.getTabAt(0).setText("Saldoku");
                tablayoutnotifikasi.getTabAt(1).setText("Saldo Server");

                idTotalTransaksiTextView.setText(String.valueOf(totaltransaksi));
                idTransaksiSuksesTextView.setText(String.valueOf(totaltransaksisukses));
                String total = String.valueOf(totalpengeluaran);
                idTotalPengeluaranTextView.setText(utils.ConvertRP(total));
            }

            @Override
            public void onFailure(@NonNull Call<ResponTransaksi> call, @NonNull Throwable t) {
                loadingPrimer.dismissDialog();
            }
        });
    }

    public void showDateDialog(String jenis) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {

                    String bulan = String.valueOf(monthOfYear + 1);
                    String day = String.valueOf(dayOfMonth);
                    if (bulan.length() == 1) {
                        bulan = "0" + bulan;

                    }
                    if (day.length() == 1) {
                        day = "0" + day;
                    }

                    String tnggl = year + "-" + bulan + "-" + day;

                    if (jenis.equals("start")) {
                        int mulai = Integer.parseInt(tnggl.replaceAll("-", ""));
                        int selesai = Integer.parseInt(idTransaksiTanggalSelesai.getText().toString().replaceAll("-", ""));

                        int jumlah = selesai - mulai;

                        if (jumlah > 30) {
                            jumlah = jumlah - 70;
                        }
                        idtransaksiTanggalEditText.setText(tnggl);
                        if ((jumlah) < 8) {
                            datahistory.clear();
                            getDataHistory(tnggl, idTransaksiTanggalSelesai.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "Transaksi Maksimal 7 hari", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        int selesai = Integer.parseInt(tnggl.replaceAll("-", ""));
                        int mulai = Integer.parseInt(idtransaksiTanggalEditText.getText().toString().replaceAll("-", ""));

                        int jumlah = selesai - mulai;

                        if (jumlah > 30) {

                            jumlah = jumlah - 70;
                        }
                        idTransaksiTanggalSelesai.setText(tnggl);
                        if ((jumlah) < 8) {
                            datahistory.clear();
                            getDataHistory(idtransaksiTanggalEditText.getText().toString(),tnggl);

                        } else {
                            Toast.makeText(getContext(), "Transaksi Maksimal 7 hari", Toast.LENGTH_LONG).show();
                        }
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        idtransaksiTanggalEditText.setText(date);
        idTransaksiTanggalSelesai.setText(date);
        getDataHistory(date, date);

        datahistory.clear();
    }
}