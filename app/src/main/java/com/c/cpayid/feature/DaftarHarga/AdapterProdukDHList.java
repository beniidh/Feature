package com.c.cpayid.feature.DaftarHarga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;

import java.util.ArrayList;

public class AdapterProdukDHList extends RecyclerView.Adapter<AdapterProdukDHList.ViewHolder> {

    Context context;
    ArrayList<ResponProdukList.mData.Product> mData;
    String rubah;

    public AdapterProdukDHList(Context context, ArrayList<ResponProdukList.mData.Product> mData) {//rubah
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_daftarharga, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponProdukList.mData.Product mdata = mData.get(position);//rubah
        holder.name.setText(mdata.getName());
        holder.harga.setText(utils.ConvertRP(mdata.getTotal_price()));
        holder.kodeProduk.setText("Kode : "+mdata.getCode());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, harga,kodeProduk;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namaProdukDH);
            harga = itemView.findViewById(R.id.hargaProdukDH);
            kodeProduk = itemView.findViewById(R.id.kodeProduk);
        }
    }
}
