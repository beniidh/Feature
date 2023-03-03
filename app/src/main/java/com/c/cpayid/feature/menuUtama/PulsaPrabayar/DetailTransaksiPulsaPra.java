package com.c.cpayid.feature.menuUtama.PulsaPrabayar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailTransaksiPulsaPra extends BottomSheetDialogFragment {
    TextView total, namapelanggann, namaPelanggan;
    RelativeLayout RelativeNamaPelangga;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_detail_transaksi_pulsa_pra, container, false);

        Button konfirasidetail = v.findViewById(R.id.konfirmasidetail);
        namapelanggann = v.findViewById(R.id.namapelanggann);
        TextView namaPelanggan = v.findViewById(R.id.namaPelanggan);

        String kode = getArguments().getString("kodeproduk");

        String namecustomer = getArguments().getString("namecustomer");
        namapelanggann.setVisibility(View.GONE);
        if (kode.equals("pln")) {
            namaPelanggan.setVisibility(View.VISIBLE);
            namaPelanggan.setText(namecustomer);
            namapelanggann.setVisibility(View.VISIBLE);
        }
        konfirasidetail.setOnClickListener(v12 -> {
            String urllicon = getArguments().getString("urlicon");
            Intent intent = new Intent(getContext(), KonfirmasiPembayaran.class);
            intent.putExtra("hargga", total.getText().toString());
            intent.putExtra("urll", urllicon);
            Preference.setUrlIcon(getContext(),urllicon);
            intent.putExtra("RefID", getArguments().getString("RefID"));
            intent.putExtra("sku_code",getArguments().getString("sku_code"));
            intent.putExtra("inquiry",getArguments().getString("inquiry"));
            intent.putExtra("nomorr",getArguments().getString("nomorr"));
            startActivity(intent);
            dismiss();

        });
        Button tutupdetail = v.findViewById(R.id.tutupdetail);
        tutupdetail.setOnClickListener(v1 -> {
            dismiss();
        });

        TextView deskripsi = v.findViewById(R.id.detaildeskripsifragment);
        TextView nomor = v.findViewById(R.id.nomortujuan);
        TextView hargaproduk = v.findViewById(R.id.hargaproduk);
        total = v.findViewById(R.id.totalpembayaran);

        String strtext = getArguments().getString("deskripsi");
        String nomorr = getArguments().getString("nomorr");
        String harga = getArguments().getString("hargga");


        hargaproduk.setText(utils.ConvertRP(harga));
        total.setText(utils.ConvertRP(harga));
        nomor.setText(nomorr);
        deskripsi.setText(strtext);
        return v;

    }
}