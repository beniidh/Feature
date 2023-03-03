package com.c.cpayid.feature.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Modal.ModalProvinsi;
import com.c.cpayid.feature.Model.ModelProvinsi;
import com.c.cpayid.feature.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProvinsi extends RecyclerView.Adapter<AdapterProvinsi.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelProvinsi> modelProvinsiList;
    private List<ModelProvinsi> modelProvinsiList2;
    public static String nameid[][] = new String[1][2];
    private List<ModelProvinsi> modelProvinsisfull;
    private int selectedPosition = 0;
    ModalProvinsi modalProvinsi;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterProvinsi(ModalProvinsi modalProvinsi, Context context, List<ModelProvinsi> modelProvinsiList) {
        this.context = context;
        this.modelProvinsiList = modelProvinsiList;
        modelProvinsisfull = new ArrayList<>(modelProvinsiList);
        this.modalProvinsi = modalProvinsi;
        for (int i = 0; i < modelProvinsiList.size(); i++) {
            selectCheck.add(0);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        ModelProvinsi modelProvinsi = modelProvinsiList.get(position);
        holder.name.setText(utils.capitalizeFirstLetter(modelProvinsi.getName().toLowerCase()));
        holder.klik.setOnClickListener(v -> {


            modalProvinsi.dismiss();
            modalProvinsi.bottomSheetListener.onButtonClick( modelProvinsi.getName(),modelProvinsi.getId());

        });

    }

    @Override
    public int getItemCount() {
        return modelProvinsiList.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelProvinsi> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelProvinsisfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelProvinsi item : modelProvinsisfull) {
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
            modelProvinsiList.clear();
            modelProvinsiList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout klik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
            klik = itemView.findViewById(R.id.linearKlikk);

        }
    }

    public List<ModelProvinsi> getModelProvinsiList() {
        return modelProvinsiList2;
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
