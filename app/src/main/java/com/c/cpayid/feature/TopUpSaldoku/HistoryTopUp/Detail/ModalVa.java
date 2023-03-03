package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.Detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.databinding.ActivityQrisBinding;
import com.c.cpayid.feature.databinding.DetailVaBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalVa extends BottomSheetDialogFragment {

    Button mengerti;
    private DetailVaBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailVaBinding.inflate(getLayoutInflater());
        View v =binding.getRoot();

        binding.status.setText(getArguments().getString("status"));
        binding.exp.setText(getArguments().getString("exp").substring(0,10));
        binding.jumlah.setText(utils.ConvertRP(getArguments().getString("jumlah")));
        binding.admin.setText(utils.ConvertRP(getArguments().getString("admin")));
        binding.NoVa.setText(getArguments().getString("number"));
        binding.keterangan.setText("VA "+getArguments().getString("ket"));

        double jumlah = Double.parseDouble(getArguments().getString("jumlah")) + Double.parseDouble(getArguments().getString("admin"));
        binding.total.setText(utils.ConvertRP(String.valueOf(jumlah)));
        binding.batal.setOnClickListener(view -> {
           dismiss();
        });

        binding.copas.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", binding.NoVa.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
        });


        return v;

    }

}
