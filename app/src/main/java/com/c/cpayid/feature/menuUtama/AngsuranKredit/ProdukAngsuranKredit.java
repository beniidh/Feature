package com.c.cpayid.feature.menuUtama.AngsuranKredit;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import io.github.muddz.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdukAngsuranKredit extends BaseActivity implements ModalAngsuran.BottomSheetListenerProduksms {

    EditText inputprodukangsuran,inputnomorangsuran;
    TextView tujukarakterangsuran;
    LinearLayout LinerAngsuran;
    TextView PPnomorA,PPnamaA,PPtarifA,PPdayaA,PPjumlahTagihanA,PPTanggalA,PPTransaksiA,PPStatusA;
    AdapterProdukAngsuran adapterProdukAngsuran;
    Button PeriksaA;
    RecyclerView recyclerView;
    ArrayList<ResponProdukAngsuran.VoucherData> mData = new ArrayList<>();
    String type ="PASCABAYAR";
    String id;
    String skucode, inquiry;
    String hargaa;
    String code;
    private static final int CONTACT_PERMISSION_CODE = 1;
    private static final int PICK_PERMISSION_CODE = 2;
    ImageView getContact;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_angsuran_kredit);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Angsuran Kredit <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        PPnomorA = findViewById(R.id.PPnomorA);
        PPnamaA= findViewById(R.id.PPnamaA);
        PPtarifA= findViewById(R.id.PPtarifA);
        PPdayaA = findViewById(R.id.PPdayaA);
        PPjumlahTagihanA = findViewById(R.id.PPjumlahTagihanA);
        PPTanggalA= findViewById(R.id.PPTanggalA);
        PPTransaksiA = findViewById(R.id.PPTransaksiA);
        PPStatusA = findViewById(R.id.PPStatusA);
        LinerAngsuran = findViewById(R.id.LinerAngsuran);
        getContact = findViewById(R.id.getContact);

        inputprodukangsuran = findViewById(R.id.inputprodukangsuran);
        PeriksaA = findViewById(R.id.PeriksaA);
        inputprodukangsuran.setFocusable(false);
        inputnomorangsuran = findViewById(R.id.inputnomorangsuran);

        inputprodukangsuran.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalAngsuran modalAngsuran = new ModalAngsuran();
            bundle.putString("id",id);
            modalAngsuran.setArguments(bundle);
            modalAngsuran.show(getSupportFragmentManager(),"modal angsuran");
        });

        tujukarakterangsuran = findViewById(R.id.tujukarakterangsuran);
        inputnomorangsuran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorangsuran.length() >= 7) {
                    tujukarakterangsuran.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterangsuran.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        PeriksaA.setOnClickListener(v -> {
            if (PeriksaA.getText().toString().equals("Periksa")) {
                if (!inputnomorangsuran.getText().toString().isEmpty()) {
                    LoadingPrimer loadingPrimer = new LoadingPrimer(ProdukAngsuranKredit.this);
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
                    MInquiry mInquiry = new MInquiry(getId(), inputnomorangsuran.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()),
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
                                    PPnamaA.setText(response.body().getData().getCustomer_name());
                                    PPnomorA.setText(response.body().getData().getCustomer_no());
                                    PPStatusA.setText(response.body().getData().getStatus());
                                    PPdayaA.setText(utils.ConvertRP(response.body().getData().getAdmin_fee()));
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    PPTanggalA.setText(hari + " " + bulan + " " + tahun);
                                    PPTransaksiA.setText(response.body().getData().getRef_id());
                                    setHargaa(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    PPtarifA.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    loadingPrimer.dismissDialog();
                                    LinerAngsuran.setVisibility(View.VISIBLE);
                                    PeriksaA.setText("Bayar");
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
                intent.putExtra("hargatotal", getHargaa());
                intent.putExtra("RefID", PPTransaksiA.getText().toString());
                intent.putExtra("sku_code",getSkucode());
                intent.putExtra("inquiry",getInquiry());
                intent.putExtra("nomorr",inputnomorangsuran.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorVector(getContact.getDrawable(), "green3");
        DrawableMap.changeColor(PeriksaA.getBackground(), "green");
        DrawableMap.changeColorStroke(PeriksaA.getBackground(), "green");
        tujukarakterangsuran.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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

    @Override
    public void onButtonClick(String name, String id) {
        getProduk(id,inputnomorangsuran.getText().toString());
        inputprodukangsuran.setText(name);
        setId(id);
    }

    public void getProduk(String id,String nomor){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukAngsuran> call = api.getProdukAngsuranSub(token,id);
        call.enqueue(new Callback<ResponProdukAngsuran>() {
            @Override
            public void onResponse(@NonNull Call<ResponProdukAngsuran> call, @NonNull Response<ResponProdukAngsuran> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                  setId(response.body().getData().get(0).getCode());
                } else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukAngsuran> call, Throwable t) {

            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                                    inputnomorangsuran.setText(nom.replaceAll("-",""));
                                } else {
                                    inputnomorangsuran.setText(contactNumber);
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