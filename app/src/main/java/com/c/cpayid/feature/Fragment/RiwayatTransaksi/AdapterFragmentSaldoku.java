package com.c.cpayid.feature.Fragment.RiwayatTransaksi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.TransaksiPending;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

public class AdapterFragmentSaldoku extends RecyclerView.Adapter<AdapterFragmentSaldoku.ViewHolder> {
    Context context;
    ArrayList<ResponTransaksi.DataTransaksi> mData;


    public AdapterFragmentSaldoku(Context context, ArrayList<ResponTransaksi.DataTransaksi> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksiku, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponTransaksi.DataTransaksi dataTransaksi = mData.get(position);

        if (dataTransaksi.getStatus().equals("PENDING")) {

            holder.status.setBackgroundColor(Color.rgb(204, 204, 0));
        } else if (dataTransaksi.getStatus().equals("GAGAL")) {
            holder.status.setBackgroundColor(Color.rgb(204, 0, 0));

        } else {
            holder.status.setBackgroundColor(Color.rgb(0, 204, 0));
        }

        holder.status.setText(dataTransaksi.getStatus());
        holder.harga.setText(utils.ConvertRP(dataTransaksi.getTotal_price()));
        holder.transaksi.setText(dataTransaksi.getCustomer_no());
        String tanggal = dataTransaksi.getCreated_at();
        String tahun = tanggal.substring(0, 4);
        String bulan = utils.convertBulan(tanggal.substring(5, 7));
        String hari = tanggal.substring(8, 10);
        String jam = tanggal.substring(11, 16);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun + " " + jam);
        String dataproduk = dataTransaksi.getProduct_name();
        if (dataproduk.length() <= 18) {
            holder.produk.setText(dataTransaksi.getProduct_name());
        } else {
            holder.produk.setText(dataTransaksi.getProduct_name().substring(0, 18) + "...");
        }

        holder.linearklik.setOnClickListener(v -> {


            Intent intent = new Intent(context, TransaksiPending.class);
            Bundle extras = new Bundle();
            Preference.setUrlIcon(context, "");
            extras.putString("transaksid", dataTransaksi.getId());
            extras.putString("code", "saldo");
            intent.putExtras(extras);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transaksi, tanggal, produk, harga, status;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transaksi = itemView.findViewById(R.id.TransaksiFSSS);
            tanggal = itemView.findViewById(R.id.TanggalFSSS);
            harga = itemView.findViewById(R.id.HargaFSSS);
            linearklik = itemView.findViewById(R.id.lintesstS);
            produk = itemView.findViewById(R.id.ProdukFSSS);
            status = itemView.findViewById(R.id.StatusFSSS);

        }
    }
}
