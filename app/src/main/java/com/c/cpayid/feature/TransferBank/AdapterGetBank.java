package com.c.cpayid.feature.TransferBank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;
import java.util.List;

public class AdapterGetBank extends RecyclerView.Adapter<AdapterGetBank.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelNamaBank.mNama.Product> modelNamaBanks;
    String rubah;
    public static String nameid[][] = new String[1][2];
    private List<ModelNamaBank.mNama.Product> modelNamaBanksfull;
    //rubah copas semua
    ModalNamaBank modalNamaBank;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterGetBank(ModalNamaBank modalNamaBank,Context context, List<ModelNamaBank.mNama.Product> modelNamaBanks) {
        this.context = context;
        this.modelNamaBanks = modelNamaBanks;
        this.modalNamaBank = modalNamaBank;
        modelNamaBanksfull = new ArrayList<>(modelNamaBanks);
        for (int i = 0; i < modelNamaBanks.size(); i++) {
            selectCheck.add(0);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listbank, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ModelNamaBank.mNama.Product modelnamabank = modelNamaBanks.get(position);
        holder.name.setText(modelnamabank.getName());
        if(modelnamabank.isGangguan()){
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.klik.setEnabled(false);
        }else {
            holder.gangguan.setVisibility(View.GONE);
            holder.klik.setEnabled(true);
        }

        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modalNamaBank.bottomSheetListener.onButtonClick(modelnamabank.getName(),modelnamabank.getCode());
                Preference.setName(v.getContext(), "");
                modalNamaBank.dismiss();
            }

        });

    }

    @Override
    public int getItemCount() {
        return modelNamaBanks.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelNamaBank.mNama.Product> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {

                filterList.addAll(modelNamaBanksfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelNamaBank.mNama.Product item : modelNamaBanksfull) {
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
            modelNamaBanks.clear();
            modelNamaBanks.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,gangguan;
        LinearLayout klik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            gangguan = itemView.findViewById(R.id.gangguanBank);
            klik = itemView.findViewById(R.id.linearKlikk);
        }
    }


    public static String[][] getNameid() {
        return nameid;
    }
}
