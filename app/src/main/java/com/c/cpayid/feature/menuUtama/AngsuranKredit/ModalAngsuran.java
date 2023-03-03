package com.c.cpayid.feature.menuUtama.AngsuranKredit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
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

public class ModalAngsuran extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterAngsuran adapterAngsuran;
    ArrayList<ModelAngsuran> modelAngsurans = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_angsuran, container, false);



        recyclerView = v.findViewById(R.id.ReyAngsuran);
        adapterAngsuran = new AdapterAngsuran(ModalAngsuran.this,getContext(), modelAngsurans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterAngsuran);

        pilih = v.findViewById(R.id.pilihAngsuran);
        tutup = v.findViewById(R.id.tutupAngsuran);
        String idd = getArguments().getString("id");
        getProduk(idd);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterAngsuran.getNameid();

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


        search = v.findViewById(R.id.search_angsuran);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.onActionViewExpanded();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterAngsuran.getFilter().filter(newText);
                return false;
            }
        });




        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id);
    }
    private void getProduk(String id){

        String token = "Bearer "+ Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponAngsuran> call = api.getProdukAngsuran(token,id);
        call.enqueue(new Callback<ResponAngsuran>() {
            @Override
            public void onResponse(Call<ResponAngsuran> call, Response<ResponAngsuran> response) {

                modelAngsurans = response.body().getData();
                adapterAngsuran = new AdapterAngsuran(ModalAngsuran.this,getContext(), modelAngsurans);
                recyclerView.setAdapter(adapterAngsuran);
            }

            @Override
            public void onFailure(Call<ResponAngsuran> call, Throwable t) {
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
