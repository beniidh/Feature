package com.c.cpayid.feature.menuUtama.Briva;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.pinNew.PinTransaction;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalBriva extends BottomSheetDialogFragment {

    TextView totalTagihan, tagihan, admin, nama, institusi, nomor;
    Button batal, bayar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_briva, container, false);
        institusi = v.findViewById(R.id.institusi);
        totalTagihan = v.findViewById(R.id.totalTagihan);
        tagihan = v.findViewById(R.id.tagihan);
        admin = v.findViewById(R.id.admin);
        nama = v.findViewById(R.id.nama);
        nomor = v.findViewById(R.id.nomor);
        batal = v.findViewById(R.id.batal);
        bayar = v.findViewById(R.id.bayar);

        String nomorr = getArguments().getString("nomorr");
        String hargaa = getArguments().getString("hargga");
        String namaa = getArguments().getString("namecustomer");
        String admin1 = getArguments().getString("admin");
        String ins = getArguments().getString("tarif");
        String tghn = getArguments().getString("tagihan");

        totalTagihan.setText(utils.ConvertRP(hargaa));
        nomor.setText(nomorr);
        nama.setText(namaa);
        admin.setText(utils.ConvertRP(admin1));
        institusi.setText(ins);
        tagihan.setText(utils.ConvertRP(tghn));



        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getContext(), PinTransaction.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("RefID", getArguments().getString("RefID"));
                bundle.putString("sku_code", getArguments().getString("sku_code"));
                bundle.putString("inquiry", getArguments().getString("inquiry"));
                bundle.putString("nomorr", getArguments().getString("nomorr"));
                bundle.putString("wallettype", "SALDOKU");
                intent1.putExtras(bundle);
                startActivity(intent1);
                dismiss();
            }
        });


        return v;
    }
}
