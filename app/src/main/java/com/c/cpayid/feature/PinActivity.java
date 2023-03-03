package com.c.cpayid.feature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Model.Mlogin;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessaging;
import io.github.muddz.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinActivity extends BaseActivity {

    ProgressBar progressBar;
    PinEditText pin1;
    String telepon;
    GpsTracker gpsTracker;
    int salah = 0;
    TextView warningpinsalah;
    String deviceToken;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_activity);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Insert PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        warningpinsalah = findViewById(R.id.warningpinsalah);
        warningpinsalah.setOnClickListener(v -> {
            Intent intent = new Intent(PinActivity.this, GantiPin.class);
            startActivity(intent);
        });

        getLocation();

        progressBar = findViewById(R.id.progressPIN);

        pin1 = findViewById(R.id.pinEditText);
        pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pin1.length() == 6) {
                    progressBar.setVisibility(View.VISIBLE);
                    String pinn = Objects.requireNonNull(pin1.getText()).toString();
                    String pinenkrip = utils.hmacSha(pinn);
                    Login(pinenkrip);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvPinLabel)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        warningpinsalah.setText(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PinActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void Login(String pin) {
        double longlitude = gpsTracker.getLongitude();
        double latitude = gpsTracker.getLatitude();
        String useragent = getUserAgent();
        String IP = getIPaddress();
        Intent tlp = getIntent();

        Preference.getSharedPreference(getApplicationContext());
        telepon = Preference.getKredentials(getApplicationContext());

        if (telepon.equals("")) {
            telepon = tlp.getStringExtra("number");
        }

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            String deviceToken = task.getResult();
            Mlogin mlogin = new Mlogin("SRVID00000014", telepon, pin, deviceToken, IP, Value.getMacAddress(getApplicationContext()), useragent, longlitude, latitude);

            Api api = RetroClient.getApiServices();
            Call<Mlogin> call = api.Login(mlogin);
            call.enqueue(new Callback<Mlogin>() {
                @Override
                public void onResponse(@NonNull Call<Mlogin> call, @NonNull Response<Mlogin> response) {
                    assert response.body() != null;
                    String code = response.body().getCode();
                    if (code.equals("200")) {
                        progressBar.setVisibility(View.GONE);
                        Intent home = new Intent(PinActivity.this, DrawerActivity.class);
                        startActivity(home);
                        String token = response.body().getData().getToken();
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setToken(getApplicationContext(), token);
                        finish();
                    } else if (code.equals("403")) {
                        StyleableToast.makeText(getApplicationContext(), response.body().getError() + " Silahkan hubungi Admin", Toast.LENGTH_LONG, R.style.mytoast).show();
                        Intent intent = new Intent(PinActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        pin1.setText("");
                        StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                        salah += 1;
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Mlogin> call, @NonNull Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }
            });
        });
    }


    private String getUserAgent() {
        return new WebView(this).getSettings().getUserAgentString();
    }

    private String getIPaddress() {
        return utils.getIPAddress(true);
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(PinActivity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
        } else {
            gpsTracker.showSettingsAlert();
        }
    }
}