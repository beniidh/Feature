package com.c.cpayid.feature.TambahKonter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Modal.ModalKabupaten;
import com.c.cpayid.feature.Modal.ModalKecamatan;
import com.c.cpayid.feature.Modal.ModalKelurahan;
import com.c.cpayid.feature.Modal.ModalKodePos;
import com.c.cpayid.feature.Modal.ModalProvinsi;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKonter extends BaseActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan, ModalKelurahan.BottomSheetListenerKelurahan, ModalKodePos.BottomSheetListenerPost {

    EditText provinsi, kecamatan, kabupaten, kelurahan, postcode, namapemilik, email, phone, alamatregis, namakonter, referal, serverid;
    ProgressBar progressBar;
    int province, Regencie, district, subdistrict, postalCode;
    Button regis;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_konter);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tambah Konter<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        namapemilik = findViewById(R.id.namapemilikKonter);
        email = findViewById(R.id.emailRegisKonter);
        phone = findViewById(R.id.PhoneRegisKonter);
        alamatregis = findViewById(R.id.alamatRegisKonter);
        progressBar = findViewById(R.id.progressRegister);
        regis = findViewById(R.id.Register_ButtonKonter);
        namakonter = findViewById(R.id.namakonterKonter);
        postcode = findViewById(R.id.postCodeKonter);
        kelurahan = findViewById(R.id.kelurahanKonter);
        provinsi = findViewById(R.id.provinsiKonter);
        kabupaten = findViewById(R.id.kabupatenKonter);
        kecamatan = findViewById(R.id.kecamatanKonter);

        regis.setOnClickListener(v -> addKonter());
        provinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                kabupaten.setEnabled(true);
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
        ((TextView) findViewById(R.id.tvDesc)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        regis.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColorStroke(regis.getBackground(), "green");
    }

    public void addKonter() {
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        String name = namapemilik.getText().toString();
        String namakonterr = namakonter.getText().toString();
        String emaill = email.getText().toString();
        String phonne = phone.getText().toString();
        String address = alamatregis.getText().toString();
        double longlitutde = gpsTracker.getLatitude();
        double latitude = gpsTracker.getLongitude();
        String UA = Value.getUserAgent(getApplicationContext());
        String IP = Value.getIPaddress();
        String mac = Value.getMacAddress(getApplicationContext());

        SendDataKonter sendDataKonter = new SendDataKonter(name, namakonterr, emaill, phonne, address, IP, mac, UA, province, Regencie, district, subdistrict, postalCode, longlitutde, latitude);
        Api api = RetroClient.getApiServices();
        Call<ResponTambahKonter> call = api.registerKonter(token, sendDataKonter);
        call.enqueue(new Callback<ResponTambahKonter>() {
            @Override
            public void onResponse(@NonNull Call<ResponTambahKonter> call, @NonNull Response<ResponTambahKonter> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "Konter Berhasil Ditambahkan", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    finish();
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Gagal " + response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponTambahKonter> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    private void onClickKecamatan() {
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
    }

    private void onClickKabupaten() {
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
    }

    private void onClickKelurahan() {
        ModalKelurahan modalKelurahan = new ModalKelurahan();
        modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    private void onClickKodePos() {
        ModalKodePos modalKodePos = new ModalKodePos();
        modalKodePos.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    public void onClickProvinsi() {
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
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
        postalCode = Integer.valueOf(id);
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