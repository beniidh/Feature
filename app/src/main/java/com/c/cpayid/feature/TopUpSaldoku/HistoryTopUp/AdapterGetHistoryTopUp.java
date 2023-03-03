package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.Detail.ModalBank;
import com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.Detail.ModalQris;
import com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.Detail.ModalVa;

import java.util.ArrayList;

public class AdapterGetHistoryTopUp extends RecyclerView.Adapter<AdapterGetHistoryTopUp.ViewHolder> {
    Context context;
    ArrayList<ResponHistory.mData> mData;

    public AdapterGetHistoryTopUp(Context context, ArrayList<ResponHistory.mData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history_topup, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponHistory.mData data = mData.get(position);
        holder.harga.setText(utils.ConvertRP(data.getAmount()));
        holder.status.setText(data.getStatus());
        holder.tanggal.setText(data.getCreated_at().substring(0,10));

        if(!data.getProof_payment().isEmpty()){
            holder.bukti.setText("Sudah upload bukti");
        }else {
            holder.bukti.setText("Belum upload bukti");

        }

        if(!data.getStatus().equals("PENDING")){

            holder.cancel.setVisibility(View.GONE);
        }else {

            holder.cancel.setVisibility(View.VISIBLE);

            if(!data.getProof_payment().isEmpty()){

            }else {
            }

        }



        holder.klikHistory.setOnClickListener(view -> {

            if(data.getType_payment().equals("va")){

                Bundle bundle = new Bundle();

                ModalVa ModalVa = new ModalVa();
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();

                bundle.putString("status",data.getStatus());
                bundle.putString("jumlah",data.getAmount());
                bundle.putString("exp",data.getVa_expired());
                bundle.putString("admin",data.getVa_admin());
                bundle.putString("number",data.getVa_number());
                bundle.putString("ket",data.getBank_code());
//                bundle.putString("type",data.getType());
//                bundle.putString("icon",data.getIcon());

                ModalVa.setArguments(bundle);
                ModalVa.show(fragmentManager, "detail");

            }else if(data.getType_payment().equals("qris")) {

                Bundle bundle = new Bundle();

                ModalQris Modalqris = new ModalQris();
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();

                bundle.putString("status",data.getStatus());
                bundle.putString("jumlah",data.getAmount());
                bundle.putString("exp",data.getVa_expired());
                bundle.putString("admin",data.getVa_admin());
                bundle.putString("qris",data.getQris_image());


                Modalqris.setArguments(bundle);
                Modalqris.show(fragmentManager, "detail");

            }else {
                Bundle bundle = new Bundle();

                ModalBank modalBank = new ModalBank();
                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();

                bundle.putString("status",data.getStatus());
                bundle.putString("jumlah",data.getAmount());
                bundle.putString("exp",data.getVa_expired());
                bundle.putString("id",data.getId());
                bundle.putString("admin",data.getVa_admin());
                bundle.putString("logobank",data.getPayment_option_server().getIcon());
                bundle.putString("norek",data.getPayment_option_server().getAccount_no());
                bundle.putString("logobank",data.getPayment_option_server().getIcon());
                bundle.putString("bankName",data.getPayment_option_server().getName());
                bundle.putString("ANbank",data.getPayment_option_server().getAccount_name());
                modalBank.setArguments(bundle);
                modalBank.show(fragmentManager, "detail");

            }
        });



        holder.cancel.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalCancel modalCancel = new ModalCancel();
            FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            bundle.putString("id",data.getId());
            modalCancel.setArguments(bundle);
            modalCancel.show(fragmentManager,"cancel");

        });

//        holder.upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context,uploadBukti.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("id",data.getId());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView harga, tanggal, status, bukti;
        LinearLayout klikHistory;
        Button cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            harga = itemView.findViewById(R.id.harga);
            tanggal = itemView.findViewById(R.id.tanggal);
            status = itemView.findViewById(R.id.status);
            bukti = itemView.findViewById(R.id.bukti);
            cancel = itemView.findViewById(R.id.cancel);

            klikHistory = itemView.findViewById(R.id.klikHistory);

        }
    }
}
