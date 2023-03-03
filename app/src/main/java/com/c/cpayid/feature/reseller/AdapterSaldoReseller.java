package com.c.cpayid.feature.reseller;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;

import java.util.ArrayList;

public class AdapterSaldoReseller extends RecyclerView.Adapter<AdapterSaldoReseller.ViewHolder> {

    Context context;
    ArrayList<ResponSaldoReseller.Data> mData;

    public AdapterSaldoReseller(Context context, ArrayList<ResponSaldoReseller.Data> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_saldoku_reseller, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponSaldoReseller.Data mData1 = mData.get(position);
        holder.nominal.setText(utils.ConvertRP(mData1.getAmount()));
        holder.nama.setText(mData1.getUser().getStore_name());
        holder.tanggal.setText(mData1.getUpdated_at().substring(0, 10));
        holder.status.setText(mData1.getStatus());

        if (mData1.getStatus().equals("PENDING")) {
            holder.status.setBackgroundColor(Color.rgb(168, 168, 50));
        } else if (mData1.getStatus().equals("REJECT")) {
            holder.status.setBackgroundColor(Color.rgb(168, 50, 50));
        } else {
            holder.status.setBackgroundColor(Color.rgb(48, 230, 148));
        }

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalApprove modalApprove = new ModalApprove();
            bundle.putString("ID", mData1.getId());
            bundle.putString("Harga", mData1.getAmount());
            FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            modalApprove.setArguments(bundle);
            modalApprove.show(fragmentManager, "detail");

        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nominal, tanggal, nama, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nominal = itemView.findViewById(R.id.nominalSR);
            tanggal = itemView.findViewById(R.id.tanggalSR);
            nama = itemView.findViewById(R.id.NamaKonterSR);
            status = itemView.findViewById(R.id.statusSR);
        }
    }
}
