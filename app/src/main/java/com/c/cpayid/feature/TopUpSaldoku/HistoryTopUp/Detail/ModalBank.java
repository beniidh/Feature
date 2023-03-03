package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.UploadBukti;
import com.c.cpayid.feature.databinding.DetailBankBinding;
import com.c.cpayid.feature.databinding.DetailVaBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class ModalBank extends BottomSheetDialogFragment {

    Button mengerti;
    private DetailBankBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailBankBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        binding.status.setText(getArguments().getString("status"));
        binding.exp.setText(getArguments().getString("exp").substring(0, 10));
        binding.jumlah.setText(utils.ConvertRP(getArguments().getString("jumlah")));
        binding.Rekening.setText(getArguments().getString("norek"));
        binding.AtasNama.setText(getArguments().getString("ANbank"));
        binding.namaBank.setText(getArguments().getString("bankName"));
        Picasso.get().load(getArguments().getString("logobank")).into(binding.logobank);


        binding.upload.setOnClickListener(view -> {

            Intent intent = new Intent(getContext(), UploadBukti.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", getArguments().getString("id"));
            startActivity(intent);
            dismiss();
        });

        binding.copas.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", binding.Rekening.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
        });


        return v;

    }

}
