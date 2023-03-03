package com.c.cpayid.feature.Notifikasi;

import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.cpayid.feature.Notifikasi.Pesan.FragmentPesan;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class NotifikasiActivity extends BaseActivity {
    TabLayout tablayoutnotifikasi;
    ViewPager viewPagerNotifikasi;
    TabAdapter tabAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Notifikasi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        viewPagerNotifikasi = findViewById(R.id.viewPagerNotifikasi);
        tablayoutnotifikasi = (TabLayout) findViewById(R.id.tablayoutnotifikasi);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FragmentTransaksi(), "Tab 1");
        tabAdapter.addFragment(new FragmentPesan(), "Tab 2");
        viewPagerNotifikasi.setAdapter(tabAdapter);
        tablayoutnotifikasi.setupWithViewPager(viewPagerNotifikasi);

        Objects.requireNonNull(tablayoutnotifikasi.getTabAt(0)).setText("Transaksi");
        Objects.requireNonNull(tablayoutnotifikasi.getTabAt(1)).setText("Pesan");
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        tablayoutnotifikasi.setSelectedTabIndicatorColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        tablayoutnotifikasi.setTabTextColors(ColorMap.getColor(ApplicationVariable.applicationId, "gray4"), ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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