package com.c.cpayid.feature.DaftarHarga;

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

public class AdapterSubProdukDH extends RecyclerView.Adapter<AdapterSubProdukDH.ViewHolder> implements Filterable {

    private Context context;
    private List<ResponSubProdukDH.mData> mProduk;
    private List<ResponSubProdukDH.mData> mProdukFull;
    private int selectedPosition = 0;
    ModalSubProdukDH modalSubProdukDH;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterSubProdukDH(ModalSubProdukDH modalSubProdukDH, Context context, List<ResponSubProdukDH.mData> mProduk) {
        this.context = context;
        this.mProduk = mProduk;
        mProdukFull = new ArrayList<>(mProduk);
        this.modalSubProdukDH = modalSubProdukDH;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponSubProdukDH.mData mproduk = mProduk.get(position);
        holder.name.setText(mproduk.getName());


        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              modalSubProdukDH.bottomSheetListenerProduksub.onButtonClicksub(mproduk.getName(),mproduk.getId());
              modalSubProdukDH.dismiss();
            }
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
            List<ResponSubProdukDH.mData> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(mProdukFull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ResponSubProdukDH.mData item : mProdukFull){
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

            mProduk.clear();
            mProduk.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{
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

    public static String[][] getNameid() {
        return nameid;
    }
}
