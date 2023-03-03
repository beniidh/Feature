package com.c.cpayid.feature.TagihanKonter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;

import java.util.ArrayList;

public class AdapterTagihanKonter extends RecyclerView.Adapter<AdapterTagihanKonter.ViewHolder> {

    Context context;
    ArrayList<ResponTagihanKonter.mData> mData;

    public AdapterTagihanKonter(Context context, ArrayList<ResponTagihanKonter.mData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tagihankonter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponTagihanKonter.mData mdata = mData.get(position);
//        holder.status.setText(mPengajuanLimit.getStatus());
        holder.nominal.setText(utils.ConvertRP(mdata.getTotal_bill()));
        holder.nama.setText(mdata.getUser().getStore_name());
        String tanggal = mdata.getUser_paylater().getDue_date();
        String tahun = tanggal.substring(0, 4);
        String bulan = utils.convertBulan(tanggal.substring(5, 7));
        String hari = tanggal.substring(8, 10);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun);

        if (mdata.getStatus().equals("PENDING")) {
            holder.status.setBackgroundColor(Color.rgb(204, 204, 0));
        } else if (mdata.getStatus().equals("REJECTED")) {
            holder.status.setBackgroundColor(Color.rgb(204, 0, 0));
        } else {
            holder.status.setBackgroundColor(Color.rgb(0, 204, 0));
        }
        holder.status.setText(mdata.getStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailTagihanKonter.class);
            intent.putExtra("namakonter", mdata.getUser().getStore_name());
            intent.putExtra("tanggaltagihan", hari + " " + bulan + " " + tahun);
            intent.putExtra("idtagihan",mdata.getId());
            intent.putExtra("tagihan",mdata.getTotal_bill());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.tanggal.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nominal, tanggal, status, nama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nominal = itemView.findViewById(R.id.nominalTaggihan);
            tanggal = itemView.findViewById(R.id.tanggalTagihanTempo);
            nama = itemView.findViewById(R.id.NamaKonterTagihan);
            status = itemView.findViewById(R.id.statusTagihan);
        }
    }
}
