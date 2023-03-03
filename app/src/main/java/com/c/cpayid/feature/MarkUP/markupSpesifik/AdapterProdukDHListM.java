package com.c.cpayid.feature.MarkUP.markupSpesifik;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukDHListM extends RecyclerView.Adapter<AdapterProdukDHListM.ViewHolder> {
String sales_code;
    Context context;
    ArrayList<ResponProdukListM.mData> mData;

    public AdapterProdukDHListM(Context context, ArrayList<ResponProdukListM.mData> mData) {
        this.context = context;
        this.mData = mData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_daftarhargamarkup, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        getContentProfile();

        ResponProdukListM.mData mdata = mData.get(position);
        holder.name.setText(mdata.getName());
        holder.harga.setText(utils.ConvertRP(mdata.getBasic_price()));
        holder.MarkupSalesS.setText("MarkUp : "+ utils.ConvertRP(mdata.getMarkup_price()));

        holder.ButtonMarkup.setBackgroundColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        holder.ButtonMarkup.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalInputSpesifikMarkup fragment = new ModalInputSpesifikMarkup(); // you fragment
            bundle.putString("produkmasterid", mdata.getProduct_master_id());
            bundle.putString("id", mdata.getId());
            bundle.putString("name", mdata.getName());

            FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager, "detail");
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, harga,MarkupSalesS;
        Button ButtonMarkup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namaProdukDH);
            harga = itemView.findViewById(R.id.hargaProdukDH);
            MarkupSalesS = itemView.findViewById(R.id.MarkupSalesS);
            ButtonMarkup = itemView.findViewById(R.id.ButtonMarkup);
        }
    }

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(context));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                String sales_code = response.body().getData().getReferal_code();
                setSales_code(sales_code);
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {

            }
        });
    }

    public String getSales_code() {
        return sales_code;
    }

    public void setSales_code(String sales_code) {
        this.sales_code = sales_code;
    }
}
