package com.c.cpayid.feature.menuUtama.Internet;

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

public class AdapterInternet extends RecyclerView.Adapter<AdapterInternet.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ModelInternet> modelInternets;
    ArrayList<ModelInternet> modelInternetsfull;
    private int selectedPosition = 0;
    ModalInternet modalInternet;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterInternet(ModalInternet modalInternet, Context context, ArrayList<ModelInternet> modelInternets) {
        this.context = context;
        this.modelInternets = modelInternets;
        modelInternetsfull = new ArrayList<>(modelInternets);
        this.modalInternet = modalInternet;

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

        ModelInternet modelInternet = modelInternets.get(position);
        holder.name.setText(modelInternet.getName());


        holder.linklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.chekP.setChecked(true);
                modalInternet.dismiss();
                modalInternet.bottomSheetListenerProduksms.onButtonClick(modelInternet.getName(),modelInternet.getId());

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
        return modelInternets.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelInternet> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelInternetsfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelInternet item : modelInternetsfull) {
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
            modelInternets.clear();
            modelInternets.addAll((List) results.values);
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
