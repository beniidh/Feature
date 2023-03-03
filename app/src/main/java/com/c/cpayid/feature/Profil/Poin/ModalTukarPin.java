package com.c.cpayid.feature.Profil.Poin;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalTukarPin extends BottomSheetDialogFragment {

    PinEditText pinpersetujuan;
    TextView idCancelPersetujuan;
    public callback Callbackk;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpersetujuan, container, false);

        pinpersetujuan = v.findViewById(R.id.pinpersetujuan);
        idCancelPersetujuan = v.findViewById(R.id.idCancelPersetujuan);
        idCancelPersetujuan.setOnClickListener(v1 -> dismiss());
        String pinID = getArguments().getString("poinID");

        pinpersetujuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (pinpersetujuan.length() == 6) {

                    TukarPoin(utils.hmacSha(pinpersetujuan.getText().toString()),pinID);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }


    private void TukarPoin(String pin,String poinID){
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        mExcange mexcange = new mExcange(poinID,pin, Value.getMacAddress(getContext()),
                Value.getIPaddress(), Value.getUserAgent(getContext()),
                Double.parseDouble(Preference.getLong(getContext())),
                Double.parseDouble(Preference.getLang(getContext())));

        Call<mExcange> call = api.tukarPoint(token,mexcange);
        call.enqueue(new Callback<mExcange>() {
            @Override
            public void onResponse(Call<mExcange> call, Response<mExcange> response) {

                if(response.body().code.equals("200")){
                    Toast.makeText(getContext(),"Poin Berhasil ditukar, Untuk detail silahkan hubungi CS",Toast.LENGTH_LONG).show();
                    Callbackk.onClick("update");
                    dismiss();
                }else {

                    Toast.makeText(getContext(),response.body().error,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<mExcange> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();


            }
        });

    }

    public interface callback{
        void onClick(String hasil);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            Callbackk = (callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

}
