package com.c.cpayid.feature.menuUtama.PaketsmsTelpon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.menuUtama.VoucherGame.ModalVoucherGame;

import java.util.ArrayList;

public class AdapterProdukSmsTelpon extends RecyclerView.Adapter<AdapterProdukSmsTelpon.ViewHolder> {

    Context context;
    ArrayList<MProdukPaketSmsT> msmsTelpons;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][3];
    ModalVoucherGame voucher;
    ModalProdukSmsTelpon modalProdukSmsTelpon;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public AdapterProdukSmsTelpon(ModalProdukSmsTelpon modalProdukSmsTelpon, Context context, ArrayList<MProdukPaketSmsT> msmsTelpons) {
        this.context = context;
        this.msmsTelpons = msmsTelpons;
        this.modalProdukSmsTelpon = modalProdukSmsTelpon;
        for (int i = 0; i < msmsTelpons.size(); i++) {
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

        MProdukPaketSmsT msmsTelpon = msmsTelpons.get(position);
        holder.name.setText(msmsTelpon.getName());

        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.chekP.setChecked(true);
                modalProdukSmsTelpon.dismiss();
                modalProdukSmsTelpon.bottomSheetListenerProduksms.onButtonClick(msmsTelpon.getName(),msmsTelpon.getId(),msmsTelpon.getIcon());
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
        return msmsTelpons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout klik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
//            linklik = itemView.findViewById(R.id.linklik);
            klik = itemView.findViewById(R.id.linearKlikk);

        }
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
