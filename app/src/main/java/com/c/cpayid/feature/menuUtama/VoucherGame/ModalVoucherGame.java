package com.c.cpayid.feature.menuUtama.VoucherGame;

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

public class ModalVoucherGame extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterVoucherGame adapterVoucherGame;
    ArrayList<MVoucherGame> mVoucherGames = new ArrayList<>();
    String id;
    Button pilih,tutup;
    protected BottomSheetListenerVoucherGame bottomSheetListenerVoucherGame;

    public ModalVoucherGame(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_vouchergame, container, false);
        getProdukGame(id);
        recyclerView = v.findViewById(R.id.ReyProdukGame);

        adapterVoucherGame = new AdapterVoucherGame(getContext(), mVoucherGames, ModalVoucherGame.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterVoucherGame);
        pilih = v.findViewById(R.id.pilihVoucherGame);
        tutup = v.findViewById(R.id.tutupVoucherGame);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterVoucherGame.getNameid();

            String namee = nameid[0][0];
            String id = nameid[0][1];

            bottomSheetListenerVoucherGame.onButtonClickKabupaten(namee,id);
            dismiss();
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dismiss();
            }
        });

        return  v;

    }

    private void getProdukGame(String id){

        String token = "Bearer "+ Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponVoucherGame> call = api.getProdukVoucherGame(token,id);
        call.enqueue(new Callback<ResponVoucherGame>() {
            @Override
            public void onResponse(Call<ResponVoucherGame> call, Response<ResponVoucherGame> response) {

                mVoucherGames = response.body().getData();
                adapterVoucherGame = new AdapterVoucherGame(getContext(), mVoucherGames, ModalVoucherGame.this);
                recyclerView.setAdapter(adapterVoucherGame);

            }

            @Override
            public void onFailure(Call<ResponVoucherGame> call, Throwable t) {
                Toast.makeText(getContext(),"Gagal",Toast.LENGTH_SHORT).show();

            }
        });

    }


    public interface BottomSheetListenerVoucherGame {
        void onButtonClickKabupaten(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerVoucherGame = (BottomSheetListenerVoucherGame) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }


}
