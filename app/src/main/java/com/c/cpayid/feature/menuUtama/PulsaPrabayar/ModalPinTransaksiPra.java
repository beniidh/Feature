package com.c.cpayid.feature.menuUtama.PulsaPrabayar;

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
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinTransaksiPra extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PinEditText pinpengajuan;
    TextView idCancelPengajuanTransaksi;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pintransaksiprapulsa, container, false);

        pinpengajuan = v.findViewById(R.id.pinpengajuantransaksipra);
        idCancelPengajuanTransaksi = v.findViewById(R.id.idCancelPengajuanTransaksi);

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

                    TransaksiPulsaPra(pin);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;

    }

    public void TransaksiPulsaPra(String pin) {



        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        GpsTracker gpsTracker = new GpsTracker(getContext());
        String sku_code = getArguments().getString("sku_code");
        String customer_code = getArguments().getString("nomorr");
        String ref_id = getArguments().getString("RefID");
        String type = getArguments().getString("inquiry");
        String wallet_type = getArguments().getString("wallettype");

        MTransaksiPraPulsa mTransaksiPraPulsa = new MTransaksiPraPulsa(sku_code, customer_code, ref_id, type, wallet_type, Value.getMacAddress(getContext()), Value.getIPaddress(), Value.getUserAgent(getContext()), pin, gpsTracker.getLongitude(), gpsTracker.getLatitude());

        Call<ResponTransaksiPulsaPra> call = api.transalsiPulsaPra(token, mTransaksiPraPulsa);
        call.enqueue(new Callback<ResponTransaksiPulsaPra>() {
            @Override
            public void onResponse(Call<ResponTransaksiPulsaPra> call, Response<ResponTransaksiPulsaPra> response) {

                if (response.body().getCode().equals("200")) {
                    Intent intent = new Intent(getContext(), TransaksiPending.class);
                    intent.putExtra("pesan",response.body().getData().getMessage());
                    intent.putExtra("hargatotal",response.body().getData().getTotal_price());
                    intent.putExtra("nomorcustomer",response.body().getData().getCustomer_no());
                    intent.putExtra("create_at",response.body().getData().getCreated_at());
                    intent.putExtra("transaksid",response.body().getData().getRef_id());
                    intent.putExtra("iconn",getArguments().getString("iconn"));
                    startActivity(intent);
                    KonfirmasiPembayaran.konifirmpembayaran.finish();
                    dismiss();

                } else {
                    StyleableToast.makeText(getContext(), response.body().getError(), Toast.LENGTH_LONG,R.style.mytoast2).show();
                    dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponTransaksiPulsaPra> call, Throwable t) {

            }
        });


    }


}
