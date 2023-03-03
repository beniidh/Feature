package com.c.cpayid.feature.menuUtama.PaketsmsTelpon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.menuUtama.Paket.MPaketData;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.DetailTransaksiPulsaPra;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterSmsTelpon extends RecyclerView.Adapter<AdapterSmsTelpon.ViewHolder> {
    Context context;
    ArrayList<MPaketData> mPaketData ;
    String nomor;
    String urlicon;

    public AdapterSmsTelpon(Context context, ArrayList<MPaketData> mPaketData, String nomor, String urlicon) {
        this.context = context;
        this.mPaketData = mPaketData;
        this.nomor = nomor;
        this.urlicon = urlicon;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MPaketData mPaketDatas = mPaketData.get(position);
//        holder.name.setText(mPaketDatas.name);
        holder.deskripsi.setText(mPaketDatas.getDescription());
        double harga = Double.parseDouble(mPaketDatas.getTotal_price());
        Locale localeid = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        holder.harga.setText(formatRupiah.format(harga));

        holder.linearklik.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("deskripsi",mPaketDatas.getDescription());
            bundle.putString("nomorr",nomor);
            bundle.putString("urlicon",urlicon);
            bundle.putString("hargga",mPaketDatas.getTotal_price());
            DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager ,"detail");
        });
    }

    @Override
    public int getItemCount() {
        return mPaketData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,deskripsi,harga;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namepulsaprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiprabayar);
            harga = itemView.findViewById(R.id.hargapulsaprabayar);
            linearklik = itemView.findViewById(R.id.linearklik);

        }
    }
}
