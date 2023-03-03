package com.c.cpayid.feature.MarkUP.markupSpesifik;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.MarkUP.ResponMarkup;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalInputSpesifikMarkup extends BottomSheetDialogFragment {

Button markupButtonMS;
TextView produkNameMS;
EditText markupNilaiMS;
    public BottomSheetListenerProdukS bottomSheetListenerProduk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_spesifik_mu, container, false);

        markupButtonMS = v.findViewById(R.id.markupButtonMS);
        produkNameMS = v.findViewById(R.id.produkNameMS);
        markupNilaiMS = v.findViewById(R.id.markupNilaiMS);

        produkNameMS.setText(getArguments().getString("name"));
        String id = getArguments().getString("id");
        String produkmasterid = getArguments().getString("produkmasterid");


        markupButtonMS.setOnClickListener(view -> {
            if(!markupNilaiMS.getText().toString().isEmpty()){
                setHarga(id,produkmasterid,markupNilaiMS.getText().toString(),getContext());
                bottomSheetListenerProduk.onButtonClickS("test","test");
                dismiss();
            }else {
                Toast.makeText(getContext(),"harga tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }

        });
        return v;

    }

    private void setHarga(String id, String product_master_id, String markup_price, Context context){

        String token ="Bearer "+ Preference.getToken(getContext());

        RequestBody productMaster = RequestBody.create(MediaType.parse("text/plain"), product_master_id);
        RequestBody Markup = RequestBody.create(MediaType.parse("text/plain"), markup_price);


        Api api = RetroClient.getApiServices();
        Call<ResponMarkup> call = api.markupSpesifik(token,id,productMaster,Markup);
        call.enqueue(new Callback<ResponMarkup>() {
            @Override
            public void onResponse(Call<ResponMarkup> call, Response<ResponMarkup> response) {
                if(response.body().getCode().equals("200")){
                    Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(getContext(),response.body().getError(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponMarkup> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });





    }

    public interface BottomSheetListenerProdukS {
        void onButtonClickS(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerProduk = (BottomSheetListenerProdukS) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}