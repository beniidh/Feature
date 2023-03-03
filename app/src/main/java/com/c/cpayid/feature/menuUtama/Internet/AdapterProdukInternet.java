package com.c.cpayid.feature.menuUtama.Internet;

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
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Transaksi.MInquiry;
import com.c.cpayid.feature.Transaksi.ResponInquiry;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.menuUtama.TransaksiHandle.DetailTransaksiPasca;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukInternet extends RecyclerView.Adapter<AdapterProdukInternet.ViewHolder> {

    Context context;
    ArrayList<ResponProdukInternet.VoucherData> mVoucherProduk;
    String nomor, type;

    public AdapterProdukInternet(Context context, ArrayList<ResponProdukInternet.VoucherData> mVoucherProduk, String nomor, String type) {
        this.context = context;
        this.mVoucherProduk = mVoucherProduk;
        this.nomor = nomor;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_air, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponProdukInternet.VoucherData mVoucherData = mVoucherProduk.get(position);
        holder.name.setText(mVoucherData.getName());
        holder.deskripsi.setText(mVoucherData.getDescription());
        holder.brand.setText(mVoucherData.getBrand());

        if (mVoucherData.isGangguan()) {
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.linearklik.setEnabled(false);
        }else {
            holder.gangguan.setVisibility(View.GONE);
            holder.linearklik.setEnabled(true);
        }

        holder.linearklik.setOnClickListener(v -> {
            GpsTracker gpsTracker = new GpsTracker(context);

            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(mVoucherData.getCode(), nomor, type, Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
            String token = "Bearer " + Preference.getToken(context);
            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
            call.enqueue(new Callback<ResponInquiry>() {
                @Override
                public void onResponse(@NonNull Call<ResponInquiry> call, @NonNull Response<ResponInquiry> response) {
                    assert response.body() != null;
                    String code = response.body().getCode();
                    if (code.equals("200")) {
                        if (response.body().getData().getStatus().equals("Sukses")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("nomorr", nomor);
                            bundle.putString("namecustomer", response.body().getData().getCustomer_name());
                            bundle.putString("RefID", response.body().getData().getRef_id());
                            bundle.putString("sku_code", response.body().getData().getBuyer_sku_code());
                            bundle.putString("kodeproduk", "pulsapasca");
                            bundle.putString("inquiry", response.body().getData().getInquiry_type());
                            bundle.putString("hargga", response.body().getData().getSelling_price());

                            bundle.putString("status", response.body().getData().getStatus());
                            bundle.putString("tagihan", utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getNilai_tagihan()));
                            bundle.putString("deskription", response.body().getData().getDescription());
                            bundle.putString("admin", utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getAdmin()));

                            Preference.setUrlIcon(context, "");
                            DetailTransaksiPasca fragment = new DetailTransaksiPasca(); // you fragment
                            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                            fragment.setArguments(bundle);
                            fragment.show(fragmentManager, "detail");
                        } else {
                            Toast.makeText(context, response.body().getData().getStatus() + " " + response.body().getData().getDescription(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponInquiry> call, @NonNull Throwable t) {
                    Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        });

        DrawableMap.changeColorStroke(holder.llContianer.getBackground(), "green");
    }

    @Override
    public int getItemCount() {
        return mVoucherProduk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi,brand,gangguan;
        LinearLayout linearklik, llContianer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llContianer = itemView.findViewById(R.id.llContianer);
            name = itemView.findViewById(R.id.nameair);
            deskripsi = itemView.findViewById(R.id.deskriair);
            linearklik = itemView.findViewById(R.id.linearklikair);
            brand =itemView.findViewById(R.id.brandair);
            gangguan = itemView.findViewById(R.id.gangguan);

        }
    }
}
