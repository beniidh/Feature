package com.c.cpayid.feature.MarkUP.markupSpesifik;

import android.annotation.SuppressLint;
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

public class AdapterSubProdukDHM extends RecyclerView.Adapter<AdapterSubProdukDHM.ViewHolder> implements Filterable {

    private Context context;
    private List<ResponSubProdukDHM.mData> mProduk;
    private List<ResponSubProdukDHM.mData> mProdukFull;
    private int selectedPosition = 0;
    ModalSubProdukDHS modalSubProdukDHS;
    public static String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterSubProdukDHM(ModalSubProdukDHS modalSubProdukDHS, Context context, List<ResponSubProdukDHM.mData> mProduk) {
        this.context = context;
        this.mProduk = mProduk;
        mProdukFull = new ArrayList<>(mProduk);
        this.modalSubProdukDHS = modalSubProdukDHS;
        for (int i = 0; i < mProduk.size(); i++) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ResponSubProdukDHM.mData mproduk = mProduk.get(position);
        holder.name.setText(mproduk.getName());


        holder.linearKlikk.setOnClickListener(v -> {

            modalSubProdukDHS.bottomSheetListenerProduksub.onButtonClicksub(mproduk.getName(),mproduk.getId());
            modalSubProdukDHS.dismiss();

        });
    }

    @Override
    public int getItemCount() {
        return mProduk.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }

    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponSubProdukDHM.mData> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mProdukFull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ResponSubProdukDHM.mData item : mProdukFull) {
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

            mProduk.clear();
            mProduk.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout linearKlikk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
            linearKlikk = itemView.findViewById(R.id.linearKlikk);

        }
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
