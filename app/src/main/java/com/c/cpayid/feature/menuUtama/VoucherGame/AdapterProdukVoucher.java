package com.c.cpayid.feature.menuUtama.VoucherGame;

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
import com.c.cpayid.feature.Helper.GpsTracker;
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

public class AdapterProdukVoucher extends RecyclerView.Adapter<AdapterProdukVoucher.ViewHolder> {

    Context context;
    ArrayList<ResponProdukVoucher.VoucherData> mVoucherProduk;
    String nomor, type;

    public AdapterProdukVoucher(Context context, ArrayList<ResponProdukVoucher.VoucherData> mVoucherProduk, String nomor, String type) {
        this.context = context;
        this.mVoucherProduk = mVoucherProduk;
        this.nomor = nomor;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_plnpra, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponProdukVoucher.VoucherData mVoucherData = mVoucherProduk.get(position);
        holder.name.setText(mVoucherData.getName());
        holder.deskripsi.setText(mVoucherData.getDescription());
        holder.harga.setText(utils.ConvertRP(mVoucherData.getTotal_price()));

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
                        Intent intent = new Intent(context, KonfirmasiPembayaran.class);
                        intent.putExtra("deskripsi", response.body().getData().getDescription());
                        intent.putExtra("nomorr", Preference.getNo(context));
                        intent.putExtra("kodeproduk", "pulsapasca");
                        //transaksi
                        intent.putExtra("RefID",response.body().getData().getRef_id());
                        intent.putExtra("sku_code",response.body().getData().getBuyer_sku_code());
                        intent.putExtra("inquiry",response.body().getData().getInquiry_type());
                        intent.putExtra("hargga", utils.ConvertRP(response.body().getData().getSelling_price()) );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponInquiry> call, @NonNull Throwable t) {

                }
            });
        });

        holder.harga.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColorStroke(holder.llContianer.getBackground(), "green");
    }

    @Override
    public int getItemCount() {
        return mVoucherProduk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi, harga,gangguan;
        LinearLayout linearklik, llContianer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llContianer = itemView.findViewById(R.id.llContianer);
            name = itemView.findViewById(R.id.nameplnprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiplnprabayar);
            harga = itemView.findViewById(R.id.hargaplnprabayar);
            linearklik = itemView.findViewById(R.id.linearklikpln);
            gangguan = itemView.findViewById(R.id.gangguan);

        }
    }
}
