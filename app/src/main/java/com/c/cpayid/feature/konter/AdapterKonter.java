package com.c.cpayid.feature.konter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterKonter extends RecyclerView.Adapter<AdapterKonter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Mkonter.mData> data;
    ArrayList<Mkonter.mData> datafull;
    KonterActivity konter_activity;

    public AdapterKonter(Context context, ArrayList<Mkonter.mData> data, KonterActivity konter_activity) {
        this.context = context;
        this.data = data;
        datafull = new ArrayList<>(data);
        this.konter_activity = konter_activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_konter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.namakonter.setText( data.get(position).getStore_name());
        holder.code.setText(data.get(position).getCode());
        holder.sales_namapemilik.setText(data.get(position).getName());
        holder.SaldokuList.setText("Saldoku : "+ utils.ConvertRP(data.get(position).getWallet().getSaldoku()));
        holder.SaldoServerList.setText("Server : "+ utils.ConvertRP(data.get(position).getWallet().getPaylatter()));
        holder.markupKonter.setText("Markup : "+ utils.ConvertRP(data.get(position).getMarkup()));
        holder.itemView.setOnClickListener(v -> konter_activity.TunjukkanKeberadaanPopupMenu(holder.namakonter,data.get(position).getId(),data.get(position).getStore_name()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKonter;
    }

    private Filter getFilterKonter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Mkonter.mData> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(datafull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Mkonter.mData item : datafull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            data.clear();
            data.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namakonter, code,KonterProfil,sales_namapemilik,SaldoServerList,SaldokuList,markupKonter;
        ImageView moreevent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namakonter = itemView.findViewById(R.id.sales_namakonter);
            code = itemView.findViewById(R.id.sales_codekonter);
            markupKonter = itemView.findViewById(R.id.markupKonter);
            moreevent = itemView.findViewById(R.id.moreevent);
            sales_namapemilik = itemView.findViewById(R.id.sales_namapemilik);
            KonterProfil = itemView.findViewById(R.id.KonterProfil);
            Typeface type = ResourcesCompat.getFont(context, R.font.abata);
            KonterProfil.setTypeface(type);

            SaldoServerList = itemView.findViewById(R.id.SaldoServerList);
            SaldokuList = itemView.findViewById(R.id.SaldokuList);
        }
    }
}
