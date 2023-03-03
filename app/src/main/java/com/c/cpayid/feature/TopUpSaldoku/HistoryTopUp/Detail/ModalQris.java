package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.Detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.databinding.DetailQrisBinding;
import com.c.cpayid.feature.databinding.DetailVaBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class ModalQris extends BottomSheetDialogFragment {

    Button mengerti;
    private DetailQrisBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailQrisBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        binding.status.setText(getArguments().getString("status"));
        binding.exp.setText(getArguments().getString("exp").substring(0, 10));
        Picasso.get().load(getArguments().getString("qris")).into(binding.qris);

        double jumlah = Double.parseDouble(getArguments().getString("jumlah")) + Double.parseDouble(getArguments().getString("admin"));
        binding.total.setText(utils.ConvertRP(String.valueOf(jumlah)));
        binding.refresh.setOnClickListener(view -> {
            Picasso.get().load(getArguments().getString("qris")).into(binding.qris);
        });


        return v;

    }

}
