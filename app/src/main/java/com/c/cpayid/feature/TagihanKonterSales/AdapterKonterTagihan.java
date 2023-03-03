package com.c.cpayid.feature.TagihanKonterSales;

import android.annotation.SuppressLint;
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

public class AdapterKonterTagihan extends RecyclerView.Adapter<AdapterKonterTagihan.ViewHolder>{

    Context context;
    ArrayList<ResponTagihanKonterSales.mData> mData;

    public AdapterKonterTagihan(Context context, ArrayList<ResponTagihanKonterSales.mData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_konter_tagihan, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponTagihanKonterSales.mData mData1 = mData.get(position);
        holder.nominal.setText(utils.ConvertRP(mData1.getTotal_bill()));
        holder.nama.setText(mData1.getUser().getStore_name());
        String tanggal = mData1.getDue_date();
        String tahun = tanggal.substring(0,4);
        String bulan = utils.convertBulan(tanggal.substring(5,7));
        String hari = tanggal.substring(8,10);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nominal,tanggal,nama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nominal = itemView.findViewById(R.id.konterNominal);
            tanggal = itemView.findViewById(R.id.konterTanggal);
            nama = itemView.findViewById(R.id.konterNama);
        }
    }
}
