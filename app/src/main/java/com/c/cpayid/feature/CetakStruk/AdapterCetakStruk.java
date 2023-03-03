package com.c.cpayid.feature.CetakStruk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.CetakStruk.StrukPLNPasca.CetakPlnPasca;
import com.c.cpayid.feature.CetakStruk.StrukPLNPra.CetakBank;
import com.c.cpayid.feature.CetakStruk.StrukPLNPra.CetakPlnPra;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.TransaksiPending;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCetakStruk extends RecyclerView.Adapter<AdapterCetakStruk.ViewHolder> implements Filterable {
    Context context;
    ArrayList<ResponStruk.DataTransaksi> mData;
    ArrayList<ResponStruk.DataTransaksi> mDatafull;
    String rubah;

    public AdapterCetakStruk(Context context, ArrayList<ResponStruk.DataTransaksi> mData) {
        this.context = context;
        this.mData = mData;
        mDatafull = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksiku, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponStruk.DataTransaksi dataTransaksi = mData.get(position);
        holder.status.setText(dataTransaksi.getStatus());
        holder.harga.setText(dataTransaksi.getTotal_price());
        holder.transaksi.setText(dataTransaksi.getId());
        String tanggal = dataTransaksi.getUpdated_at();
        String tahun = tanggal.substring(0, 4);
        String bulan = utils.convertBulan(tanggal.substring(5, 7));
        String hari = tanggal.substring(8, 10);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun);
        holder.produk.setText(dataTransaksi.getProduct_name());

        holder.linearklik.setOnClickListener(v -> {
//
//            Intent intent = new Intent(context, DetailTransaksiTruk.class);
//            Bundle extras = new Bundle();
//            extras.putString("transaksid",dataTransaksi.getId());
//            extras.putString("nomor",dataTransaksi.getCustomer_no());
//            extras.putString("produk",dataTransaksi.getProduct_name());
//            extras.putString("harga",dataTransaksi.getTotal_price());
//            extras.putString("sn",dataTransaksi.getSn());
            String jam = tanggal.substring(11, 16);
//            extras.putString("tanggal",hari + " " + bulan + " " + tahun);
//            extras.putString("waktu",jam);
//            intent.putExtras(extras);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);

//rubah
            cekTransaksi(dataTransaksi.getProduct_master_id(), dataTransaksi.getCustomer_no(), dataTransaksi.getProduct_name()
                    , dataTransaksi.getTotal_price(), dataTransaksi.getProduct_name(), hari + " " + bulan + " " + tahun, jam, jam, dataTransaksi.getSn(), dataTransaksi.getId());


        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }

    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponStruk.DataTransaksi> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mDatafull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ResponStruk.DataTransaksi item : mDatafull) {
                    if (item.getCustomer_no().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);

                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


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


    private void cekTransaksi(String codeProduk, String nomor, String produk, String harga, String nama, String tanggal
            , String waktu, String waktu2, String sn, String trxid) {

        String token = "Bearer " + Preference.getToken(context);
        Api api = RetroClient.getApiServices();
        Call<ResponCodeSubPS> call = api.getSubCodePS(token, codeProduk);
        call.enqueue(new Callback<ResponCodeSubPS>() {
            @Override
            public void onResponse(Call<ResponCodeSubPS> call, Response<ResponCodeSubPS> response) {

                if (response.body().getCode().equals("200")) {
                    String productSub = response.body().getData().getProduct_subcategory().getId();
                    if (productSub.equals("SUBCATID23012900000061")) {

                        Intent intent = new Intent(context, CetakPlnPasca.class);
                        intent.putExtra("nomor", nomor);
                        intent.putExtra("produk", produk);
                        intent.putExtra("harga", harga);
                        intent.putExtra("nama", nama);
                        intent.putExtra("tanggal", tanggal);
                        intent.putExtra("waktu", waktu);
                        intent.putExtra("waktu2", waktu2);
                        intent.putExtra("sn", sn);
                        intent.putExtra("transaksid", trxid);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    } else if (productSub.equals("SUBCATID22070700000036")) {

                        Intent intent = new Intent(context, CetakPlnPra.class);
                        intent.putExtra("nomor", nomor);
                        intent.putExtra("produk", produk);
                        intent.putExtra("harga", harga);
                        intent.putExtra("nama", nama);
                        intent.putExtra("tanggal", tanggal);
                        intent.putExtra("waktu", waktu);
                        intent.putExtra("waktu2", waktu2);
                        intent.putExtra("sn", sn);
                        intent.putExtra("transaksid", trxid);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    } else if (productSub.equals("SUBCATID23013100000082")) {
                        Intent intent = new Intent(context, CetakBank.class);
                        intent.putExtra("nomor", nomor);
                        intent.putExtra("produk", produk);
                        intent.putExtra("harga", harga);
                        intent.putExtra("nama", nama);
                        intent.putExtra("tanggal", tanggal);
                        intent.putExtra("waktu", waktu);
                        intent.putExtra("waktu2", waktu2);
                        intent.putExtra("sn", sn);
                        intent.putExtra("transaksid", trxid);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }  else {

                        Intent intent = new Intent(context, DetailTransaksiTruk.class);
                        intent.putExtra("nomor", nomor);
                        intent.putExtra("produk", produk);
                        intent.putExtra("harga", harga);
                        intent.putExtra("nama", nama);
                        intent.putExtra("tanggal", tanggal);
                        intent.putExtra("waktu", waktu);
                        intent.putExtra("waktu2", waktu2);
                        intent.putExtra("sn", sn);
                        intent.putExtra("transaksid", trxid);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                } else {

                    Toast.makeText(context, response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponCodeSubPS> call, Throwable t) {



            }
        });


    }
}
