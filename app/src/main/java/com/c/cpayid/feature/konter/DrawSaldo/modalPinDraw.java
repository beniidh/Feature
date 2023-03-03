package com.c.cpayid.feature.konter.DrawSaldo;

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
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class modalPinDraw extends BottomSheetDialogFragment {


    GpsTracker gpsTracker;
    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pintransfer, container, false);
        getLocation();

        pinpengajuan = v.findViewById(R.id.pinpengajuantransfer);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuantransfer);
        idPinPengajuanButton = v.findViewById(R.id.idPinPengajuanButtontransfer);
        idCancelPengajuan.setOnClickListener(v12 -> dismiss());
        pinpengajuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(pinpengajuan.length() ==6){

                    String pinn = utils.hmacSha(Objects.requireNonNull(pinpengajuan.getText()).toString());
                    String jumlahtransfer = getArguments().getString("nominal");
                    double pengajuan = Double.parseDouble(jumlahtransfer);
                    sendTransfer(pinn, Value.getMacAddress(getContext()), getIPaddress(), getUserAgent(), gpsTracker.getLatitude(), gpsTracker.getLongitude(), pengajuan);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;

    }

    private void sendTransfer(String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double amount) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getContext());
        mDraw mdraw = new mDraw(mac_address, ip_address, user_agent, pin,getArguments().getString("id"),latitude,longitude,amount);

        Call<mDraw> call = api.drawSaldoku(token, mdraw);
        call.enqueue(new Callback<mDraw>() {
            @Override
            public void onResponse(Call<mDraw> call, Response<mDraw> response) {

                if (response.body().getCode().equals("200")) {
                    StyleableToast.makeText(getContext(),"Penarikan Berhasil",Toast.LENGTH_LONG,R.style.mytoast).show();
                    DrawMyBalance.drawsaldo.finish();
                    dismiss();

                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<mDraw> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            }
        });

    }


    private String getUserAgent() {

        String ua = new WebView(getContext()).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
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
