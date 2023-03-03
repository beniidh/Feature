package com.c.cpayid.feature.menuUtama.UangElektronik;

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

import com.c.cpayid.feature.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterUangElektronik extends RecyclerView.Adapter<AdapterUangElektronik.ViewHolder> implements Filterable {

    Context context;
    ArrayList<MUangElektronik> mUangElektroniks;
    ArrayList<MUangElektronik> mUangElektronikfulls;
    ModalUangElektronik modalUangElektronik;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterUangElektronik(ModalUangElektronik modalUangElektronik, Context context, ArrayList<MUangElektronik> mUangElektroniks) {
        this.context = context;
        this.mUangElektroniks = mUangElektroniks;
        mUangElektronikfulls = new ArrayList<>(mUangElektroniks);
        this.modalUangElektronik = modalUangElektronik;

        for (int i = 0; i < mUangElektroniks.size(); i++) {
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

        MUangElektronik mUangElektronik = mUangElektroniks.get(position);
        holder.name.setText(mUangElektronik.getName());


        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        holder.linklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modalUangElektronik.dismiss();
                modalUangElektronik.bottomSheetListenerProduksms.onButtonClick(mUangElektronik.getName(),mUangElektronik.getId());


            }
        });


    }

    @Override
    public int getItemCount() {
        return mUangElektroniks.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MUangElektronik> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mUangElektronikfulls);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MUangElektronik item : mUangElektronikfulls) {
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
            mUangElektroniks.clear();
            mUangElektroniks.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout linklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
            linklik = itemView.findViewById(R.id.linearKlikk);

        }
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
