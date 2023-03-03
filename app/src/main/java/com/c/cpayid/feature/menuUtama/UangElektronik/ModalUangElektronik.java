package com.c.cpayid.feature.menuUtama.UangElektronik;

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

public class ModalUangElektronik extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterUangElektronik adapterUangElektronik;
    ArrayList<MUangElektronik> mUangElektroniks = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_uangelektronik, container, false);


        recyclerView = v.findViewById(R.id.ReyUangElektronik);
        adapterUangElektronik = new AdapterUangElektronik(ModalUangElektronik.this,getContext(), mUangElektroniks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterUangElektronik);

        pilih = v.findViewById(R.id.pilihUangElektronik);
        tutup = v.findViewById(R.id.tutupUangElektronik);

        String idd = getArguments().getString("id");
        getProdukUE(idd);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterUangElektronik.getNameid();

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


        search = v.findViewById(R.id.search_uangelektronik);
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

                adapterUangElektronik.getFilter().filter(newText);
                return false;
            }
        });




        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id);
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

    public void getProdukUE(String id){
        String token = "Bearer "+ Preference.getToken(getContext());

        Api api = RetroClient.getApiServices();
        Call<ResponUangElektronik> call = api.getProdukCategoryUE(token,id);
        call.enqueue(new Callback<ResponUangElektronik>() {
            @Override
            public void onResponse(Call<ResponUangElektronik> call, Response<ResponUangElektronik> response) {
                String code = response.body().getCode();
                if(code.equals("200")){
                    mUangElektroniks = response.body().getData();
                    adapterUangElektronik = new AdapterUangElektronik(ModalUangElektronik.this,getContext(), mUangElektroniks);
                    recyclerView.setAdapter(adapterUangElektronik);
                }else {

                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponUangElektronik> call, Throwable t) {

            }
        });

    }
}
