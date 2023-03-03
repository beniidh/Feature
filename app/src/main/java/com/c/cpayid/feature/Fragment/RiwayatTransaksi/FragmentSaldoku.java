package com.c.cpayid.feature.Fragment.RiwayatTransaksi;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.R;

import java.util.ArrayList;

public class FragmentSaldoku extends BaseFragment {

    private FragmentSaldokuViewModel mViewModel;
    RecyclerView recyclerView;
    AdapterFragmentSaldoku adapterFragmentSaldoku;
    ArrayList<ResponTransaksi.DataTransaksi> mData = new ArrayList<>();


    public static FragmentSaldoku newInstance() {
        return new FragmentSaldoku();
    }

    public FragmentSaldoku(ArrayList<ResponTransaksi.DataTransaksi> mData) {
        this.mData = mData;
    }

    public FragmentSaldoku() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saldoku_fragment, container, false);

        recyclerView = v.findViewById(R.id.ReyFragmentSaldoku);
        adapterFragmentSaldoku = new AdapterFragmentSaldoku(getContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterFragmentSaldoku);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentSaldokuViewModel.class);
        // TODO: Use the ViewModel
    }
}