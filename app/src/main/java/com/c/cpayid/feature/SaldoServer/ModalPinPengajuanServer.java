package com.c.cpayid.feature.SaldoServer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
import com.c.cpayid.feature.PengajuanLimit.PengajuanDompet;
import com.c.cpayid.feature.PengajuanLimit.SendPengajuan;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinPengajuanServer extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PengajuanDompet pengajuanDompet;
    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;
    AjukanLimit ajukanLimit;

    public ModalPinPengajuanServer(AjukanLimit ajukanLimit) {
        this.ajukanLimit =ajukanLimit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpengajuanserver, container, false);
        getLocation();

        ((TextView) v.findViewById(R.id.tvInputPinLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        pinpengajuan = v.findViewById(R.id.pinpengajuanServer);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuanServer);
        idCancelPengajuan.setOnClickListener(v12 -> dismiss());
        idCancelPengajuan.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));

        pinpengajuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pinpengajuan.length() == 6) {
                    assert getArguments() != null;
                    String jumlahpengajuan = getArguments().getString("saldo");
                    double pengajuan = Double.parseDouble(jumlahpengajuan);
                    String pinn = utils.hmacSha(Objects.requireNonNull(pinpengajuan.getText()).toString());

                    ajukanLimit(pinn, Value.getMacAddress(getContext()), getIPaddress(), getUserAgent(), gpsTracker.getLatitude(), gpsTracker.getLongitude(), pengajuan);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void ajukanLimit(String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double amount) {
        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getContext());

        SendPengajuan pengajuan = new SendPengajuan(pin, mac_address, ip_address, user_agent, latitude, longitude, amount);

        Call<SendPengajuan> call = api.SetPayLetter(token, pengajuan);
        call.enqueue(new Callback<SendPengajuan>() {
            @Override
            public void onResponse(@NonNull Call<SendPengajuan> call, @NonNull Response<SendPengajuan> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    ajukanLimit.getStatusPayLatter();

//                    pengajuanDompet.idPengajuanLimitEditText.setText("");
                    dismiss();

                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendPengajuan> call, @NonNull Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    private String getUserAgent() {
        return new WebView(getContext()).getSettings().getUserAgentString();
    }

    private String getIPaddress() {
        return utils.getIPAddress(true);
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
    }
}
