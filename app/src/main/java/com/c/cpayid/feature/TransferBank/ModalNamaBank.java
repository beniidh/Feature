package com.c.cpayid.feature.TransferBank;

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

public class ModalNamaBank extends BottomSheetDialogFragment {

    RecyclerView recyclerViewP;
    AdapterGetBank adapterGetBank;
    ArrayList<ModelNamaBank.mNama.Product> modelnamabank = new ArrayList<>();
    String rubah; //rubah
    Button tutup, pilih;
    SearchView searchbank;
    protected BottomSheetListener bottomSheetListener;
    String codesub;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_pilihbank, container, false);
        recyclerViewP = v.findViewById(R.id.ReyBank);

        adapterGetBank = new AdapterGetBank(ModalNamaBank.this,getContext(), modelnamabank);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewP.setLayoutManager(mLayoutManager);
        recyclerViewP.setAdapter(adapterGetBank);

        getBankSub(getArguments().getString("id"));
        tutup = v.findViewById(R.id.tutupP);
        pilih = v.findViewById(R.id.pilihP);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameid[][] = adapterGetBank.getNameid();
                String namee = nameid[0][0];
                String id = nameid[0][1];

                bottomSheetListener.onButtonClick(namee, id);
                Preference.setName(getContext(), "");

                dismiss();
            }
        });

        searchbank = v.findViewById(R.id.search_bank);
        searchbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbank.onActionViewExpanded();
            }
        });

        searchbank.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterGetBank.getFilter().filter(newText);
                return false;
            }
        });

        return v;
    }

    private void getNamaBank(String id) {

        Api api = RetroClient.getApiServices();
        Call<ModelNamaBank> call = api.getNamaBank("Bearer " + Preference.getToken(getContext()),id);
        call.enqueue(new Callback<ModelNamaBank>() {
            @Override
            public void onResponse(Call<ModelNamaBank> call, Response<ModelNamaBank> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    modelnamabank = response.body().getData().getProduct();
                    adapterGetBank = new AdapterGetBank(ModalNamaBank.this,getContext(), modelnamabank);
                    recyclerViewP.setAdapter(adapterGetBank);
                } else {
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelNamaBank> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface BottomSheetListener {
        void onButtonClick(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

    private void getBankSub(String id) {
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponBankSub> call = api.getSubCategoryBank(token, id);
        call.enqueue(new Callback<ResponBankSub>() {
            @Override
            public void onResponse(Call<ResponBankSub> call, Response<ResponBankSub> response) {
                String code = response.body().getCode();

                if (code.equals("200")) {
//                    Preference.setSubBank(getContext(),);
                    getNamaBank(response.body().getData().get(0).getId());
                } else {
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponBankSub> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCodesub() {
        return codesub;
    }

    public void setCodesub(String codesub) {
        this.codesub = codesub;
    }
}
