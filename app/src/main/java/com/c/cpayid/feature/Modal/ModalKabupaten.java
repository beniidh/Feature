package com.c.cpayid.feature.Modal;

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

import com.c.cpayid.feature.Adapter.AdapterKabupaten;

import com.c.cpayid.feature.Adapter.AdapterProvinsi;
import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Respon.ResponK;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.ModelKabupaten;

import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalKabupaten extends BottomSheetDialogFragment {

    RecyclerView recyclerViewK;
    AdapterKabupaten adapterKabupaten;
    ArrayList<ModelKabupaten> modelKabupatens = new ArrayList<>();
    Button tutup, pilih;
    AdapterProvinsi adapterProvinsi;
    public BottomSheetListenerKabupaten bottomSheetListenerKabupaten;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_kabupaten, container, false);

        recyclerViewK = v.findViewById(R.id.ReyKabupaten);
        tutup = v.findViewById(R.id.tutupK);
        pilih = v.findViewById(R.id.pilihK);
        searchView = v.findViewById(R.id.search_kabupaten);


        adapterKabupaten = new AdapterKabupaten(ModalKabupaten.this,getContext(), modelKabupatens);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewK.setLayoutManager(mLayoutManager);
        recyclerViewK.setAdapter(adapterKabupaten);

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

                adapterKabupaten.getFilter().filter(newText);
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

                String id = Preference.getID(getContext());
                String name = Preference.getName(getContext());
                bottomSheetListenerKabupaten.onButtonClickKabupaten(name, id);
                Preference.setName(getContext(),"");
                dismiss();
            }
        });

        return v;
    }

    private void getKabupaten() {


        String nameid[][] = AdapterProvinsi.getNameid();


         String idprovinsi = getArguments().getString("provinsikey");
        long id;
        if (idprovinsi.isEmpty()){

            id = Long.parseLong(Preference.getIDProvinsi(getContext()));
         }else {
            id = Long.parseLong(getArguments().getString("provinsikey"));
        }



        Api api = RetroClient.getApiServices();
        Call<ResponK> call = api.getAllKabupaten(id);
        call.enqueue(new Callback<ResponK>() {
            @Override
            public void onResponse(Call<ResponK> call, Response<ResponK> response) {

                modelKabupatens = (ArrayList<ModelKabupaten>) response.body().getData();
                adapterKabupaten = new AdapterKabupaten(ModalKabupaten.this,getContext(), modelKabupatens);
                recyclerViewK.setAdapter(adapterKabupaten);
            }

            @Override
            public void onFailure(Call<ResponK> call, Throwable t) {

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
