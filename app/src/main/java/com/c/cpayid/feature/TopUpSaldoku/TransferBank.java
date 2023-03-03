package com.c.cpayid.feature.TopUpSaldoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.ActivityHistoryTopUp;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.github.dhaval2404.imagepicker.ImagePicker;
import io.github.muddz.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferBank extends BaseActivity implements ModalPinTopUpSaldoku.BottomSheetListeneridUpload {

    TextView saldokubank,titleakunbank,noRekenning,NamaRekening;
    Button oktransaksi, uploadBuktiTBSaldo,batal;
    ImageView SalinTotal,SalinRekening;
    int up = 0;
    String jumlah="";
    String primaryid = "";
    LoadingPrimer loadingPrimer;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer_bank);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pembayaran Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        loadingPrimer = new LoadingPrimer(TransferBank.this);
        loadingPrimer.startDialogLoadingCancleAble();

        saldokubank = findViewById(R.id.saldokubank);
        uploadBuktiTBSaldo = findViewById(R.id.uploadBuktiTBSaldo);
        oktransaksi = findViewById(R.id.oktransaksi);
        titleakunbank = findViewById(R.id.titleakunbank);
        noRekenning = findViewById(R.id.noRekenning);
        NamaRekening = findViewById(R.id.NamaRekening);
        batal = findViewById(R.id.batal);

        SalinTotal = findViewById(R.id.salinTotal);
        SalinRekening = findViewById(R.id.salinRekening);

        String noRekening = getIntent().getStringExtra("NoRekening");
        String titleakun = getIntent().getStringExtra("Title");
        String namaRekening = getIntent().getStringExtra("NamaRekening");

        SalinTotal.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", getJumlah());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        batal.setOnClickListener(view -> {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(TransferBank.this);
            alertdialog.setTitle("TopUp Saldoku");
            alertdialog.setMessage("Apakah anda yakin ingin Membatalkan ?");
            alertdialog.setPositiveButton("yes", (dialog, which) -> {
                String token = "Bearer " + Preference.getToken(getApplicationContext());
                mCancel cancel = new mCancel(getPrimaryid(),"CANCEL", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()));

                Api api = RetroClient.getApiServices();
                Call<mCancel> call = api.CancelTopup(token,cancel);
                call.enqueue(new Callback<mCancel>() {
                    @Override
                    public void onResponse(@NonNull Call<mCancel> call, @NonNull Response<mCancel> response) {
                        assert response.body() != null;
                        if (response.body().getCode().equals("200")){
                            Intent intent = new Intent(TransferBank.this, ActivityHistoryTopUp.class);
                            startActivity(intent);
                            Intent intent2 = new Intent("finish_activity");
                            sendBroadcast(intent2);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),response.body().error,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<mCancel> call, @NonNull Throwable t) {

                    }
                });
            });

            alertdialog.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = alertdialog.create();
            alertDialog.show();

        });

        SalinRekening.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", noRekenning.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        noRekenning.setText(noRekening);
        titleakunbank.setText(titleakun);
        NamaRekening.setText(namaRekening);
        Handler handler = new Handler();
        handler.postDelayed(() -> ajukanLimit(Double.parseDouble(Preference.getSaldoku(getApplicationContext()).replaceAll(",",""))),1500);

        uploadBuktiTBSaldo.setOnClickListener(v -> {
            String idprimary = getPrimaryid();
            if (idprimary.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Lakukan Pengajuan terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {
                ImagePicker.with(TransferBank.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        titleakunbank.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap photo = bitmap;
                    uploadBukti(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onButtonClickIdUpload(String id) {
        setPrimaryid(id);
        if (!id.isEmpty()) {
            oktransaksi.setText("Pengajuan Berhasil");
        }
    }

    private void uploadBukti(Bitmap photo) {
        Preference.getSharedPreference(getBaseContext());
        final LoadingPrimer loadingPrimerd = new LoadingPrimer(TransferBank.this);
        loadingPrimerd.startDialogLoading();
        String typee = "proof_saldoku";
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typee);
        RequestBody primary_id = RequestBody.create(MediaType.parse("text/plain"), getPrimaryid());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile);
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        //creating retrofit object
        Api api = RetroClient.getApiServices();
//        progresktp.setVisibility(View.VISIBLE);
        Call<ResponTopUp> call = api.uploadBuktiBayar(token, body, type, primary_id);
        call.enqueue(new Callback<ResponTopUp>() {

            @Override
            public void onResponse(@NonNull Call<ResponTopUp> call, @NonNull Response<ResponTopUp> response) {
                assert response.body() != null;
                String code = response.body().getCode();
//                progresktp.setVisibility(View.INVISIBLE);
//                foto2 ="1";
                if (code.equals("200")) {
                    Toast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT).show();
                    loadingPrimerd.dismissDialog();
                    Intent intent = new Intent(TransferBank.this, ActivityHistoryTopUp.class);
                    startActivity(intent);
                    Intent intent2 = new Intent("finish_activity");
                    sendBroadcast(intent2);
                    finish();
//                    uploadKTP.setImageDrawable(getDrawable(R.drawable.check));
                } else {
                    loadingPrimerd.dismissDialog();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponTopUp> call, @NonNull Throwable t) {
//                progresktp.setVisibility(View.GONE);
                StyleableToast.makeText(getApplicationContext(), "Yuk upload lagi,Koneksimu kurang baik", Toast.LENGTH_LONG, R.style.mytoast).show();
                loadingPrimerd.dismissDialog();
            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(String primaryid) {
        this.primaryid = primaryid;
    }

    private void ajukanLimit(double amount) {
        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        String id = getIntent().getStringExtra("id");
        ReqSaldoku reqSaldoku = new ReqSaldoku(id, Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), amount);

        Call<ReqSaldoku> call = api.AddRequestSaldoku(token, reqSaldoku);
        call.enqueue(new Callback<ReqSaldoku>() {
            @Override
            public void onResponse(@NonNull Call<ReqSaldoku> call, @NonNull Response<ReqSaldoku> response) {
                assert response.body() != null;
                String code = response.body().getCode();

                if (code.equals("200")) {
                    loadingPrimer.dismissDialog();
                    saldokubank.setText(utils.ConvertRP(response.body().getData().getAmount()));
                    setJumlah(response.body().getData().getAmount());
                    setPrimaryid(response.body().getData().getId());

                    Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_LONG).show();
                } else {
                    loadingPrimer.dismissDialog();
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReqSaldoku> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}