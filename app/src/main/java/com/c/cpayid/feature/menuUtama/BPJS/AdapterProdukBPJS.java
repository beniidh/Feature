package com.c.cpayid.feature.menuUtama.BPJS;

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

public class AdapterProdukBPJS extends RecyclerView.Adapter<AdapterProdukBPJS.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ResponBPJS.mData.Product> mBpjs;
    ArrayList<ResponBPJS.mData.Product> mBpjsFulls;
    String rubah; //copas semua
    private int selectedPosition = 0;
    ModalBpjs modalBpjs;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterProdukBPJS( ModalBpjs modalBpjs,Context context, ArrayList<ResponBPJS.mData.Product> mBpjs) {
        this.context = context;
        this.mBpjs = mBpjs;
        this.modalBpjs = modalBpjs;
        mBpjsFulls = new ArrayList<>(mBpjs);
        for (int i = 0; i < mBpjs.size(); i++) {
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

        ResponBPJS.mData.Product mbpjs = mBpjs.get(position);
        holder.name.setText(mbpjs.getName());


        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalBpjs.bottomSheetListenerProduksms.onButtonClick(mbpjs.getName(),mbpjs.getCode());
                modalBpjs.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBpjs.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponBPJS.mData.Product> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mBpjsFulls);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ResponBPJS.mData.Product item : mBpjsFulls) {
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
            mBpjs.clear();
            mBpjs.addAll((List) results.values);
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

    public static String[][] getNameid() {
        return nameid;
    }
}
