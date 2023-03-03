package com.c.cpayid.feature.menuUtama.GasNegara;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
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

public class ProdukGasnegara extends BaseActivity implements ModalGasnegara.BottomSheetListenerProduksms {

    EditText inputprodukgasnegara,inputnomorgasnegara;
    TextView tujukaraktergasnegara;
    AdapterProdukGasnegara adapterProdukGasnegara;
    ArrayList<ResponProdukGasnegara.VoucherData> mData = new ArrayList<>();
    RecyclerView recyclerView;
    String type = "PASCABAYAR";
    String idd;
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_gasnegara);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Gas Negara <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        inputprodukgasnegara = findViewById(R.id.inputprodukgasnegara);
        inputprodukgasnegara.setFocusable(false);
        inputnomorgasnegara = findViewById(R.id.inputnomorgasnegara);
        tujukaraktergasnegara = findViewById(R.id.tujukaraktergasnegara);
        recyclerView = findViewById(R.id.ReyProdukGasnegara);

        adapterProdukGasnegara = new AdapterProdukGasnegara(getApplicationContext(), mData, inputnomorgasnegara.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukGasnegara);

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        inputprodukgasnegara.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalGasnegara modalgas = new ModalGasnegara();
            bundle.putString("id", id);
            modalgas.setArguments(bundle);
            modalgas.show(getSupportFragmentManager(), "Modal pajak");
        });

        inputnomorgasnegara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorgasnegara.length() >= 7) {
                    tujukaraktergasnegara.setVisibility(View.INVISIBLE);
                } else {
                    tujukaraktergasnegara.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(),inputnomorgasnegara.getText().toString());
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorVector(getContact.getDrawable(), "green3");
        tujukaraktergasnegara.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    public void getProduk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukGasnegara> call = api.getProdukGasSub(token, id);
        call.enqueue(new Callback<ResponProdukGasnegara>() {
            @Override
            public void onResponse(@NonNull Call<ResponProdukGasnegara> call, @NonNull Response<ResponProdukGasnegara> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mData = response.body().getData();
                    adapterProdukGasnegara = new AdapterProdukGasnegara(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukGasnegara);
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponProdukGasnegara> call, @NonNull Throwable t) {

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

    @Override
    public void onButtonClick(String name, String id) {
        getProduk(id,inputnomorgasnegara.getText().toString());
        inputprodukgasnegara.setText(name);
        setIdd(id);
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
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
                                    inputnomorgasnegara.setText(nom.replaceAll("-",""));
                                } else {
                                    inputnomorgasnegara.setText(contactNumber);
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