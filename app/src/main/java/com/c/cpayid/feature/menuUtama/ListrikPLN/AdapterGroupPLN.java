package com.c.cpayid.feature.menuUtama.ListrikPLN;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.menuUtama.HolderPulsa.ResponGroup;

import java.util.ArrayList;

public class AdapterGroupPLN extends RecyclerView.Adapter<AdapterGroupPLN.ViewHolder> {
    Context context;
    ArrayList<ResponGroup.Data> datasub;


    public AdapterGroupPLN(Context context, ArrayList<ResponGroup.Data> datasub) {
        this.context = context;
        this.datasub = datasub;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_groupkategori, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponGroup.Data data = datasub.get(position);
        holder.namesub.setText(data.getName());
        holder.linierSubCategory.setOnClickListener(v -> {

            Intent intent = new Intent(context, PlnProduk.class);
            intent.putExtra("id", data.getId());
            intent.putExtra("jenis","sub");
            intent.putExtra("name", data.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return datasub.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linierSubCategory;
        TextView namesub;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linierSubCategory = itemView.findViewById(R.id.linierSubCategory);
            namesub = itemView.findViewById(R.id.namesub);


        }
    }

}
