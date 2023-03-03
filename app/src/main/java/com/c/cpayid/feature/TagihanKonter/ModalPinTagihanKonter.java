package com.c.cpayid.feature.TagihanKonter;

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
import com.c.cpayid.feature.Helper.GpsTracker;
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


public class ModalPinTagihanKonter extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PinEditText pinpengajuan;
    TextView idCancelPengajuanTransaksi;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pintransaksiprapulsa, container, false);

        pinpengajuan = v.findViewById(R.id.pinpengajuantransaksipra);
        idCancelPengajuanTransaksi = v.findViewById(R.id.idCancelPengajuanTransaksi);
        id = getArguments().getString("idtagihan");

        idCancelPengajuanTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        pinpengajuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (pinpengajuan.length() == 6) {

                    String pin = utils.hmacSha(pinpengajuan.getText().toString());
                    Approve(pin);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;

    }

    public void Approve(String pin) {
        gpsTracker = new GpsTracker(getContext());
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        String aksi = getArguments().getString("aksi");
        SendApprove sendApprove = new SendApprove(id, pin, aksi, Value.getMacAddress(getContext()), Value.getIPaddress(), Value.getUserAgent(getContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
        Call<ResponApprove> call = api.ApproveTagihan(token, sendApprove);
        call.enqueue(new Callback<ResponApprove>() {
            @Override
            public void onResponse(Call<ResponApprove> call, Response<ResponApprove> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    StyleableToast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    dismiss();
                    DetailTagihanKonter.detailtagihan.finish();
                } else {
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                    dismiss();
                    DetailTagihanKonter.detailtagihan.finish();
                }
            }

            @Override
            public void onFailure(Call<ResponApprove> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


    }


}
