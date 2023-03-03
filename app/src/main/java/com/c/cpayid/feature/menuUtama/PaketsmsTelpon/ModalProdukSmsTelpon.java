package com.c.cpayid.feature.menuUtama.PaketsmsTelpon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class ModalProdukSmsTelpon extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterProdukSmsTelpon adapterProdukSmsTelpon;
    ArrayList<MProdukPaketSmsT> msmsTelpons = new ArrayList<>();
    Button pilih, tutup;
    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_produksmspulsa, container, false);

        getProdukSmsTelpon();
        recyclerView = v.findViewById(R.id.RecycleProduksmsTelpon);
        adapterProdukSmsTelpon = new AdapterProdukSmsTelpon(ModalProdukSmsTelpon.this,getContext(), msmsTelpons);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukSmsTelpon);

        pilih = v.findViewById(R.id.pilihProdukSmsTelpon);
        tutup = v.findViewById(R.id.tutupProdukSmsTelpon);

        pilih.setOnClickListener(v1 -> {

            String nameid[][] = adapterProdukSmsTelpon.getNameid();

            String namee = nameid[0][0];
            String id = nameid[0][1];
            String icon = nameid[0][2];
            bottomSheetListenerProduksms.onButtonClick(namee, id,icon);
            dismiss();
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id,String icon);
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

    public void getProdukSmsTelpon() {

        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukSmsTelp> call = api.getProdukSmsTelpon(token, "CATID062502100000007");
        call.enqueue(new Callback<ResponProdukSmsTelp>() {
            @Override
            public void onResponse(Call<ResponProdukSmsTelp> call, Response<ResponProdukSmsTelp> response) {
                String code = response.body().getCode();

                if (code.equals("200")) {
                    msmsTelpons = response.body().getData();
                    adapterProdukSmsTelpon = new AdapterProdukSmsTelpon(ModalProdukSmsTelpon.this,getContext(), msmsTelpons);
                    recyclerView.setAdapter(adapterProdukSmsTelpon);
                }
            }
            @Override
            public void onFailure(Call<ResponProdukSmsTelp> call, Throwable t) {

            }
        });
    }
}
