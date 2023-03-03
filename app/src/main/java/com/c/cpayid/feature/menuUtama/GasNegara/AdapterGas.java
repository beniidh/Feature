package com.c.cpayid.feature.menuUtama.GasNegara;

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

public class AdapterGas extends RecyclerView.Adapter<AdapterGas.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ResponGasnegara.mData> modelPajaks;
    ArrayList<ResponGasnegara.mData> modelPajaksfull;
    private int selectedPosition = 0;
    ModalGasnegara modalGasnegara;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterGas(ModalGasnegara modalGasnegara, Context context, ArrayList<ResponGasnegara.mData> modelPajaks) {
        this.context = context;
        this.modelPajaks = modelPajaks;
        modelPajaksfull = new ArrayList<>(modelPajaks);
        this.modalGasnegara = modalGasnegara;
        for (int i = 0; i < modelPajaks.size(); i++) {
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

        ResponGasnegara.mData modelPajak = modelPajaks.get(position);
        holder.name.setText(modelPajak.getName());

        holder.linklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.chekP.setChecked(true);
                modalGasnegara.dismiss();
                modalGasnegara.bottomSheetListenerProduksms.onButtonClick(modelPajak.getName(),modelPajak.getId());

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
        return modelPajaks.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponGasnegara.mData> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelPajaksfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ResponGasnegara.mData item : modelPajaksfull) {
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
            modelPajaks.clear();
            modelPajaks.addAll((List) results.values);
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
