package com.c.cpayid.feature.TarikKomisi;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalTarikKomisi extends BottomSheetDialogFragment {

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

        pinpersetujuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (pinpersetujuan.length() == 6) {

                    TarikSaldo(utils.hmacSha(pinpersetujuan.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void TarikSaldo(String pin) {
        String token = "Bearer " + Preference.getToken(getContext());
        String jumlah = getArguments().getString("jumlah");
        mKomisi komisi = new mKomisi(pin,"SALDOKU", Value.getMacAddress(getContext()),
                Value.getIPaddress(), Value.getUserAgent(getContext()),Double.parseDouble(jumlah),
                Double.parseDouble(Preference.getLong(getContext())),
                Double.parseDouble(Preference.getLang(getContext())));

        Api api = RetroClient.getApiServices();
        Call<ResponKomisi> call = api.withdrawKomisi(token, komisi);
        call.enqueue(new Callback<ResponKomisi>() {
            @Override
            public void onResponse(Call<ResponKomisi> call, Response<ResponKomisi> response) {
                String code = response.body().getCode();

                if (code.equals("200")) {
                    Toast.makeText(getContext(), "Tarik saldo berhasil", Toast.LENGTH_LONG).show();
                    Callbackk.onClick("Berhasil");
                    dismiss();

                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_LONG, R.style.mytoast2).show();
                    dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponKomisi> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                Log.d("pesan", t.getMessage());
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
