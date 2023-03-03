package com.c.cpayid.feature.menuUtama.HolderPulsa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Transaksi.MInquiry;
import com.c.cpayid.feature.Transaksi.ResponInquiry;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukHolder extends RecyclerView.Adapter<AdapterProdukHolder.ViewHolder> {
    Context context;
    ArrayList<ResponProdukHolder.Mdata> produk;
    String nomor;
    Activity Context;


    public AdapterProdukHolder(Context context, ArrayList<ResponProdukHolder.Mdata> produk, String nomor, Produkholder Context) {
        this.context = context;
        this.produk = produk;
        this.nomor = nomor;
        this.Context = Context;
    }

    public AdapterProdukHolder(Context context, ArrayList<ResponProdukHolder.Mdata> produk, String nomor, ProductHolderGame Context) {
        this.context = context;
        this.produk = produk;
        this.nomor = nomor;
        this.Context = Context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponProdukHolder.Mdata Produk = produk.get(position);
        holder.name.setText(Produk.getName());
        holder.deskripsi.setText(Produk.getDescription());
        holder.harga.setText(utils.ConvertRP(Produk.getTotal_price()));
        holder.poin.setText(Produk.getPoin() + " Poin");

        if (Produk.isGangguan()) {
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.linearklik.setEnabled(false);
        } else {
            holder.gangguan.setVisibility(View.GONE);
            holder.linearklik.setEnabled(true);
        }

        holder.linearklik.setOnClickListener(v -> {
            LoadingPrimer loadingPrimer = new LoadingPrimer(Context);
            loadingPrimer.startDialogLoadingCancleAble();

                Api api = RetroClient.getApiServices();
                MInquiry mInquiry = new MInquiry(Produk.getCode(),  Preference.getNo(context), "PRABAYAR", Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context),Double.valueOf(Preference.getLang(v.getContext())), Double.valueOf(Preference.getLong(v.getContext())));
                String token = "Bearer " + Preference.getToken(context);

                Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);

                call.enqueue(new Callback<ResponInquiry>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponInquiry> call, @NonNull Response<ResponInquiry> response) {

                        assert response.body() != null;
                        String code = response.body().getCode();
                        if (code.equals("200")) {

                            Intent intent = new Intent(context, KonfirmasiPembayaran.class);
                            intent.putExtra("deskripsi", response.body().getData().getDescription());
                            intent.putExtra("nomorr", Preference.getNo(context));
                            intent.putExtra("kodeproduk", "pulsapra");
                            //transaksi
                            intent.putExtra("RefID", response.body().getData().getRef_id());
                            intent.putExtra("sku_code", response.body().getData().getBuyer_sku_code());
                            intent.putExtra("inquiry", response.body().getData().getInquiry_type());
                            intent.putExtra("hargga", utils.ConvertRP(response.body().getData().getSelling_price()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                        }

                        loadingPrimer.dismissDialog();

                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponInquiry> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Koneksi tidak stabil", Toast.LENGTH_LONG).show();
                        loadingPrimer.dismissDialog();
                    }
                });
        });

        DrawableMap.changeColorStroke(holder.container.getBackground(), "green");
        holder.harga.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi, harga, gangguan, poin;
        LinearLayout linearklik, container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            name = itemView.findViewById(R.id.namepulsaprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiprabayar);
            harga = itemView.findViewById(R.id.hargapulsaprabayar);
            linearklik = itemView.findViewById(R.id.linearklik);
            gangguan = itemView.findViewById(R.id.gangguan);
            poin = itemView.findViewById(R.id.poin);
        }
    }


}
