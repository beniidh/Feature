package com.c.cpayid.feature.menuUtama.HolderPulsa;

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
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

public class AdapterGroupHolder extends RecyclerView.Adapter<AdapterGroupHolder.ViewHolder> {
    Context context;
    ArrayList<ResponGroup.Data> datasub;

    public AdapterGroupHolder(Context context, ArrayList<ResponGroup.Data> datasub) {
        this.context = context;
        this.datasub = datasub;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_groupkategori, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponGroup.Data data = datasub.get(position);
        holder.namesub.setText(data.getName());
        holder.linierSubCategory.setOnClickListener(v -> {
            if (Preference.getNoType(context).equals("PASCABAYAR")) {
                if (Preference.getPascatype(context.getApplicationContext()).equals("air_pdam")) {
                    Intent intent = new Intent(context, ProductHolderPascaPdam.class);
                    intent.putExtra("id", data.getId());
                    intent.putExtra("jenis", "sub");
                    intent.putExtra("name", data.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProdukHolderPasca.class);
                    intent.putExtra("id", data.getId());
                    intent.putExtra("jenis", "sub");
                    intent.putExtra("name", data.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } else {
                if (Preference.getPascatype(context.getApplicationContext()).equals("voucher_game")) {
                    Intent intent = new Intent(context, ProductHolderGame.class);
                    intent.putExtra("id", data.getId());
                    intent.putExtra("jenis", "sub");
                    intent.putExtra("name", data.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Produkholder.class);
                    intent.putExtra("id", data.getId());
                    intent.putExtra("jenis", "sub");
                    intent.putExtra("name", data.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linierSubCategory = itemView.findViewById(R.id.linierSubCategory);
            namesub = itemView.findViewById(R.id.namesub);
        }
    }
}
