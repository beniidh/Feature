package com.c.cpayid.feature.Profil.Poin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.databinding.ListRewardBinding;
import com.c.cpayid.feature.engine.DrawableMap;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptergetReward extends RecyclerView.Adapter<AdaptergetReward.ViewHolder> {

    Context context;
    ArrayList<mReward.DataEntity> data;
    int poin;

    public AdaptergetReward(Context context, ArrayList<mReward.DataEntity> data, int poin) {
        this.context = context;
        this.data = data;
        this.poin = poin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListRewardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mReward.DataEntity reward = data.get(position);

        holder.listRewardBinding.judul.setText(reward.name);
        holder.listRewardBinding.deskripsi.setText(reward.description);
        holder.listRewardBinding.HargaPoin.setText(String.valueOf(reward.poinPrice));
        String path = reward.image;
        if (!path.isEmpty()) {
            Picasso.get().load(reward.image).into(holder.listRewardBinding.gambar);
        }

        if (reward.poinPrice > poin) {
            holder.listRewardBinding.buttonTukar.setText(context.getResources().getString(R.string.poin));
            holder.listRewardBinding.buttonTukar.setEnabled(false);
        } else {
            holder.listRewardBinding.buttonTukar.setText(context.getResources().getString(R.string.poinCukup));
        }

        holder.listRewardBinding.buttonTukar.setOnClickListener(v -> {
            ModalTukarPin modalTukarPin = new ModalTukarPin();
            Bundle bundle = new Bundle();
            bundle.putString("poinID",reward.id);
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            modalTukarPin.setArguments(bundle);
            modalTukarPin.show( fragmentManager, "modalTukar");
        });

        DrawableMap.changeColor(holder.listRewardBinding.buttonTukar.getBackground(), "green");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListRewardBinding listRewardBinding;

        public ViewHolder( ListRewardBinding itemView) {
            super(itemView.getRoot());
            listRewardBinding = itemView;
        }
    }
}
