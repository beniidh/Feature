package com.c.cpayid.feature.menuUtama.BPJS;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

public class ModalBpjs extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterProdukBPJS adapterProdukBPJS;
    ArrayList<ResponBPJS.mData.Product> mBpjs = new ArrayList<>();
    Button pilih, tutup;
    String rubah;
    //copas semua
    androidx.appcompat.widget.SearchView search;

    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_air, container, false);

        recyclerView = v.findViewById(R.id.ReyProdukAir);
        adapterProdukBPJS = new AdapterProdukBPJS(ModalBpjs.this,getContext(), mBpjs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukBPJS);

        String idd = getArguments().getString("id");
        getProduk(idd);

        pilih = v.findViewById(R.id.pilihProdukAir);
        tutup = v.findViewById(R.id.tutupProdukAir);

        pilih.setOnClickListener(v1 -> {

            String nameid[][] = AdapterProdukBPJS.getNameid();
            String namee = nameid[0][0];
            String id = nameid[0][1];
            bottomSheetListenerProduksms.onButtonClick(namee, id);
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
                adapterProdukBPJS.getFilter().filter(newText);
                return false;
            }
        });


        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id);
    }

    public void getProduk(String id) {

        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponBPJSKS> call = api.getProdukBpjs(token, id);
        call.enqueue(new Callback<ResponBPJSKS>() {
            @Override
            public void onResponse(Call<ResponBPJSKS> call, Response<ResponBPJSKS> response) {

                if (response.body().getCode().equals("200")){

                    getProdukSub(response.body().getData().get(0).getId());
                }else {
                    Toast.makeText(getContext(),response.body().getError(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponBPJSKS> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
                Log.e("errorapa",t.toString());
            }
        });
    }

    public void getProdukSub(String id) {
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponBPJS> call = api.getProdukBpjsSub(token, id);
        call.enqueue(new Callback<ResponBPJS>() {
            @Override
            public void onResponse(Call<ResponBPJS> call, Response<ResponBPJS> response) {

                if (response.body().getCode().equals("200")) {

                    mBpjs = response.body().getData().getProduct();
                    adapterProdukBPJS = new AdapterProdukBPJS(ModalBpjs.this,getContext(), mBpjs);
                    recyclerView.setAdapter(adapterProdukBPJS);

                }else {

                    Toast.makeText(getContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponBPJS> call, Throwable t) {

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
