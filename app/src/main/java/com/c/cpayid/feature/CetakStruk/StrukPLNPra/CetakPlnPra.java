package com.c.cpayid.feature.CetakStruk.StrukPLNPra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.CetakStruk.Epos.AsyncBluetoothEscPosPrint;
import com.c.cpayid.feature.CetakStruk.Epos.AsyncEscPosPrinter;
import com.c.cpayid.feature.CetakStruk.Cetak;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.Respon.ResponProfil;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import io.github.muddz.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakPlnPra extends BaseActivity {

    TextView KonterPR, AlamatPR, WaktuPR, namaPR, tarifPR, DayaPR,
            KWHPR, NominalPR, adminPR, totaltagihanPR, SNPR, nomorPR;
    Button setAdmin, CetakPra, shareStruk;
    EditText pilihPerangkat;

    TextView header, footer;
    ImageView EditHeader, EditFooter;

    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN
    };

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_pln_pra);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        EditFooter = findViewById(R.id.EditFooter);
        EditHeader = findViewById(R.id.EditHeader);
        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);

        header.setText(Preference.getHeader(getApplicationContext()));
        footer.setText(Preference.getFooter(getApplicationContext()));

        EditFooter.setOnClickListener(v -> popUpEditFooter());
        EditHeader.setOnClickListener(v -> popUpEditFooterHeader());

        //Button
        setAdmin = findViewById(R.id.setAdmin);
        CetakPra = findViewById(R.id.CetakPra);
        //TextView
        KonterPR = findViewById(R.id.KonterPR);
        AlamatPR = findViewById(R.id.AlamatPR);
        WaktuPR = findViewById(R.id.WaktuPR);
        shareStruk = findViewById(R.id.shareStruk);
        nomorPR = findViewById(R.id.nomorPR);
        namaPR = findViewById(R.id.namaPR);
        tarifPR = findViewById(R.id.tarifPR);
        DayaPR = findViewById(R.id.DayaPR);
        KWHPR = findViewById(R.id.KWHPR);
        NominalPR = findViewById(R.id.NominalPR);
        adminPR = findViewById(R.id.adminPR);
        totaltagihanPR = findViewById(R.id.totaltagihanPR);
        SNPR = findViewById(R.id.SNPR);
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        String SN = getIntent().getExtras().getString("sn");
        String[] sn = SN.split(",");

        nomorPR.setText("Nomor : " + getIntent().getExtras().getString("nomor"));
        namaPR.setText("Nama : " + sn[1].substring(5));
        WaktuPR.setText(getIntent().getExtras().getString("waktu2"));
        tarifPR.setText("Tarif/Daya : " + sn[2].substring(5));
        totaltagihanPR.setText("Total Tagihan : " + utils.ConvertRP(getIntent().getExtras().getString("harga")));
        NominalPR.setText("Nominal : " + utils.ConvertRP(getIntent().getExtras().getString("harga")));
        SNPR.setText("Token : " + sn[0].substring(6));
        KWHPR.setText("KWH : " + sn[3].substring(4));

        setAdmin.setOnClickListener(view -> popUpMenuSetSellPrice());
        CetakPra.setOnClickListener(view -> printBluetooth());

        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        pilihPerangkat.setOnClickListener(v -> browseBluetoothDevice());

        getContentProfile();

        LinearLayout LinCetakPln = findViewById(R.id.LinCetakPln);
        shareStruk.setOnClickListener(v -> {
            Bitmap bitmap = Bitmap.createBitmap(LinCetakPln.getWidth(),LinCetakPln.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            LinCetakPln.draw(canvas);

            saveImageExternal(bitmap);
            File imagePath = new File(getApplicationContext().getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.c.cpayid.feature.fileprovider", newFile);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(Intent.createChooser(shareIntent, "Choose an app"));
            }
        });

        if (!hasPermissions(CetakPlnPra.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(CetakPlnPra.this, PERMISSIONS, 1);
        }
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColor(shareStruk.getBackground(), "green");
        DrawableMap.changeColorStroke(shareStruk.getBackground(), "green2");
        DrawableMap.changeColor(CetakPra.getBackground(), "green");
        DrawableMap.changeColorStroke(CetakPra.getBackground(), "green");
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


    public void popUpEditFooter() {
        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakPlnPra.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_footer, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.EFooter);
        editText.setText(Preference.getFooter(getApplicationContext()));
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);

        button1.setOnClickListener(v -> {
            footer.setText(editText.getText().toString());
            Preference.setFooter(getApplicationContext(), editText.getText().toString());

            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    public void popUpEditFooterHeader() {
        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakPlnPra.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_footerheader, null);

        EditText editTextF = (EditText) dialogView.findViewById(R.id.EFooter);
        EditText editTextH = (EditText) dialogView.findViewById(R.id.EHeader);
        editTextF.setText(Preference.getFooter(getApplicationContext()));
        editTextH.setText(Preference.getHeader(getApplicationContext()));
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);

        button1.setOnClickListener(v -> {
            footer.setText(editTextF.getText().toString());
            Preference.setFooter(getApplicationContext(), editTextF.getText().toString());
            header.setText(editTextH.getText().toString());
            Preference.setHeader(getApplicationContext(), editTextH.getText().toString());

            dialogBuilder.dismiss();

        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }


    private void saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        try {
            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                KonterPR.setText(response.body().getData().getStore_name());
                AlamatPR.setText(response.body().getData().getAddress());
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    private BluetoothConnection selectedDevice;

    public void browseBluetoothDevice() {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                items[++i] = device.getDevice().getName();
            }

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CetakPlnPra.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, (dialogInterface, i1) -> {
                int index = i1 - 1;
                if (index == -1) {
                    selectedDevice = null;
                } else {
                    selectedDevice = bluetoothDevicesList[index];
                }
//
                pilihPerangkat.setText(items[i1]);
            });

            android.app.AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    public static final int PERMISSION_BLUETOOTH = 1;

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, Cetak.PERMISSION_BLUETOOTH);
        } else {
            new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(selectedDevice));
        }
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[L]" + header.getText().toString() + "\n" + "\n" +
                        "[C]<u><font size='tall' color ='bg-black'>" + KonterPR.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + AlamatPR.getText().toString() + "\n" +
                        "[C]<u type='double'>" + WaktuPR.getText().toString() + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]" + "<b>Struk Pembayaran PLN Prabayar<b>" + "\n" +
                        "[C]" + WaktuPR.getText().toString() + "\n" +
                        "[C]\n" +
                        "[C]\n" +
                        "[L]Nomor [L]: " + nomorPR.getText().toString().substring(7) + "\n" +
                        TextAdd(namaPR.getText().toString().substring(7),"Nama") + "\n" +
                        "[L]Tarif/Daya [L]: " + tarifPR.getText().toString().substring(13) + "\n" +
                        "[L]KwH [L]: " + KWHPR.getText().toString().substring(6) + "\n" +

                        "[l]\n" +
                        "[L]<b>Total Bayar<b>" + "[L]: " + adminPR.getText().toString() + "\n" +
                        "[C]--------------------------------\n" +
                        "[C]<font size='tall'>" + SNPR.getText().toString().substring(7) + "</font>" + "\n" +
                        "[C]\n" +
                        "[C]struk ini merupakan bukti\n" +
                        "[C]pembayaran yang SAH\n" +
                        "[C]harap disimpan\n" +
                        "[C]\n" +
                        "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                        "[L]\n" +
                        "[L]" + footer.getText().toString() + "\n" +
                        "[L]\n"
        );
    }

    public void popUpMenuSetSellPrice() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(CetakPlnPra.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargaadmin, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            int total = Integer.parseInt(editText.getText().toString()) + Integer.parseInt(getIntent().getExtras().getString("harga"));
            adminPR.setText(harga);
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private String TextAdd(String caption,String judul){
        String value="";

        if (caption.length() < 15) {
            value = "[L]<b>"+judul+"</b>[L]: " + caption;
        } else if (caption.length() >= 15 && caption.length() < 30 ) {
            value = "[L]<b>"+judul+"</b>[L]: " + caption.substring(0,14)+"\n"+"[L][L] " + caption.substring(14);
        } else {
            value = "[L]<b>"+judul+"</b>[L]: " + caption.substring(0, 14) + "\n" + "[L][L] " + caption.substring(14, 30) + "\n" + "[L][L] " + caption.substring(30);
        }

        return value;
    }

}