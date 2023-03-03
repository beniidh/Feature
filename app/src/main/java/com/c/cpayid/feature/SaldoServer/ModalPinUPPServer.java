package com.c.cpayid.feature.SaldoServer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinUPPServer extends BottomSheetDialogFragment {

    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;
    private BottomSheetListeneridUpload bottomSheetListeneridUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpengajuan_server, container, false);

        pinpengajuan = v.findViewById(R.id.pinpengajuan);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuan);
        idPinPengajuanButton = v.findViewById(R.id.idPinPengajuanButton);
        idCancelPengajuan.setOnClickListener(v12 -> dismiss());
        idPinPengajuanButton.setOnClickListener(v1 -> {

            if (pinpengajuan.length() < 6) {
                StyleableToast.makeText(getContext(), "Lengkapi PIN", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            } else {
                GpsTracker gpsTracker = new GpsTracker(getContext());
                String pinn = utils.hmacSha(Objects.requireNonNull(pinpengajuan.getText()).toString());
                String jumlahpengajuan = Preference.getSaldoServer(getContext());
                double pengajuan = Double.parseDouble(jumlahpengajuan);
                ajukanUPP(Preference.getIdUPP(getContext()),pinn, Value.getMacAddress(getContext()), Value.getIPaddress(), Value.getUserAgent(getContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude(), pengajuan);
            }
        });

        return v;

    }

    private void ajukanUPP(String id, String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double totalbil) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getContext());
        AddUPP upp = new AddUPP(id, pin, mac_address, ip_address, user_agent, latitude, longitude, "SALES", totalbil);

        Call<ResponUPP> call = api.SetUPP(token, upp);
        call.enqueue(new Callback<ResponUPP>() {
            @Override
            public void onResponse(Call<ResponUPP> call, Response<ResponUPP> response) {
                assert response.body() != null;
                String code = response.body().getCode();

                if (code.equals("200")) {

                    String idd = response.body().getData().getId();

                    bottomSheetListeneridUpload.onButtonClickIdUpload(idd);

                    StyleableToast.makeText(getContext(), response.body().getData().getStatus(), Toast.LENGTH_LONG, R.style.mytoast2).show();
                    dismiss();

                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_LONG, R.style.mytoast2).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponUPP> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_LONG, R.style.mytoast2).show();

            }
        });

    }
    public interface BottomSheetListeneridUpload {
        void onButtonClickIdUpload(String id);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListeneridUpload = (BottomSheetListeneridUpload) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }





}
