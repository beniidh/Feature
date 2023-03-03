package com.c.cpayid.feature.menuUtama.HolderPulsa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductHolderPascaPdam extends BaseActivity {

    EditText inputnomorproduk;
    RecyclerView ReyProdukHolder;
    AdapterProdukHolderP adapterProdukHolder;
    ArrayList<ResponProdukHolder.Mdata> produk = new ArrayList<>();
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_holder_pasca_pdam);
        String produkname = getIntent().getStringExtra("name");
        Preference.setNo(getApplicationContext(),"");
        Preference.setJenisProduk(getApplicationContext(),produkname);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>"+produkname+"<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));

        inputnomorproduk = findViewById(R.id.inputnomorproduk);
        ReyProdukHolder = findViewById(R.id.ReyProdukHolder);

        adapterProdukHolder = new AdapterProdukHolderP(getApplicationContext(), produk, inputnomorproduk.getText().toString(), ProductHolderPascaPdam.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ReyProdukHolder.setLayoutManager(mLayoutManager);
        ReyProdukHolder.setAdapter(adapterProdukHolder);

        String id = getIntent().getStringExtra("id");
        String jenis = getIntent().getStringExtra("jenis");
        if (jenis.equals("sub")) {
            getSubProduct(id);
        } else {
            getProduct(id);
        }

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        inputnomorproduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Preference.setNo(getApplicationContext(), inputnomorproduk.getText().toString());

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
        ((TextView) findViewById(R.id.tujukarakterair)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    private void getProduct(String id) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(ProductHolderPascaPdam.this);
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukHolder> call = api.getProdukHolder(token, id);
        call.enqueue(new Callback<ResponProdukHolder>() {
            @Override
            public void onResponse(@NonNull Call<ResponProdukHolder> call, @NonNull Response<ResponProdukHolder> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    produk = response.body().getData();
                    adapterProdukHolder = new AdapterProdukHolderP(getApplicationContext(), produk,  Preference.getNo(getApplicationContext()), ProductHolderPascaPdam.this);
                    ReyProdukHolder.setAdapter(adapterProdukHolder);
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
                loadingPrimer.dismissDialog();
            }

            @Override
            public void onFailure(@NonNull Call<ResponProdukHolder> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG).show();
                loadingPrimer.dismissDialog();
            }
        });
    }

    private void getSubProduct(String id) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(ProductHolderPascaPdam.this);
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukHolder> call = api.getProdukHolderSub(token, id);
        call.enqueue(new Callback<ResponProdukHolder>() {
            @Override
            public void onResponse(@NonNull Call<ResponProdukHolder> call, @NonNull Response<ResponProdukHolder> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    produk = response.body().getData();
                    adapterProdukHolder = new AdapterProdukHolderP(getApplicationContext(), produk,  Preference.getNo(getApplicationContext()), ProductHolderPascaPdam.this);
                    ReyProdukHolder.setAdapter(adapterProdukHolder);
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
                loadingPrimer.dismissDialog();
            }

            @Override
            public void onFailure(@NonNull Call<ResponProdukHolder> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG).show();
                loadingPrimer.dismissDialog();
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

    //Chek Contact Permision
    private boolean checkContactPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
    }

    //permintaan koneksi
    private void requestContactPermission() {
        String[] permision = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permision, CONTACT_PERMISSION_CODE);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult (
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
                                String nom = contactNumber;
                                if (contactNumber.substring(0, 3).equals("+62")) {
                                    nom = "0" + contactNumber.substring(3).replaceAll(" ", "");
                                    inputnomorproduk.setText(nom.replaceAll("-", ""));
                                } else {
                                    inputnomorproduk.setText(nom.replaceAll("-", ""));
                                }
                                String id = getIntent().getStringExtra("id");
                                Preference.setNo(getApplicationContext(),nom.replaceAll("-",""));

                                String jenis = getIntent().getStringExtra("jenis");
                                if (jenis.equals("sub")) {
                                    getSubProduct(id);
                                } else {
                                    getProduct(id);
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