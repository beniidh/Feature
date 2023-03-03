package com.c.cpayid.feature.menuUtama.ListrikPLNPasca;

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
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.DetailTransaksiPulsaPra;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterProdukPLNPasca extends RecyclerView.Adapter<AdapterProdukPLNPasca.ViewHolder> {

    Context context;
    ArrayList<ModelProdukPlnPasca> modelProdukPlnPascas;
    String nomor;

    public AdapterProdukPLNPasca(Context context, ArrayList<ModelProdukPlnPasca> modelProdukPlnPascas, String nomor) {
        this.context = context;
        this.modelProdukPlnPascas = modelProdukPlnPascas;
        this.nomor = nomor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_plnpra, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelProdukPlnPasca modelProdukPlnPasca = modelProdukPlnPascas.get(position);
        holder.name.setText(modelProdukPlnPasca.getName());
        holder.deskripsi.setText(modelProdukPlnPasca.getDescription());
        double harga = Double.parseDouble(modelProdukPlnPasca.getBasic_price());
        Locale localeid = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        holder.harga.setText(formatRupiah.format(harga));

        holder.linearklik.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("deskripsi",modelProdukPlnPasca.getDescription());
            bundle.putString("nomorr",nomor);
            bundle.putString("hargga",modelProdukPlnPasca.getBasic_price());
            bundle.putString("kodeproduk","pln");
            DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager ,"detail");
        });




    }

    @Override
    public int getItemCount() {
        return modelProdukPlnPascas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,deskripsi,harga;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameplnprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiplnprabayar);
            harga = itemView.findViewById(R.id.hargaplnprabayar);
            linearklik = itemView.findViewById(R.id.linearklikpln);



        }
    }
}
