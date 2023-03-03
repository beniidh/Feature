package com.c.cpayid.feature.menuUtama.ListrikPLNPasca;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Transaksi.MInquiry;
import com.c.cpayid.feature.Transaksi.ResponInquiry;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.menuUtama.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlnProdukPasca extends BaseActivity {
    EditText inputplnpasca;
    TextView tujukarakterplnpasca;
    String skucode, inquiry,hargaa,sellingprice,codeplnpasca;
    Button periksaplnpascaP, bayartagihanpulsapascaP;
    TextView name, nomcos, deskrip, tanggall, transaksi, tagihan, PPtarifP,PPStatusP,PPdayaP,PPAdminP;
    LinearLayout LinearPasca;

    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln_produk_pasca);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Listrik PLN Pasca <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        inputplnpasca = findViewById(R.id.nomorinputPLNPasca);
        tujukarakterplnpasca = findViewById(R.id.tujukarakterplnpasca);
        periksaplnpascaP = findViewById(R.id.periksaplnpascaP);
        name = findViewById(R.id.PPnamaP);
        nomcos = findViewById(R.id.PPnomorP);
        deskrip = findViewById(R.id.PPdeskriptionP);
        tanggall = findViewById(R.id.PPTanggalP);
        transaksi = findViewById(R.id.PPTransaksiP);
        PPtarifP = findViewById(R.id.PPtarifP);
        tagihan = findViewById(R.id.PPjumlahTagihanP);
        LinearPasca = findViewById(R.id.LinearPasca);
        PPStatusP = findViewById(R.id.PPStatusP);
        PPdayaP = findViewById(R.id.PPdayaP);
        PPAdminP = findViewById(R.id.PPAdminP);

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        String id = getIntent().getStringExtra("id");
        getCodeSub(id);

        periksaplnpascaP.setOnClickListener(v -> {
            if (periksaplnpascaP.getText().toString().equals("Cek Tagihan")) {
                if (!inputplnpasca.getText().toString().isEmpty()) {
                    LoadingPrimer loadingPrimer = new LoadingPrimer(PlnProdukPasca.this);
                    loadingPrimer.startDialogLoading();

                    GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

                    Api api = RetroClient.getApiServices();
                    MInquiry mInquiry = new MInquiry(getCodeplnpasca(), inputplnpasca.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    String token = "Bearer " + Preference.getToken(getApplicationContext());

                    Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
                    call.enqueue(new Callback<ResponInquiry>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<ResponInquiry> call, @NonNull Response<ResponInquiry> response) {
                            assert response.body() != null;
                            String code = response.body().getCode();
                            if (code.equals("200")) {
                                if (response.body().getData().getStatus().equals("Sukses")) {
                                    name.setText(response.body().getData().getCustomer_name());
                                    nomcos.setText(response.body().getData().getCustomer_no());
                                    deskrip.setText(response.body().getData().getDescription());
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    tanggall.setText(hari + " " + bulan + " " + tahun);
                                    PPdayaP.setText(response.body().getData().getDetail_product().getDaya());
                                    tagihan.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    transaksi.setText(response.body().getData().getRef_id());
                                    PPAdminP.setText(utils.ConvertRP(response.body().getData().getAdmin_fee()));
                                    PPtarifP.setText(response.body().getData().getDetail_product().getTarif());
                                    PPStatusP.setText(response.body().getData().getStatus());
                                    setHargaa(response.body().getData().getDetail_product().getDetail().get(0).getNilai_tagihan());
                                    setSellingprice(response.body().getData().getSelling_price());

                                    loadingPrimer.dismissDialog();
                                    periksaplnpascaP.setText("Bayar");
                                    LinearPasca.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(getApplicationContext(),response.body().getData().getStatus()+" "+response.body().getData().getDescription(),Toast.LENGTH_LONG).show();
                                    loadingPrimer.dismissDialog();
                                }
                            } else {
                                StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponInquiry> call, @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                            loadingPrimer.dismissDialog();
                        }
                    });
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }
            } else {
                Intent intent = new Intent(getApplicationContext(), KonfirmasiPembayaran.class);
                intent.putExtra("hargga", utils.ConvertRP(getSellingprice()));
                intent.putExtra("RefID", transaksi.getText().toString());
                intent.putExtra("sku_code",getSkucode());
                intent.putExtra("inquiry",getInquiry());
                intent.putExtra("nomorr",nomcos.getText().toString());
                startActivity(intent);
            }
        });

        inputplnpasca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputplnpasca.length() >= 7) {
                    tujukarakterplnpasca.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterplnpasca.setVisibility(View.VISIBLE);
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

        DrawableMap.changeColorVector(getContact.getDrawable(), "green3");
        DrawableMap.changeColor(periksaplnpascaP.getBackground(), "green");
        DrawableMap.changeColorStroke(periksaplnpascaP.getBackground(), "green");
        tujukarakterplnpasca.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    public void getCodePLN(String id){
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGetCodePasca> call = api.getCodePLNPasca(token,id);
        call.enqueue(new Callback<ResponGetCodePasca>() {
            @Override
            public void onResponse(@NonNull Call<ResponGetCodePasca> call, @NonNull Response<ResponGetCodePasca> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if(code.equals("200")){
                    String codePLN = response.body().getData().getProduct().get(0).getBuyer_sku_code();
                    setCodeplnpasca(codePLN);
                } else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponGetCodePasca> call, @NonNull Throwable t) {

            }
        });
    }

    public void getCodeSub(String id){
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponCodeSub> call = api.getCodeSubPln(token,id);
        call.enqueue(new Callback<ResponCodeSub>() {
            @Override
            public void onResponse(@NonNull Call<ResponCodeSub> call, @NonNull Response<ResponCodeSub> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    getCodePLN(response.body().getData().get(0).getId());
                } else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponCodeSub> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
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

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getCodeplnpasca() {
        return codeplnpasca;
    }

    public void setCodeplnpasca(String codeplnpasca) {
        this.codeplnpasca = codeplnpasca;
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
                                    inputplnpasca.setText(nom.replaceAll("-",""));
                                } else {
                                    inputplnpasca.setText(contactNumber);
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