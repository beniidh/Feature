package com.c.cpayid.feature.menuUtama.ListrikPLN;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponSubP;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlnProduk extends BaseActivity {
    EditText nomorinputpln;
    TextView keterangan;
    AdapterProdukPLN adapterProdukPLN;
    ArrayList<ModelProdukPln> modelProdukPlns = new ArrayList<>();
    RecyclerView recyclerPLN;
    String type;
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln__produk);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Listrik PLN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        String id = intent.getStringExtra("id");
        getProdukPln(id);

        //Broadcase for kill activity from another activity
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

        recyclerPLN = findViewById(R.id.ReyProdukPlnPra);
        nomorinputpln = findViewById(R.id.nomorinputPLN);
        adapterProdukPLN = new AdapterProdukPLN(getApplicationContext(), modelProdukPlns,nomorinputpln.getText().toString(),type, PlnProduk.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPLN.setLayoutManager(mLayoutManager);
        recyclerPLN.setAdapter(adapterProdukPLN);
        registerForContextMenu(nomorinputpln);
        keterangan = findViewById(R.id.tujukarakterpln);
        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        nomorinputpln.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nomorinputpln.length() >= 7) {
                    keterangan.setVisibility(View.INVISIBLE);
                } else {
                    keterangan.setVisibility(View.VISIBLE);
                }
                getProdukPln(id);
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
        keterangan.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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

    private void getSubProdukID(String id){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSubP> call = api.getSubCategoryPLN(token,id);
        call.enqueue(new Callback<ResponSubP>() {
            @Override
            public void onResponse(@NonNull Call<ResponSubP> call, @NonNull Response<ResponSubP> response) {
                assert response.body() != null;
                String code= response.body().getCode();
                if (code.equals("200")) {
                    String idProduct = response.body().getData().get(0).getId();
                    getProdukPln(idProduct);
                } else {
                    Toast.makeText(getApplicationContext(),"gagal code",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponSubP> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProdukPln(String id){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponListrikPln> call = api.getProdukPLNListrik(token,id);
        call.enqueue(new Callback<ResponListrikPln>() {
            @Override
            public void onResponse(@NonNull Call<ResponListrikPln> call, @NonNull Response<ResponListrikPln> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if(code.equals("200")){
                    modelProdukPlns = (ArrayList<ModelProdukPln>) response.body().getData();
                    adapterProdukPLN = new AdapterProdukPLN(getApplicationContext(), modelProdukPlns,nomorinputpln.getText().toString(),type, PlnProduk.this);
                    recyclerPLN.setAdapter(adapterProdukPLN);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponListrikPln> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String teks = "";
        ClipData clip = clipboard.getPrimaryClip();
        ClipData.Item itema = clip.getItemAt(0);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.paste:
                String nomor = itema.getText().toString();
                if (nomor.substring(0, 3).equals("+62")) {
                    String nom = "0" + nomor.substring(3, nomor.length());
                    nomorinputpln.setText(nom);
                } else {
                    nomorinputpln.setText(nomor);
                }

                break;
        }

        return true;
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
                                    nomorinputpln.setText(nom.replaceAll("-",""));
                                } else {
                                    nomorinputpln.setText(contactNumber);
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