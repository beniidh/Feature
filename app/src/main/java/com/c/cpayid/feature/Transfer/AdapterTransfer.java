package com.c.cpayid.feature.Transfer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTransfer extends RecyclerView.Adapter<AdapterTransfer.ViewHolder> implements Filterable {

    private final Context context;
    private List<ModelKonter.data> modelKonters;
    private List<ModelKonter.data> modelKontersFull;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterTransfer(Context context, List<ModelKonter.data> modelKonters) {
        this.context = context;
        this.modelKonters = modelKonters;
        modelKontersFull = new ArrayList<>(modelKonters);

        for (int i = 0; i < modelKonters.size(); i++) {
            selectCheck.add(0);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtransferkonter, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ModelKonter.data modelKonter = modelKonters.get(position);
        holder.name.setText(modelKonter.getStore_name());

        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        holder.chekP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int k=0; k<selectCheck.size(); k++) {
                    if(k==position) {
                        selectCheck.set(k,1);
                    } else {
                        selectCheck.set(k,0);
                    }
                }
                notifyDataSetChanged();

                nameid[0][0] = modelKonter.getStore_name();
                nameid[0][1] = modelKonter.getId();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelKonters.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }

    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelKonter.data> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(modelKontersFull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelKonter.data item : modelKontersFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
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

            modelKonters.clear();
            modelKonters.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView name;
     CheckBox chekP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);

        }
    }

    public static String[][] getNameid() {
        return nameid;
    }

}
