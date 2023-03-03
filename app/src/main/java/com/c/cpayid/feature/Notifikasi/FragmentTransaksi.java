package com.c.cpayid.feature.Notifikasi;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTransaksi extends BaseFragment {

    private FragmentTransaksiViewModel mViewModel;
    AdapterFragmentTransaksi adapterFragmentTransaksi;
    ArrayList<ResponTransaksiN.DataTransaksi> mData = new ArrayList<>();

    public static FragmentTransaksi newInstance() {
        return new FragmentTransaksi();
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaksi_fragment, container, false);

        recyclerView = v.findViewById(R.id.reyTransaksiNotifikasi);

        adapterFragmentTransaksi = new AdapterFragmentTransaksi(getContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterFragmentTransaksi);

        getData();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentTransaksiViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getData(){
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponTransaksiN> call = api.getHistoriTransaksiN(token);
        call.enqueue(new Callback<ResponTransaksiN>() {
            @Override
            public void onResponse(@NonNull Call<ResponTransaksiN> call, @NonNull Response<ResponTransaksiN> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if(code.equals("200")){
                    mData = response.body().getData();
                    adapterFragmentTransaksi = new AdapterFragmentTransaksi(getContext(),mData);
                    recyclerView.setAdapter(adapterFragmentTransaksi);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponTransaksiN> call, @NonNull Throwable t) {

            }
        });
    }
}