package com.c.cpayid.feature.Fragment.RekapsaldoFragment.Komponen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.Fragment.RekapSaldo.responRekap;
import com.c.cpayid.feature.R;

import java.util.ArrayList;

public class DebitFragment extends BaseFragment {
    AdapterRekapDebit adapterRekapSaldo;
    ArrayList<responRekap.Data.Item> data = new ArrayList<>();
    RecyclerView recyclerView;

    public DebitFragment(ArrayList<responRekap.Data.Item> data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_debit, container, false);

        recyclerView = v.findViewById(R.id.reyRekapSaldo);

        adapterRekapSaldo = new AdapterRekapDebit(getContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterRekapSaldo);

        return v;
    }
}