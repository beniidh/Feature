package com.c.cpayid.feature.MarkUP;

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

public class AdapterMarkUp extends RecyclerView.Adapter<AdapterMarkUp.ViewHolder> implements Filterable {

    private final Context context;
    private List<mMarkUp> markUps;
    private List<mMarkUp> markUpsFull;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public static   String nameid[][] = new String[1][2];

    public AdapterMarkUp(Context context, List<mMarkUp> markUps) {
        this.context = context;
        this.markUps = markUps;
        markUpsFull = new ArrayList<>(markUps);

        for (int i = 0; i < markUps.size(); i++) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {

        mMarkUp mMarkUp = markUps.get(position);
        holder.name.setText(mMarkUp.getName());


        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        holder.chekP.setOnClickListener(v -> {

            for(int k=0; k<selectCheck.size(); k++) {
                if(k==position) {
                    selectCheck.set(k,1);
                } else {
                    selectCheck.set(k,0);
                }
            }
            notifyDataSetChanged();
            nameid[0][0] = mMarkUp.getName();
            nameid[0][1] = mMarkUp.getId();

        });

    }

    @Override
    public int getItemCount() {
        return markUps.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }

    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<mMarkUp> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(markUpsFull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(mMarkUp item : markUpsFull){
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

            markUps.clear();
            markUps.addAll((List)results.values);
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
