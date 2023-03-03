package com.c.cpayid.feature.menuUtama.PajakPBB;

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

public class AdapterPajak extends RecyclerView.Adapter<AdapterPajak.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ModelPajak> modelPajaks;
    ArrayList<ModelPajak> modelPajaksfull;
    ModalPajak modalPajak;
    private int selectedPosition = 0;
    public static String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterPajak(ModalPajak modalPajak, Context context, ArrayList<ModelPajak> modelPajaks) {
        this.context = context;
        this.modelPajaks = modelPajaks;
        modelPajaksfull = new ArrayList<>(modelPajaks);
        this.modalPajak = modalPajak;
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

        ModelPajak modelPajak = modelPajaks.get(position);
        holder.name.setText(modelPajak.getName());


        holder.linklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.chekP.setChecked(true);
                modalPajak.dismiss();
                modalPajak.bottomSheetListenerProduksms.onButtonClick(modelPajak.getName(),modelPajak.getId());
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
            List<ModelPajak> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelPajaksfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelPajak item : modelPajaksfull) {
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
