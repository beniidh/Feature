package com.c.cpayid.feature.menuUtama.air;

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

public class AdapterAir extends RecyclerView.Adapter<AdapterAir.ViewHolder> implements Filterable {

    Context context;
    ArrayList<MAir> mAirs;
    ArrayList<MAir> mAirsFulls;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    ModalAir modalAir;

    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterAir(ModalAir modalAir, Context context, ArrayList<MAir> mAirs) {
        this.context = context;
        this.mAirs = mAirs;
        this.modalAir = modalAir;
        mAirsFulls = new ArrayList<>(mAirs);
        for (int i = 0; i < mAirs.size(); i++) {
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

        MAir mAir = mAirs.get(position);
        holder.name.setText(mAir.getName());

        holder.linklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.chekP.setChecked(true);
                modalAir.dismiss();
                modalAir.bottomSheetListenerProduksms.onButtonClick(mAir.getName(),mAir.getId());
            }
        });

//        holder.chekP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked==true){
//                    modelKabupatens.get(holder.getAdapterPosition()).setPilihan(true);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mAirs.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MAir> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mAirsFulls);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MAir item : mAirsFulls) {
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
            mAirs.clear();
            mAirs.addAll((List) results.values);
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
