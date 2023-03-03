package com.c.cpayid.feature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.Modal.ModalKabupaten;
import com.c.cpayid.feature.Modal.ModalKecamatan;
import com.c.cpayid.feature.Modal.ModalKelurahan;
import com.c.cpayid.feature.Modal.ModalKodePos;
import com.c.cpayid.feature.Modal.ModalProvinsi;
import com.c.cpayid.feature.Model.MRegister;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan, ModalKelurahan.BottomSheetListenerKelurahan, ModalKodePos.BottomSheetListenerPost {

    EditText provinsi, kecamatan, kabupaten, kelurahan, postcode, namapemilik, email, phone, alamatregis, namakonter, referal, serverid;
    ProgressBar progressBar;
    Button regis;
    GpsTracker gpsTracker;
    int province, Regencie, district, subdistrict, postalCode;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Profil Konter<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        // give permission
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getLocation();
        namapemilik = findViewById(R.id.namapemilik);
        email = findViewById(R.id.emailRegis);
        phone = findViewById(R.id.PhoneRegis);
        alamatregis = findViewById(R.id.alamatRegis);
        progressBar = findViewById(R.id.progressRegister);
        regis = findViewById(R.id.Register_Button);
        namakonter = findViewById(R.id.namakonter);
        postcode = findViewById(R.id.postCode);
        kelurahan = findViewById(R.id.kelurahan);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);
        kecamatan = findViewById(R.id.kecamatan);
        referal = findViewById(R.id.referalcodeRegis);
        serverid = findViewById(R.id.serverID);

        provinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kabupaten.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kabupaten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kecamatan.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kecamatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kelurahan.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kelurahan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postcode.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        postcode.setFocusable(false);
        postcode.setEnabled(false);
        postcode.setOnClickListener(v -> onClickKodePos());

        kelurahan.setFocusable(false);
        kelurahan.setEnabled(false);
        kelurahan.setOnClickListener(v -> onClickKelurahan());

        //ID Provinsi definition

        provinsi.setFocusable(false);
        provinsi.setOnClickListener(v -> onClickProvinsi());

        //ID Kabupaten definition

        kabupaten.setFocusable(false);
        kabupaten.setEnabled(false);
        kabupaten.setOnClickListener(v -> onClickKabupaten());


        kecamatan.setFocusable(false);
        kecamatan.setEnabled(false);
        kecamatan.setOnClickListener(v -> onClickKecamatan());
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvDescNomor)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        ((TextView) findViewById(R.id.tvDesc)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(regis.getBackground(), "green");
        DrawableMap.changeColorStroke(regis.getBackground(), "green");
    }

    public void onClickProvinsi() {
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
    }

    private void onClickKecamatan() {
        Bundle bundle = new Bundle();
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        bundle.putString("kabupatenkey","");
        modalKecamatan.setArguments(bundle);
        modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
    }

    private void onClickKabupaten() {
        Bundle bundle = new Bundle();
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        bundle.putString("provinsikey","");
        modalKabupaten.setArguments(bundle);
        modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
    }

    private void onClickKelurahan() {
        Bundle bundle = new Bundle();
        ModalKelurahan modalKelurahan = new ModalKelurahan();
        bundle.putString("kecamatankey","");
        modalKelurahan.setArguments(bundle);
        modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    private void onClickKodePos() {
        Bundle bundle = new Bundle();
        ModalKodePos modalKodePos = new ModalKodePos();
        bundle.putString("kelurahankey","");
        modalKodePos.setArguments(bundle);
        modalKodePos.show(getSupportFragmentManager(), "Modalkodepos");
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

    public void Register(View view) {
        getLocation();

        String name = namapemilik.getText().toString().toUpperCase().trim();
        String Store_name = namakonter.getText().toString().toUpperCase().trim();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String alamat = alamatregis.getText().toString();
        String parent = referal.getText().toString();
        String serveridd = serverid.getText().toString();
        String IpAddres = getIPaddress();
        String MacAddres = Value.getMacAddress(getApplicationContext());
        String useragent = getUserAgent();
        double longlitude = gpsTracker.getLongitude();
        double latitude = gpsTracker.getLatitude();


        if (name.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else if (Phone.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Telepon tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else if (Email.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            regis.setText("");

            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                String deviceToken = task.getResult();

                MRegister register = new MRegister(name, deviceToken, Email, Phone, Store_name, MacAddres, IpAddres, alamat, parent, useragent, 0, 0, 0, 0, 0, longlitude, latitude, ApplicationVariable.getServerId());
                Api api = RetroClient.getApiServices();
                Call<MRegister> call = api.Register(register);

                call.enqueue(new Callback<MRegister>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<MRegister> call, @NonNull Response<MRegister> response) {
                        assert response.body() != null;
                        String code = response.body().getCode();
                        if (code.equals("200")) {
                            String user_id = response.body().getData().getUser_id();
                            String user_code = response.body().getData().getUser_code();
                            String otp_id = response.body().getData().getOtp_id();
                            String phonee = response.body().getData().getPhone();
                            Intent intent = new Intent(RegisterActivity.this, OtpSend.class);

                            Preference.getSharedPreference(getBaseContext());
                            Preference.setKeyOtpId(getBaseContext(), otp_id);
                            Preference.setKeyPhone(getBaseContext(), phonee);
                            Preference.setKeyUserCode(getBaseContext(), user_code);
                            Preference.setKeyUserId(getBaseContext(), user_id);

                            progressBar.setVisibility(View.GONE);
                            regis.setText("Selanjutnya");

                            namapemilik.setText("");
                            namakonter.setText("");
                            phone.setText("");
                            email.setText("");
                            alamatregis.setText("");
                            provinsi.setText("");
                            kabupaten.setText("");
                            kecamatan.setText("");
                            kelurahan.setText("");
                            postcode.setText("");
                            referal.setText("");
                            serverid.setText("");
                            Preference.setTrackRegister(getApplicationContext(), "1");
                            startActivity(intent);
                            finish();
                        } else {
                            StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                            progressBar.setVisibility(View.GONE);
                            regis.setText("Selanjutnya");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MRegister> call, @NonNull Throwable t) {
                        StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan Internet", Toast.LENGTH_LONG, R.style.mytoast2).show();
                    }
                });
            });
        }
    }

    private String getUserAgent() {
        return new WebView(this).getSettings().getUserAgentString();
    }

    private String getIPaddress() {
        return utils.getIPAddress(true);
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(RegisterActivity.this);
        if (gpsTracker.canGetLocation()) {

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onButtonClick(String name, String id) {
        provinsi.setText(name);
        province = Integer.parseInt(id);
        Preference.setIDProvinsi(getApplicationContext(), id);
    }

    @Override
    public void onButtonClickKabupaten(String name, String id) {
        kabupaten.setText(name);
        Regencie = Integer.parseInt(id);
    }

    @Override
    public void onButtonClickKecamatan(String name, String id) {
        kecamatan.setText(name);
        district = Integer.parseInt(id);
    }

    @Override
    public void onButtonClickKelurahan(String name, String id) {
        kelurahan.setText(name);
        subdistrict = Integer.parseInt(id);
    }

    @Override
    public void onButtonClickPost(String postalcode, String id) {
        postcode.setText(postalcode);
        postalCode = Integer.parseInt(id);
    }
}