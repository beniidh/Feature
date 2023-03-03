package com.c.cpayid.feature.menuUtama.HolderPulsa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHolder extends RecyclerView.Adapter<AdapterHolder.ViewHolder> {
    Context context;
    ArrayList<ResponSub.mData> datasub;
    HolderPulsaActivity holderPulsaActivity;

    public AdapterHolder(Context context, ArrayList<ResponSub.mData> datasub, HolderPulsaActivity holderPulsaActivity) {
        this.context = context;
        this.datasub = datasub;
        this.holderPulsaActivity = holderPulsaActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_subcategory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponSub.mData data = datasub.get(position);
        holder.namesub.setText(data.getName());

        if (!data.getIcon().isEmpty()) {
            Picasso.get().load(data.getIcon()).into(holder.iconsub);
        } else {
            Picasso.get().load("http://").into(holder.iconsub);
        }

        holder.linierSubCategory.setOnClickListener(v -> {
            if (Preference.getNoType(context).equals("PASCABAYAR")) {
                chekGroupPasca(data.getId(), data.getName());
            } else {
                chekGroup(data.getId(), data.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datasub.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linierSubCategory;
        TextView namesub;
        ImageView iconsub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linierSubCategory = itemView.findViewById(R.id.linierSubCategory);
            namesub = itemView.findViewById(R.id.namesub);
            iconsub = itemView.findViewById(R.id.iconsub);
        }
    }

    public void chekGroup(String id, String nama) {
        String token = "Bearer " + Preference.getToken(context.getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGroup> call = api.getProdukGroup(token, id);
        call.enqueue(new Callback<ResponGroup>() {
            @Override
            public void onResponse(@NonNull Call<ResponGroup> call, @NonNull Response<ResponGroup> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                   Intent intent = new Intent(context, GroupHolderActivity.class);
                   intent.putExtra("id",id);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);
                } else if (code.equals("400")) {
                    if (Preference.getPascatype(context.getApplicationContext()).equals("voucher_game")) {
                        Intent intent = new Intent(context, ProductHolderGame.class);
                        intent.putExtra("id", id);
                        intent.putExtra("jenis", "nosub");
                        intent.putExtra("name", nama);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, Produkholder.class);
                        intent.putExtra("id", id);
                        intent.putExtra("jenis", "nosub");
                        intent.putExtra("name", nama);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponGroup> call, @NonNull Throwable t) {

            }
        });
    }

    public void chekGroupPasca(String id, String nama) {
        String token = "Bearer " + Preference.getToken(context.getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGroup> call = api.getProdukGroup(token, id);
        call.enqueue(new Callback<ResponGroup>() {
            @Override
            public void onResponse(@NonNull Call<ResponGroup> call, @NonNull Response<ResponGroup> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    Intent intent = new Intent(context, GroupHolderActivity.class);
                    intent.putExtra("id",id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (code.equals("400")) {
                    if (Preference.getPascatype(context.getApplicationContext()).equals("air_pdam")) {
                        Intent intent = new Intent(context, ProductHolderPascaPdam.class);
                        intent.putExtra("id", id);
                        intent.putExtra("jenis", "nosub");
                        intent.putExtra("name", nama);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ProdukHolderPasca.class);
                        intent.putExtra("id", id);
                        intent.putExtra("jenis", "nosub");
                        intent.putExtra("name", nama);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponGroup> call, @NonNull Throwable t) {

            }
        });
    }
}
