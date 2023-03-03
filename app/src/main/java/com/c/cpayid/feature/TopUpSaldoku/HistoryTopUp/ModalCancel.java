package com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp;

import android.content.Context;
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
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.mCancel;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalCancel extends BottomSheetDialogFragment {

    Button tidak,ya;
    private BottomSheetListener bottomSheetListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cancletopup, container, false);

        ya = v.findViewById(R.id.ya);
        tidak = v.findViewById(R.id.tidak);

        ya.setOnClickListener(view -> {
            String id = getArguments().getString("id");

            String token = "Bearer " + Preference.getToken(getContext());
            mCancel cancel = new mCancel(id,"CANCEL", Value.getMacAddress(getContext()), Value.getIPaddress(), Value.getUserAgent(getContext()));

            Api api = RetroClient.getApiServices();
            Call<mCancel> call = api.CancelTopup(token,cancel);
            call.enqueue(new Callback<mCancel>() {
                @Override
                public void onResponse(Call<mCancel> call, Response<mCancel> response) {
                    if(response.body().getCode().equals("200")){

                        bottomSheetListener.onButtonClick("Berhasil","321");
                    }else {
                        Toast.makeText(getContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<mCancel> call, Throwable t) {

                }
            });
            dismiss();

        });

        tidak.setOnClickListener(view -> {
            dismiss();

        });

        return v;

    }

    public interface BottomSheetListener {
        void onButtonClick(String name, String id);
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
