package com.c.cpayid.feature.menuUtama.VoucherGame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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

public class ProdukVoucherGame extends BaseActivity implements ModalVoucherGame.BottomSheetListenerVoucherGame {

    EditText inputprodukvouchergame, inputnomorvouchergame;
    TextView tujukaraktervouchergame;
    AdapterProdukVoucher adapterProdukVoucher;
    ArrayList<ResponProdukVoucher.VoucherData> mVoucherGame = new ArrayList<>();
    RecyclerView recyclerView;
    String id;
    String type = "PRABAYAR";
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_voucher_game);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Voucher Game <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        inputprodukvouchergame = findViewById(R.id.inputprodukvouchergame);
        inputprodukvouchergame.setFocusable(false);
        inputnomorvouchergame = findViewById(R.id.inputnomorvouchergame);
        tujukaraktervouchergame = findViewById(R.id.tujukaraktervouchergame);
        recyclerView = findViewById(R.id.ReyVoucherGame);

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mVoucherGame, inputnomorvouchergame.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukVoucher);

        inputprodukvouchergame.setOnClickListener(v -> {

            Intent intent = getIntent();
            String id = intent.getStringExtra("id");

            ModalVoucherGame modalVoucherGame = new ModalVoucherGame(id);
            modalVoucherGame.show(getSupportFragmentManager(), "Modal Voucher");

        });

        inputnomorvouchergame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorvouchergame.length() >= 7) {
                    tujukaraktervouchergame.setVisibility(View.INVISIBLE);
                    Preference.setNo(getApplicationContext(),inputnomorvouchergame.getText().toString());
                } else {
                    tujukaraktervouchergame.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getVoucherGameProduk(getId(),inputnomorvouchergame.getText().toString());
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorVector(getContact.getDrawable(), "green3");
        tujukaraktervouchergame.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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
    public void onButtonClickKabupaten(String name, String id) {
        setId(id);
        getVoucherGameProduk(id,inputnomorvouchergame.getText().toString());
        inputprodukvouchergame.setText(name);
    }

    public void getVoucherGameProduk(String id,String nomor) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukVoucher> call = api.getProdukVG(token, id);
        call.enqueue(new Callback<ResponProdukVoucher>() {
            @Override
            public void onResponse(Call<ResponProdukVoucher> call, Response<ResponProdukVoucher> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mVoucherGame = response.body().getData();
                    adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mVoucherGame, nomor, type);
                    recyclerView.setAdapter(adapterProdukVoucher);

                } else {

                    ArrayList<ResponProdukVoucher.VoucherData> mVoucherGamee = new ArrayList<>();
                    mVoucherGame = mVoucherGamee;
                    adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mVoucherGame, inputnomorvouchergame.getText().toString(), type);
                    recyclerView.setAdapter(adapterProdukVoucher);

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponProdukVoucher> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    //Chek Contact Permision
    private boolean checkContactPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
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
                                        inputnomorvouchergame.setText(nom.replaceAll("-",""));

                                    } else {
                                        inputnomorvouchergame.setText(contactNumber);
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