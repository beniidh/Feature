package com.c.cpayid.feature.menuUtama.AngsuranKredit;

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

public class AdapterAngsuran extends RecyclerView.Adapter<AdapterAngsuran.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ModelAngsuran> modelAngsurans;
    ArrayList<ModelAngsuran> modelAngsuransfull;
    private int selectedPosition = 0;
    ModalAngsuran modalAngsuran;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterAngsuran(ModalAngsuran modalAngsuran, Context context, ArrayList<ModelAngsuran> modelAngsurans) {
        this.context = context;
        this.modelAngsurans = modelAngsurans;
        modelAngsuransfull = new ArrayList<>(modelAngsurans);
        this.modalAngsuran = modalAngsuran;

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

        ModelAngsuran modelAngsuran = modelAngsurans.get(position);
        holder.name.setText(modelAngsuran.getName());

        holder.linklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modalAngsuran.dismiss();
                holder.chekP.setChecked(true);
                modalAngsuran.bottomSheetListenerProduksms.onButtonClick(modelAngsuran.getName(),modelAngsuran.getId());


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
        return modelAngsurans.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelAngsuran> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelAngsuransfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelAngsuran item : modelAngsuransfull) {
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
            modelAngsurans.clear();
            modelAngsurans.addAll((List) results.values);
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
