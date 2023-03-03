package com.c.cpayid.feature.Fragment.RekapsaldoFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.Fragment.RekapSaldo.responRekap;
import com.c.cpayid.feature.Fragment.RekapsaldoFragment.Komponen.DebitFragment;
import com.c.cpayid.feature.Fragment.RekapsaldoFragment.Komponen.KreditFragment;
import com.c.cpayid.feature.Fragment.RekapsaldoFragment.Komponen.SemuaFragment;
import com.c.cpayid.feature.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RekapFragment extends BaseFragment {

    TabLayout tablayoutRekap;
    ViewPager ViewPagerlayoutTRekap;
    TabAdapter tabAdapter;
    ImageView FilterRekap;
    modalFilter Modalfiltedr;
    ArrayList<responRekap.Data.Item> data = new ArrayList<>();
    ArrayList<responRekap.Data.Item> dataDebit = new ArrayList<>();
    ArrayList<responRekap.Data.Item> dataKredit = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rekap, container, false);

        tablayoutRekap = v.findViewById(R.id.tablayoutRekap);
        ViewPagerlayoutTRekap = v.findViewById(R.id.ViewPagerlayoutTRekap);
        FilterRekap = v.findViewById(R.id.FilterRekap);

        String dateToday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        String dateYesterday = dateFormat.format(cal.getTime());

        getDataRekap(dateYesterday, dateToday, "SALDOKU");
        FilterRekap.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            modalFilter modalKelurahan = new modalFilter();
            bundle.putString("kecamatankey", "");
            modalKelurahan.setArguments(bundle);
            modalKelurahan.show(getChildFragmentManager(), "Modal Filter");
        });

        getChildFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, result) -> {
            String resultt = result.getString("bundleKey");
            String tanggalmulai = result.getString("Tanggalmulai");
            String tanggalselesai = result.getString("Tanggalselesai");
            String jenissaldo = result.getString("jenissaldo");
            getDataRekap(tanggalmulai, tanggalselesai, jenissaldo);
        });

        return v;
    }

    @Override
    public void settingLayout(View view) {
        super.settingLayout(view);

        tablayoutRekap.setSelectedTabIndicatorColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        tablayoutRekap.setTabTextColors(ColorMap.getColor(ApplicationVariable.applicationId, "gray4"), ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    public void getDataRekap(String tanggalStart, String tanggalEnd, String type) {
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<responRekap> call = api.getSaldoRekap(token, tanggalStart, tanggalEnd, type);
        call.enqueue(new Callback<responRekap>() {
            @Override
            public void onResponse(@NonNull Call<responRekap> call, @NonNull Response<responRekap> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData().getItems();
                    dataDebit.clear();
                    dataKredit.clear();
                    for (responRekap.Data.Item datasecond : data) {
                        if (datasecond.getType_nominal().equals("KREDIT")) {
                            dataDebit.add(datasecond);
                        } else {
                            dataKredit.add(datasecond);
                        }
                    }
                } else {
                    data.clear();
                    dataDebit.clear();
                    dataKredit.clear();
                    Toast.makeText(requireContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

                if (!isAdded()) return;

                tabAdapter = new TabAdapter(getChildFragmentManager());
                tabAdapter.addFragment(new SemuaFragment(data), "Tab 1");
                tabAdapter.addFragment(new DebitFragment(dataDebit), "Tab 2");
                tabAdapter.addFragment(new KreditFragment(dataKredit), "Tab 3");
                ViewPagerlayoutTRekap.setAdapter(tabAdapter);
                tablayoutRekap.setupWithViewPager(ViewPagerlayoutTRekap);
                tablayoutRekap.getTabAt(0).setText("Semua");
                tablayoutRekap.getTabAt(1).setText("Kredit");
                tablayoutRekap.getTabAt(2).setText("Debit");
            }

            @Override
            public void onFailure(@NonNull Call<responRekap> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Koneksi tidak stabil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}