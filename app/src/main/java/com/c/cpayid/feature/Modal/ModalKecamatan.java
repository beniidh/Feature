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

import com.c.cpayid.feature.Adapter.AdapterKecamatan;
import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Respon.ResponKe;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.ModelKecamatan;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalKecamatan extends BottomSheetDialogFragment {


    RecyclerView recyclerViewKe;
    AdapterKecamatan adapterKecamatan;
    ArrayList<ModelKecamatan> modelKecamatans = new ArrayList<>();
    Button tutup, pilih;
    public BottomSheetListenerKecamatan bottomSheetListenerKecamatan;
    SearchView searchViewKe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_kecamatan,container,false);

        recyclerViewKe = v.findViewById(R.id.ReyKecamatan);
        tutup = v.findViewById(R.id.tutupKe);
        pilih = v.findViewById(R.id.pilihKe);

        adapterKecamatan = new AdapterKecamatan(ModalKecamatan.this,getContext(), modelKecamatans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewKe.setLayoutManager(mLayoutManager);
        recyclerViewKe.setAdapter(adapterKecamatan);

        searchViewKe = v.findViewById(R.id.search_kecamatan);

        searchViewKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewKe.onActionViewExpanded();
            }
        });
        searchViewKe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterKecamatan.getFilter().filter(newText);
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

                String id =  Preference.getID(getContext());
                String name = Preference.getName(getContext());

                bottomSheetListenerKecamatan.onButtonClickKecamatan(name,id);
                Preference.setName(getContext(),"");
                dismiss();
            }
        });


        getKecamatan();
        return v;

    }

    private void getKecamatan() {

        String idkabupaten = getArguments().getString("kabupatenkey");
        long id;
        if (idkabupaten.isEmpty()){

            id = Long.parseLong(Preference.getIDKabupaten(getContext()));
        }else {
            id = Long.parseLong(getArguments().getString("kabupatenkey"));
        }


        Api api = RetroClient.getApiServices();
        Call<ResponKe> call = api.getAllKecamatan(id);
        call.enqueue(new Callback<ResponKe>() {
            @Override
            public void onResponse(Call<ResponKe> call, Response<ResponKe> response) {

                modelKecamatans = (ArrayList<ModelKecamatan>) response.body().getData();
                adapterKecamatan = new AdapterKecamatan(ModalKecamatan.this,getContext(), modelKecamatans);
                recyclerViewKe.setAdapter(adapterKecamatan);

            }

            @Override
            public void onFailure(Call<ResponKe> call, Throwable t) {

            }
        });
    }

    public interface BottomSheetListenerKecamatan {
        void onButtonClickKecamatan(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerKecamatan = (BottomSheetListenerKecamatan) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
