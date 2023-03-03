package com.c.cpayid.feature;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Model.MsetPIN;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertPinActivity extends BaseActivity {

    PinEditText pinsatu,pindua;
    Button selesaiInsertPIN;
    GpsTracker gpsTracker;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_p_i_n_activity);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Insert PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        pinsatu = findViewById(R.id.pinsatu);
        pindua = findViewById(R.id.pindua);
        selesaiInsertPIN = findViewById(R.id.selesaiInsertPIN);

        pindua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pinsatuu = Objects.requireNonNull(pinsatu.getText()).toString();
                String pinduaa = Objects.requireNonNull(pindua.getText()).toString();

                if (pindua.length() == 6) {
                    if (pinsatuu.equals(pinduaa)) {
                        selesaiInsertPIN.setOnClickListener(v -> {
                            getLocation();
                            String UserAgent = getUserAgent();
                            String IP = getIPaddress();
                            String pinsattu = pinsatu.getText().toString();
                            String pinddua = pindua.getText().toString();
                            String MacAddres = Value.getMacAddress(getApplicationContext());
                            double longlitut = gpsTracker.getLongitude();
                            double latitude = gpsTracker.getLatitude();
                            Intent intent = getIntent();
                            String token = Preference.getToken(getApplicationContext());

                            String pinsatuenkrip = utils.hmacSha(pinsattu);
                            String pinduaenkrip = utils.hmacSha(pinddua);

                            Api api = RetroClient.getApiServices();
                            MsetPIN msetPIN = new MsetPIN(pinsatuenkrip,pinduaenkrip,MacAddres,IP,UserAgent,longlitut,latitude);
                            Call<MsetPIN> call = api.SetPIN("Bearer "+token,msetPIN);
                            call.enqueue(new Callback<MsetPIN>() {
                                @Override
                                public void onResponse(@NonNull Call<MsetPIN> call, @NonNull Response<MsetPIN> response) {
                                    assert response.body() != null;
                                    String code = response.body().getCode();
                                    String error = response.body().getError();

                                    if (code.equals("200")) {
                                        StyleableToast.makeText(getApplicationContext(),"Set PIN Berhasil ",Toast.LENGTH_SHORT,R.style.mytoast).show();
                                        Intent home = new Intent(InsertPinActivity.this, DrawerActivity.class);
                                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                                      Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                      Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Preference.setTrackRegister(getApplicationContext(),"");
                                        startActivity(home);
                                    } else {
                                      StyleableToast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT,R.style.mytoast).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<MsetPIN> call, @NonNull Throwable t) {
                                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                                }
                            });
                        });
                    } else {
                        StyleableToast.makeText(getApplicationContext(),"PIN tidak sama ",Toast.LENGTH_SHORT,R.style.mytoast).show();
                    }
                }
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvPin)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvConfirm)).setText(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(selesaiInsertPIN.getBackground(), "green");
        DrawableMap.changeColorStroke(selesaiInsertPIN.getBackground(), "green");
    }

    private String getUserAgent() {
        return new WebView(this).getSettings().getUserAgentString();
    }

    private String getIPaddress() {
        return utils.getIPAddress(true);
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(InsertPinActivity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}