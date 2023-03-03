package com.c.cpayid.feature.CetakStruk;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;

import java.io.File;
import java.util.Objects;

public class DetailTransaksiTruk extends BaseActivity {

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    TextView title;
    TextView idNomorStruk,namaTPC, idProdukStruk, hargastruk, idSaldokuStruk, idTanggalStruk, idWaktuStruk, idNomorSNStruk, idNomorTransaksiStruk, idTotalPembelianStruk;
    Button setHargaJual, cetakPDF;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_truk);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Detail Transaksi Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setHargaJual = findViewById(R.id.setHargaJual);
        namaTPC = findViewById(R.id.namaTPC);
        idNomorStruk = findViewById(R.id.idNomorStruk);
        idProdukStruk = findViewById(R.id.idProdukStruk);
        idSaldokuStruk = findViewById(R.id.idSaldokuStruk);
        idTanggalStruk = findViewById(R.id.idTanggalStruk);
        idWaktuStruk = findViewById(R.id.idWaktuStruk);
        idNomorSNStruk = findViewById(R.id.idNomorSNStruk);
        idNomorTransaksiStruk = findViewById(R.id.idNomorTransaksiStruk);
        idTotalPembelianStruk = findViewById(R.id.idTotalPembelianStruk);
        hargastruk = findViewById(R.id.hargastruk);
        title = findViewById(R.id.titlestruk);
        idNomorStruk.setText(getIntent().getExtras().getString("nomor"));
        idProdukStruk.setText(getIntent().getExtras().getString("produk"));
        idSaldokuStruk.setText(utils.ConvertRP(getIntent().getExtras().getString("harga")));
        idTanggalStruk.setText(getIntent().getExtras().getString("tanggal"));
        namaTPC.setText(getIntent().getExtras().getString("nama"));
        idWaktuStruk.setText(getIntent().getExtras().getString("waktu"));
        idNomorSNStruk.setText(getIntent().getExtras().getString("sn"));
        idNomorTransaksiStruk.setText(getIntent().getExtras().getString("transaksid"));
        idTotalPembelianStruk.setText(utils.ConvertRP(getIntent().getExtras().getString("harga")));
        hargastruk.setText(utils.ConvertRP(getIntent().getExtras().getString("harga")));
        title.setText(getIntent().getExtras().getString("produk"));

        setHargaJual.setOnClickListener(v -> popUpMenuSetSellPrice());

        cetakPDF = findViewById(R.id.cetakPDF);
        cetakPDF.setOnClickListener(v -> {
            Intent intent = new Intent(DetailTransaksiTruk.this, Cetak.class);
            intent.putExtra("nomor", idNomorStruk.getText().toString());
            intent.putExtra("produk", idProdukStruk.getText().toString());
            intent.putExtra("hargajual", idTotalPembelianStruk.getText().toString());
            intent.putExtra("tanggal", idTanggalStruk.getText().toString());
            intent.putExtra("nama", namaTPC.getText().toString());
            intent.putExtra("waktu", idWaktuStruk.getText().toString());
            intent.putExtra("sn", idNomorSNStruk.getText().toString());
            intent.putExtra("title", title.getText().toString());
            intent.putExtra("transaksid", idNomorTransaksiStruk.getText().toString());
            startActivity(intent);

//                createpdf();
        });

        if (!hasPermissions(DetailTransaksiTruk.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(DetailTransaksiTruk.this, PERMISSIONS, PERMISSION_ALL);
        }

        File file = new File(this.getExternalFilesDir(null).getAbsolutePath(), "pdfsdcard_location");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        ((TextView) findViewById(R.id.tvDesc)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(setHargaJual.getBackground(), "green2");
        DrawableMap.changeColorStroke(setHargaJual.getBackground(), "green2");
        DrawableMap.changeColor(cetakPDF.getBackground(), "green");
        DrawableMap.changeColorStroke(cetakPDF.getBackground(), "green");
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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void popUpMenuSetSellPrice() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(DetailTransaksiTruk.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargajual, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            idTotalPembelianStruk.setText(harga);
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

}