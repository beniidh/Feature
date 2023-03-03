package com.c.cpayid.feature.SaldoServer;

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
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinUPP extends BottomSheetDialogFragment {

    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;
    String roless;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpengajuan_server, container, false);

        pinpengajuan = v.findViewById(R.id.pinpengajuan);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuan);
        idPinPengajuanButton = v.findViewById(R.id.idPinPengajuanButton);
        idCancelPengajuan.setOnClickListener(v12 -> dismiss());
        getContentProfil();

        idPinPengajuanButton.setOnClickListener(v1 -> {


            if (pinpengajuan.length() < 6) {
                StyleableToast.makeText(getContext(), "Lengkapi PIN", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            } else {
                GpsTracker gpsTracker = new GpsTracker(getContext());
                String pinn = utils.hmacSha(Objects.requireNonNull(pinpengajuan.getText()).toString());
                String jumlahpengajuan = Preference.getSaldoServer(getContext());
                double pengajuan = Double.parseDouble(jumlahpengajuan);
                if(getRoless().equals("4")){
                    ajukanUPP(Preference.getIdUPP(getContext()),"SERVER", pinn, utils.getMacAddr(),
                            Value.getIPaddress(), Value.getUserAgent(getContext()), gpsTracker.getLatitude(),
                            gpsTracker.getLongitude(), pengajuan);
                }else {

                    ajukanUPP(Preference.getIdUPP(getContext()),"SALES", pinn, utils.getMacAddr(),
                            Value.getIPaddress(), Value.getUserAgent(getContext()), gpsTracker.getLatitude(),
                            gpsTracker.getLongitude(), pengajuan);
                }


            }
        });

        return v;

    }

    private void ajukanUPP(String id,String type, String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double totalbil) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getContext());
        AddUPP upp = new AddUPP(id, pin, mac_address, ip_address, user_agent, latitude, longitude, type, totalbil);

        Call<ResponUPP> call = api.SetUPP(token, upp);
        call.enqueue(new Callback<ResponUPP>() {
            @Override
            public void onResponse(Call<ResponUPP> call, Response<ResponUPP> response) {
                assert response.body() != null;
                String code = response.body().getCode();

                if (code.equals("200")) {

                    StyleableToast.makeText(getContext(), response.body().getData().getStatus(), Toast.LENGTH_LONG, R.style.mytoast2).show();
                    dismiss();
                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_LONG, R.style.mytoast2).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponUPP> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                dismiss();
            }
        });

    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
              setRoless(response.body().getData().getRoles());
            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public String getRoless() {
        return roless;
    }

    public void setRoless(String roless) {
        this.roless = roless;
    }
}
