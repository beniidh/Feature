package com.c.cpayid.feature.Modal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Profil.MPin;
import com.c.cpayid.feature.Profil.UbahPin;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalPinBaru extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PinEditText pinsatuuu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinbaru, container, false);
        getLocation();

        pinsatuuu = v.findViewById(R.id.pinsatuuu);
        pinsatuuu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(pinsatuuu.length()==6){

                    setPinBaru(pinsatuuu.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    public void setPinBaru(String pinbaru) {

        String pin = getArguments().getString("PINedit");
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        MPin mPin = new MPin(pin, pinbaru, Value.getMacAddress(getContext()), getIPaddress(), getUserAgent(), gpsTracker.getLatitude(), gpsTracker.getLatitude());
        Call<MPin> call = api.UbahPin(token, mPin);
        call.enqueue(new Callback<MPin>() {
            @Override
            public void onResponse(Call<MPin> call, Response<MPin> response) {
                String code = response.body().getCode();
                if(code.equals("200")){
                    StyleableToast.makeText(getContext(),"PIN berhasil diubah", Toast.LENGTH_SHORT,R.style.mytoast3).show();
                    UbahPin.pin.finish();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<MPin> call, Throwable t) {

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
