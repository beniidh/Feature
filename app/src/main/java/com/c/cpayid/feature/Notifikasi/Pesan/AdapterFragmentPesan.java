package com.c.cpayid.feature.Notifikasi.Pesan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;

import java.util.ArrayList;

public class AdapterFragmentPesan extends RecyclerView.Adapter<AdapterFragmentPesan.ViewHolder> {
    Context context;
    ArrayList<mPesan.mData> mData;

    public AdapterFragmentPesan(Context context, ArrayList<mPesan.mData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notifikasi_pesan, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mPesan.mData dataTransaksi = mData.get(position);

        holder.title.setText(dataTransaksi.getTitle());
        holder.isi.setVisibility(View.GONE);
        String tanggal = dataTransaksi.getCreated_at();
        String tahun = tanggal.substring(0, 4);
        String bulan = utils.convertBulan(tanggal.substring(5, 7));
        String hari = tanggal.substring(8, 10);
        String waktu = tanggal.substring(11, 16);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun);
        holder.waktu.setText(waktu);

        holder.linearklik.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPesanActivity.class);
            intent.putExtra("tanggal",hari + " " + bulan + " " + tahun);
            intent.putExtra("judul", dataTransaksi.getTitle());
            intent.putExtra("isi", dataTransaksi.getMessage());
            intent.putExtra("id", dataTransaksi.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, isi, tanggal, waktu;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.NTketerangantransaksi);
            tanggal = itemView.findViewById(R.id.NTtanggal);
            isi = itemView.findViewById(R.id.NTketeranganisi);
            linearklik = itemView.findViewById(R.id.linearnotifikasi);
            waktu = itemView.findViewById(R.id.NTwaktu);
        }
    }
}
