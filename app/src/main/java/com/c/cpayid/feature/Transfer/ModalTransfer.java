package com.c.cpayid.feature.Transfer;

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

import com.c.cpayid.feature.Adapter.AdapterProvinsi;
import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalTransfer extends BottomSheetDialogFragment {

    RecyclerView recyclerViewK;
    AdapterTransfer adapterTransfer;
    ArrayList<ModelKonter.data> modelkonter = new ArrayList<>();
    Button tutup, pilih;

    private BottomSheetListenerKabupaten bottomSheetListenerKabupaten;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_transfer, container, false);

        recyclerViewK = v.findViewById(R.id.ReyTransfer);
        tutup = v.findViewById(R.id.tutupK);
        pilih = v.findViewById(R.id.pilihK);
        searchView = v.findViewById(R.id.search_transfer);


        adapterTransfer = new AdapterTransfer(getContext(), modelkonter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewK.setLayoutManager(mLayoutManager);
        recyclerViewK.setAdapter(adapterTransfer);

        getKabupaten();

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

                adapterTransfer.getFilter().filter(newText);
                return false;
            }
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pilih.setOnClickListener(v1 -> {

            String nameid[][] = AdapterTransfer.getNameid();

            String namee = nameid[0][0];
            String id = nameid[0][1];

            bottomSheetListenerKabupaten.onButtonClickKabupaten(namee, id);
            Preference.setName(getContext(), "");
            dismiss();
        });

        return v;
    }

    private void getKabupaten() {


        String nameid[][] = AdapterProvinsi.getNameid();

        Api api = RetroClient.getApiServices();
        Call<ModelKonter> call = api.getKonter("Bearer "+ Preference.getToken(getContext()));
        call.enqueue(new Callback<ModelKonter>() {
            @Override
            public void onResponse(Call<ModelKonter> call, Response<ModelKonter> response) {
                ArrayList<ModelKonter.data> modelkontesr = new ArrayList<>();
                modelkonter = response.body().getData();
                if(modelkonter == null){
                    modelkonter = modelkontesr;
                }
                adapterTransfer = new AdapterTransfer(getContext(), modelkonter);
                recyclerViewK.setAdapter(adapterTransfer);
            }

            @Override
            public void onFailure(Call<ModelKonter> call, Throwable t) {

            }
        });

    }

    public interface BottomSheetListenerKabupaten {
        void onButtonClickKabupaten(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerKabupaten = (BottomSheetListenerKabupaten) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

}
