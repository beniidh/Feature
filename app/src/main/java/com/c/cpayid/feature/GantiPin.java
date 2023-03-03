package com.c.cpayid.feature;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.mResetPassword;
import com.c.cpayid.feature.Respon.ResponResetPassword;
import com.c.cpayid.feature.databinding.ActivityGantiPinBinding;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPin extends BaseActivity {

    private ActivityGantiPinBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGantiPinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Lupa PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        binding.ResetPINGanti.setOnClickListener(v -> {
            if (binding.inputEmailGantiPin.getText().toString().isEmpty()) {
                StyleableToast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            } else {
                resetPassword(binding.inputEmailGantiPin.getText().toString());
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColor(binding.ResetPINGanti.getBackground(), "green");
        DrawableMap.changeColorStroke(binding.ResetPINGanti.getBackground(), "green");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void resetPassword(String email) {
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        Api api = RetroClient.getApiServices();
        mResetPassword mResetPassword = new mResetPassword(email, Value.getIPaddress(),
                Value.getMacAddress(getApplicationContext()),
                Value.getUserAgent(getApplicationContext()),
                gpsTracker.getLongitude(), gpsTracker.getLatitude());

        Call<ResponResetPassword> call = api.resetPassword(mResetPassword);
        call.enqueue(new Callback<ResponResetPassword>() {
            @Override
            public void onResponse(@NonNull Call<ResponResetPassword> call, @NonNull Response<ResponResetPassword> response) {
                assert response.body() != null;
                String respon = response.body().getCode();
                if (respon.equals("200")) {
                    binding.keterangangantipin.setText(response.body().getData().getMessage());
                } else {
                    binding.keterangangantipin.setText(response.body().getError());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponResetPassword> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}