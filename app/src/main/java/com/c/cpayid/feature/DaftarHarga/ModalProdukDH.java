package com.c.cpayid.feature.DaftarHarga;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

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

public class ModalProdukDH extends BottomSheetDialogFragment {


    RecyclerView recyclerViewK;
    AdapterProdukDH adapterProdukDH;
    ArrayList<ResponProdukDH.mData> mData = new ArrayList<>();
    Button tutup, pilih;
    protected BottomSheetListenerProduk bottomSheetListenerProduk;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_daftarharga, container, false);

        recyclerViewK = v.findViewById(R.id.ReyProdukDH);
        tutup = v.findViewById(R.id.tutupDH);
        pilih = v.findViewById(R.id.pilihDH);
        searchView = v.findViewById(R.id.search_DH);


        adapterProdukDH = new AdapterProdukDH(ModalProdukDH.this,getContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewK.setLayoutManager(mLayoutManager);
        recyclerViewK.setAdapter(adapterProdukDH);

        getProdukDH();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterProdukDH.getFilter().filter(newText);
                return false;
            }
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nameid[][] = adapterProdukDH.getNameid();

                String namee = nameid[0][0];
                String id = nameid[0][1];


                bottomSheetListenerProduk.onButtonClick(namee, id);
                Preference.setName(getContext(),"");
                dismiss();
            }
        });

        return v;
    }

    private void getProdukDH() {
        String token = "Bearer "+ Preference.getToken(getContext());

        Api api = RetroClient.getApiServices();
        Call<ResponProdukDH> call = api.getProdukDH(token);
        call.enqueue(new Callback<ResponProdukDH>() {
            @Override
            public void onResponse(Call<ResponProdukDH> call, Response<ResponProdukDH> response) {
                for(ResponProdukDH.mData  produk : response.body().getData()){

                    if (produk.getType().equals("PRABAYAR")){

                        mData.add(produk);
                    }
                }
                adapterProdukDH = new AdapterProdukDH(ModalProdukDH.this,getContext(), mData);
                recyclerViewK.setAdapter(adapterProdukDH);
            }

            @Override
            public void onFailure(Call<ResponProdukDH> call, Throwable t) {

            }
        });

    }

    public interface BottomSheetListenerProduk {
        void onButtonClick(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerProduk = (BottomSheetListenerProduk) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

}
