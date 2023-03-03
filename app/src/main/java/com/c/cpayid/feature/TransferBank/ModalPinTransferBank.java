package com.c.cpayid.feature.TransferBank;

import android.content.Intent;
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
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.TransaksiPending;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinTransferBank extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PinEditText pinpengajuan;
    TextView idCancelPengajuanTransaksi;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pintransferbank, container, false);

        pinpengajuan = v.findViewById(R.id.pinpengajuanbank);
        idCancelPengajuanTransaksi = v.findViewById(R.id.idCancelPengajuanBank);

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

                    TransaksiBank(pin);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;

    }

    public void TransaksiBank(String pin) {

        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        GpsTracker gpsTracker = new GpsTracker(getContext());
        String sku_code = getArguments().getString("sku_code");
        String customer_code = getArguments().getString("customer_no");
        String ref_id = getArguments().getString("RefID");
        String harga = getArguments().getString("Amount");
        String wallet_type = getArguments().getString("wallettype");

        MTransfer mTransfer = new MTransfer(sku_code,customer_code,Integer.parseInt(harga),"TRANSFER",wallet_type,ref_id,pin,
                Value.getMacAddress(getContext()), Value.getIPaddress(),
                Value.getUserAgent(getContext()),gpsTracker.getLongitude(),gpsTracker.getLatitude());

        Call<ResponTransfer> call = api.TransferBank(token, mTransfer);
        call.enqueue(new Callback<ResponTransfer>() {
            @Override
            public void onResponse(Call<ResponTransfer> call, Response<ResponTransfer> response) {

                if (response.body().getCode().equals("200")) {
                    Intent intent = new Intent(getContext(), TransaksiPending.class);
                    intent.putExtra("pesan",response.body().getData().getMessage());
                    intent.putExtra("hargatotal",response.body().getData().getTotal_price());
                    intent.putExtra("nomorcustomer",response.body().getData().getCustomer_no());
                    intent.putExtra("create_at",response.body().getData().getCreated_at());
                    intent.putExtra("transaksid",response.body().getData().getRef_id());
                    MetodeBayarBank.metodebayar.finish();
                    startActivity(intent);
                    dismiss();


                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_LONG,R.style.mytoast2).show();
                    dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponTransfer> call, Throwable t) {

            }
        });


    }


}
