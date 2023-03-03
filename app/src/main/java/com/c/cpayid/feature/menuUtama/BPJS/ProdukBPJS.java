package com.c.cpayid.feature.menuUtama.BPJS;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Transaksi.MInquiry;
import com.c.cpayid.feature.Transaksi.ResponInquiry;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.cpayid.feature.sharePreference.Preference;
import io.github.muddz.styleabletoast.StyleableToast;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdukBPJS extends BaseActivity implements ModalBpjs.BottomSheetListenerProduksms {

    EditText nomorinputBpjs,pilihProdukBPJS;
    TextView PPnomorBp,PPnamaBp,PPtarifBp,PPdayaBp,PPjumlahTagihanBp,PPTanggalBp,PPTransaksiBp,PPStatusBp;
    Button periksaBpjs;
    LinearLayout LineraBPJS;
    ImageView getContact;
    String skucode, inquiry;
    String hargaa;
    String code;
    private static final int CONTACT_PERMISSION_CODE = 1;
    private static final int PICK_PERMISSION_CODE = 2;

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_b_p_j_s);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>BPJS <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        nomorinputBpjs   = findViewById(R.id.nomorinputBpjs);
        PPnomorBp = findViewById(R.id.PPnomorBp);
        PPnamaBp= findViewById(R.id.PPnamaBp);
        PPtarifBp= findViewById(R.id.PPtarifBp);
        PPdayaBp = findViewById(R.id.PPdayaBp);
        PPjumlahTagihanBp = findViewById(R.id.PPjumlahTagihanBp);
        PPTanggalBp= findViewById(R.id.PPTanggalBp);
        PPTransaksiBp = findViewById(R.id.PPTransaksiBp);
        PPStatusBp = findViewById(R.id.PPStatusBp);
        periksaBpjs = findViewById(R.id.periksaBpjs);
        LineraBPJS = findViewById(R.id.LineraBPJS);
        getContact = findViewById(R.id.getContact);
        pilihProdukBPJS = findViewById(R.id.pilihProdukBPJS);
        String id = getIntent().getStringExtra("id");

        pilihProdukBPJS.setOnClickListener(view -> {
            ModalBpjs modalBpjs = new ModalBpjs();
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            modalBpjs.setArguments(bundle);
            modalBpjs.show(getSupportFragmentManager(),"modal");
        });

        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        periksaBpjs.setOnClickListener(v -> {
            if (periksaBpjs.getText().toString().equals("Cek Tagihan")) {
                if (!nomorinputBpjs.getText().toString().isEmpty()) {
                    LoadingPrimer loadingPrimer = new LoadingPrimer(ProdukBPJS.this);
                    loadingPrimer.startDialogLoading();

                    GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

                    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Value.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();

                    Api api = retrofit.create(Api.class);
                    MInquiry mInquiry = new MInquiry(getCode(), nomorinputBpjs.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()),
                            gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    String token = "Bearer " + Preference.getToken(getApplicationContext());

                    Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
                    call.enqueue(new Callback<ResponInquiry>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<ResponInquiry> call, @NonNull Response<ResponInquiry> response) {
                            assert response.body() != null;
                            String code = response.body().getCode();
                            if (code.equals("200")) {
                                if(response.body().getData().getStatus().equals("Sukses")){
                                    PPnamaBp.setText(response.body().getData().getCustomer_name());
                                    PPnomorBp.setText(response.body().getData().getCustomer_no());
                                    PPStatusBp.setText(response.body().getData().getStatus());
                                    PPdayaBp.setText(utils.ConvertRP(response.body().getData().getAdmin_fee()));
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    PPTanggalBp.setText(hari + " " + bulan + " " + tahun);
                                    PPTransaksiBp.setText(response.body().getData().getRef_id());
                                    setHargaa(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    PPtarifBp.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    loadingPrimer.dismissDialog();
                                    LineraBPJS.setVisibility(View.VISIBLE);
                                    periksaBpjs.setText("Bayar");
                                } else {
                                    Toast.makeText(getApplicationContext(),response.body().getData().getStatus()+" "+response.body().getData().getDescription(),Toast.LENGTH_SHORT).show();
                                    loadingPrimer.dismissDialog();
                                }
                            } else {
                                StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponInquiry> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
                            loadingPrimer.dismissDialog();
                        }
                    });

                } else {
                    StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }
            } else {
                Intent intent = new Intent(getApplicationContext(), KonfirmasiPembayaran.class);
                intent.putExtra("hargga", getHargaa());
                intent.putExtra("RefID", PPTransaksiBp.getText().toString());
                intent.putExtra("sku_code",getSkucode());
                intent.putExtra("inquiry",getInquiry());
                intent.putExtra("nomorr",nomorinputBpjs.getText().toString());
                startActivity(intent);

//                Intent intent1 = new Intent(getApplicationContext(), pin_transaksi.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("RefID", PPTransaksiBp.getText().toString());
//                bundle.putString("sku_code", getSkucode());
//                bundle.putString("inquiry", getInquiry());
//                bundle.putString("nomorr", nomorinputBpjs.getText().toString());
//                bundle.putString("wallettype", "SALDOKU");
//                intent1.putExtras(bundle);
//                startActivity(intent1);
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorVector(getContact.getDrawable(), "green3");
        DrawableMap.changeColor(periksaBpjs.getBackground(), "green");
        DrawableMap.changeColorStroke(periksaBpjs.getBackground(), "green");
        ((TextView) findViewById(R.id.tujukarakterBpjs)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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

//    public void getProduk(String id){
//
//        String token = "Bearer "+ Preference.getToken(getApplicationContext());
//        Api api = RetroClient.getApiServices();
//        Call<ResponBPJS> call = api.getProdukBpjs(token,id);
//        call.enqueue(new Callback<ResponBPJS>() {
//            @Override
//            public void onResponse(Call<ResponBPJS> call, Response<ResponBPJS> response) {
//                if (response.body().getCode().equals("200")){
//
//                    String id = response.body().getData().get(0).getId();
////                    getProdukSub(id);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponBPJS> call, Throwable t) {
//
//            }
//        });
//    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public String getHargaa() {
        return hargaa;
    }

    public void setHargaa(String hargaa) {
        this.hargaa = hargaa;
    }

    @Override
    public void onButtonClick(String name, String id) {
        setCode(id);
        pilihProdukBPJS.setText(name);
    }

    //Chek Contact Permision
    private boolean checkContactPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
    }

    //permintaan koneksi
    private void requestContactPermission() {
        String[] permision = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permision, CONTACT_PERMISSION_CODE);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Cursor cursor1, cursor2;
                    assert data != null;
                    Uri uri = data.getData();

                    cursor1 = getContentResolver().query(uri, null, null, null, null);
                    if (cursor1.moveToFirst()) {
                        @SuppressLint("Range") String contacid = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                        @SuppressLint("Range") String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        @SuppressLint("Range") String idResult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        int idResultHold = Integer.parseInt(idResult);

                        if (idResultHold == 1) {
                            cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.
                                            Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +
                                            contacid, null, null);

                            while (cursor2.moveToNext()) {
                                @SuppressLint("Range") String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                if (contactNumber.substring(0, 3).equals("+62")) {
                                    String nom = "0" + contactNumber.substring(3).replaceAll(" ","");
                                    nomorinputBpjs.setText(nom.replaceAll("-",""));
                                } else {
                                    nomorinputBpjs.setText(contactNumber);
                                }
                            }

                            cursor2.close();
                        }
                        cursor1.close();
                    }
                }
            }
        });

    public void openYourActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        launchSomeActivity.launch(intent);
    }
}