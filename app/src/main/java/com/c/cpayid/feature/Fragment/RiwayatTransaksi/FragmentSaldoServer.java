package com.c.cpayid.feature.Fragment.RiwayatTransaksi;

import androidx.fragment.app.FragmentActivity;
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

public class FragmentSaldoServer extends BaseFragment {

    private FragmentSaldoServerViewModel mViewModel;
    RecyclerView recyclerView;
    AdapterFragmentSaldoServer adapterFragmentSaldoServer;
    ArrayList<ResponTransaksi.DataTransaksi> mData = new ArrayList<>();
    public static FragmentActivity saldoserver;

    public static FragmentSaldoServer newInstance() {
        return new FragmentSaldoServer();
    }

    public FragmentSaldoServer(ArrayList<ResponTransaksi.DataTransaksi> mData) {
        this.mData = mData;
    }

    public FragmentSaldoServer() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saldo_server_fragment, container, false);
        recyclerView = v.findViewById(R.id.ReyFragmentSaldoServer);
//        LinearLayout lintesst = v.findViewById(R.id.lintesst);

        saldoserver = getActivity();
        adapterFragmentSaldoServer = new AdapterFragmentSaldoServer(getContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterFragmentSaldoServer);

//        lintesst.setOnClickListener(v1 -> {
//
//            Intent intent = new Intent(getActivity(), HistoryTransaksi.class);
//            startActivity(intent);
//        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentSaldoServerViewModel.class);
        // TODO: Use the ViewModel
    }
}