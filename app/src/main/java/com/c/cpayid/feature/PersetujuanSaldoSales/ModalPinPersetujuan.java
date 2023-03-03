package com.c.cpayid.feature.PersetujuanSaldoSales;

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
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.PengajuanLimit.PengajuanDompet;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinPersetujuan extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PengajuanDompet pengajuanDompet;
    PinEditText pinpersetujuan;
    TextView idCancelPersetujuan;
   static ModelPersetujuanSaldo modelPersetujuanSaldos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpersetujuan, container, false);


        pinpersetujuan = v.findViewById(R.id.pinpersetujuan);
        idCancelPersetujuan = v.findViewById(R.id.idCancelPersetujuan);

        idCancelPersetujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pinpersetujuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(pinpersetujuan.length() == 6){

                    sendRequestPersetujuan(pinpersetujuan.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void sendRequestPersetujuan(String pin){
        GpsTracker gpsTracker = new GpsTracker(getContext());
        String token = "Bearer " + Preference.getToken(getContext());
        String id = getArguments().getString("ID");
        String status = getArguments().getString("STATUS");
        Api api = RetroClient.getApiServices();
        SendDataPersetujuan sendDataPersetujuan = new SendDataPersetujuan(id, utils.hmacSha(pin),status, Value.getMacAddress(getContext()), Value.getIPaddress(), Value.getUserAgent(getContext()),gpsTracker.getLatitude(),gpsTracker.getLongitude());
        Call<ResponPersetujuan> call = api.sendDataPersetujuan(token,sendDataPersetujuan);
        call.enqueue(new Callback<ResponPersetujuan>() {
            @Override
            public void onResponse(Call<ResponPersetujuan> call, Response<ResponPersetujuan> response) {
                String code = response.body().getCode();

                if(code.equals("200")){
                    StyleableToast.makeText(getContext(),"Berhasil",Toast.LENGTH_SHORT,R.style.mytoast).show();
                    dismiss();
                    DetailPersetujuan.detailPersetujuan.finish();

                }else {

                    StyleableToast.makeText(getContext(),response.body().getError(),Toast.LENGTH_SHORT,R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<ResponPersetujuan> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                Log.d("pesan",t.getMessage());
            }
        });


    }

    public static ModelPersetujuanSaldo getModelPersetujuanSaldos() {
        return modelPersetujuanSaldos;
    }
}
