package com.c.cpayid.feature.menuUtama.ListrikPLN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Transaksi.MInquiry;
import com.c.cpayid.feature.Transaksi.ResponInquiry;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.DetailTransaksiPulsaPra;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukPLN extends RecyclerView.Adapter<AdapterProdukPLN.ViewHolder> {

    Context context;
    ArrayList<ModelProdukPln> modelProdukPlns;
    String nomor,type;
    PlnProduk produk;

    public AdapterProdukPLN(Context context, ArrayList<ModelProdukPln> modelProdukPlns, String nomor, String type, PlnProduk produk) {
        this.context = context;
        this.modelProdukPlns = modelProdukPlns;
        this.nomor = nomor;
        this.type = type;
        this.produk = produk;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_plnpra, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelProdukPln modelProdukPln = modelProdukPlns.get(position);
        holder.name.setText(modelProdukPln.getName());
        holder.deskripsi.setText(modelProdukPln.getDescription());
        holder.harga.setText(utils.ConvertRP(modelProdukPln.getTotal_price()));
        holder.poin.setText(modelProdukPln.getPoin() + " Poin");

        if (modelProdukPln.isGangguan()) {
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.linearklik.setEnabled(false);
        } else {
            holder.gangguan.setVisibility(View.GONE);
            holder.linearklik.setEnabled(true);
        }

        GpsTracker gpsTracker = new GpsTracker(context);
        holder.linearklik.setOnClickListener(v -> {
            LoadingPrimer loadingPrimer = new LoadingPrimer(produk);
            loadingPrimer.startDialogLoadingCancleAble();

            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(modelProdukPln.getCode(), nomor, Preference.getTypee(context), Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
            String token = "Bearer " + Preference.getToken(context);
            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
            call.enqueue(new Callback<ResponInquiry>() {
                @Override
                public void onResponse(@NonNull Call<ResponInquiry> call, @NonNull Response<ResponInquiry> response) {
                    assert response.body() != null;
                    String code = response.body().getCode();
                    if (code.equals("200")){
                        Bundle bundle = new Bundle();
                        bundle.putString("deskripsi",modelProdukPln.getDescription());
                        bundle.putString("nomorr",nomor);
                        bundle.putString("namecustomer",response.body().getData().getCustomer_name());
                        bundle.putString("RefID",response.body().getData().getRef_id());
                        bundle.putString("sku_code",response.body().getData().getBuyer_sku_code());
                        bundle.putString("kodeproduk","pln");
                        bundle.putString("inquiry",response.body().getData().getInquiry_type());
                        bundle.putString("hargga", response.body().getData().getSelling_price());
                        Preference.setUrlIcon(context,"");
                        DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        fragment.setArguments(bundle);
                        fragment.show(fragmentManager ,"detail");
                    } else {
                        Toast.makeText(context,response.body().getError(),Toast.LENGTH_LONG).show();
                    }
                    loadingPrimer.dismissDialog();
                }

                @Override
                public void onFailure(@NonNull Call<ResponInquiry> call, @NonNull Throwable t) {
                    loadingPrimer.dismissDialog();
                }
            });
        });

        DrawableMap.changeColorStroke(holder.llContianer.getBackground(), "green");
    }

    @Override
    public int getItemCount() {
        return modelProdukPlns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,deskripsi,harga,gangguan,poin;
        LinearLayout linearklik, llContianer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llContianer = itemView.findViewById(R.id.llContianer);
            name = itemView.findViewById(R.id.nameplnprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiplnprabayar);
            harga = itemView.findViewById(R.id.hargaplnprabayar);
            linearklik = itemView.findViewById(R.id.linearklikpln);
            gangguan = itemView.findViewById(R.id.gangguan);
            poin = itemView.findViewById(R.id.poin);
        }
    }
}
