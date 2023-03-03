package com.c.cpayid.feature.menuUtama.air;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalAir extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterAir adapterAir;
    ArrayList<MAir> mAirs = new ArrayList<>();
    Button pilih,tutup;
    androidx.appcompat.widget.SearchView search;

    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_air, container, false);


        recyclerView = v.findViewById(R.id.ReyProdukAir);
        adapterAir = new AdapterAir(ModalAir.this,getContext(), mAirs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterAir);

        String idd = getArguments().getString("id");
        getProdukAir(idd);

        pilih = v.findViewById(R.id.pilihProdukAir);
        tutup = v.findViewById(R.id.tutupProdukAir);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterAir.getNameid();

            String namee = nameid[0][0];
            String id = nameid[0][1];

            bottomSheetListenerProduksms.onButtonClick(namee,id);
            dismiss();
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        search = v.findViewById(R.id.searchprodukair);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.onActionViewExpanded();
            }
        });

        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterAir.getFilter().filter(newText);
                return false;
            }
        });


        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id);
    }

    private void getProdukAir(String id){

        String token = "Bearer "+ Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponAir> call = api.getProdukCategoryAir(token,id);
        call.enqueue(new Callback<ResponAir>() {
            @Override
            public void onResponse(Call<ResponAir> call, Response<ResponAir> response) {
                mAirs = response.body().getData();
                adapterAir = new AdapterAir(ModalAir.this,getContext(), mAirs);
                recyclerView.setAdapter(adapterAir);
            }
            @Override
            public void onFailure(Call<ResponAir> call, Throwable t) {
                Toast.makeText(getContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerProduksms = (BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
