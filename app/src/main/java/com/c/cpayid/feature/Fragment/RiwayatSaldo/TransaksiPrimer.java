package com.c.cpayid.feature.Fragment.RiwayatSaldo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.Fragment.TransaksiFragment;
import com.c.cpayid.feature.R;
import com.google.android.material.tabs.TabLayout;


public class TransaksiPrimer extends BaseFragment {

    TabLayout tablayoutnotifikasi;
    private ViewPager viewPager;
    TabPrimer tabPrimer;
    TabPrimer tabPrimer3;
    TabPrimer tabPrimer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_transaksi_primer, container, false);
        tablayoutnotifikasi = v.findViewById(R.id.tablayoutransaksi2);
        viewPager = v.findViewById(R.id.ViewPagerlayoutTransaksi2);
        tabPrimer = new TabPrimer(getChildFragmentManager());
        tabPrimer.addFragment(new TransaksiFragment(), "Tab 1");
        tabPrimer.addFragment(new TransaksiFragment(), "Tab 2");
        viewPager.setAdapter(tabPrimer);
        tablayoutnotifikasi.setupWithViewPager(viewPager);
        tablayoutnotifikasi.getTabAt(0).setText("Transaksi");
        tablayoutnotifikasi.getTabAt(1).setText("Rekap Saldo");

        return v;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getParentFragmentManager().getFragment(savedInstanceState,"key");
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        getParentFragmentManager().putFragment(outState,"key",new TransaksiFragment());
//    }

    @Override
    public void onResume() {
        super.onResume();
        getParentFragmentManager().getFragments();

        tabPrimer = new TabPrimer(getParentFragmentManager());
        tabPrimer.addFragment(new TransaksiFragment(), "Tab 1");
        tabPrimer.addFragment(new TransaksiFragment(), "Tab 2");
        viewPager.setAdapter(tabPrimer);
        tablayoutnotifikasi.setupWithViewPager(viewPager);
        tablayoutnotifikasi.getTabAt(0).setText("Transaksi");
        tablayoutnotifikasi.getTabAt(1).setText("Rekap Saldo");
    }
}