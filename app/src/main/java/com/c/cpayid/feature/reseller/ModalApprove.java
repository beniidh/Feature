package com.c.cpayid.feature.reseller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalApprove extends BottomSheetDialogFragment {

    Button setuju, tolak;
    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.approve, container, false);

//        String id = getArguments().getString("idapp");
          setuju = v.findViewById(R.id.setujuSaldoku);
          tolak = v.findViewById(R.id.tolakSaldoku);
        String id = getArguments().getString("ID");
        String harga = getArguments().getString("Harga");

        setuju.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), PilihMetodeBayar.class);
            intent.putExtra("ID",id);
            intent.putExtra("Harga",harga);
            startActivity(intent);
            dismiss();


        });

        tolak.setOnClickListener(view -> {

            GpsTracker gpsTracker = new GpsTracker(getContext());
            String token ="Bearer "+ Preference.getToken(getContext());
            mSetujuSaldo mSetuju = new mSetujuSaldo(id,"REJECT","SALDOKU",
                    Value.getMacAddress(getContext()), Value.getIPaddress(),
                    Value.getUserAgent(getContext()),
                    gpsTracker.getLongitude(),
                    gpsTracker.getLatitude());

            Api api = RetroClient.getApiServices();
            Call<ResponApproveSaldoR> call = api.ApproveSaldokuReselesser(token,mSetuju);
            call.enqueue(new Callback<ResponApproveSaldoR>() {
                @Override
                public void onResponse(Call<ResponApproveSaldoR> call, Response<ResponApproveSaldoR> response) {

                    if(response.body().getCode().equals("200")){
                        Toast.makeText(getContext(),"Berhasil",Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(),response.body().getError(),Toast.LENGTH_SHORT).show();

                    }
                         bottomSheetListener.onButtonClick();
                    dismiss();
                }

                @Override
                public void onFailure(Call<ResponApproveSaldoR> call, Throwable t) {

                }
            });


        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClick();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

}
