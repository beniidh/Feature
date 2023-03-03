package com.c.cpayid.feature.CetakStruk.StrukPLNPra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.c.cpayid.feature.databinding.ActivityCetakBankBinding;
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

public class CetakBank extends BaseActivity {

    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_SCAN
    };

    private ActivityCetakBankBinding binding;
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCetakBankBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        binding.Harga.setText(utils.ConvertRP(getIntent().getExtras().getString("hargadasar")));
        binding.total.setText(utils.ConvertRP(getIntent().getExtras().getString("hargadasar")));
        binding.namaPS.setText("Penerima : "+getIntent().getExtras().getString("nama"));
        binding.IdPS.setText("Bank Penerima : "+getIntent().getExtras().getString("produk"));
        binding.WaktuPS.setText( getIntent().getExtras().getString("tanggal"));
        binding.nomorRekening.setText("Nomor Rekening : "+ getIntent().getExtras().getString("nomor"));

        binding.header.setText(Preference.getHeader(getApplicationContext()));
        binding.footer.setText(Preference.getFooter(getApplicationContext()));
        binding.setAdmin.setOnClickListener(v -> popUpMenuSetSellPrice());
        getContentProfile();

        binding.CetakPasca.setOnClickListener( v-> printBluetooth());

        binding.shareStrukP.setOnClickListener(v->{
            Bitmap bitmap = Bitmap.createBitmap(binding.lin.getWidth(),binding.lin.getHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            binding.lin.draw(canvas);

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

        binding.pilihPerangkat.setOnClickListener(v -> browseBluetoothDevice());

        if (!hasPermissions(CetakBank.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(CetakBank.this, PERMISSIONS, 1);
        }
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColor(binding.shareStrukP.getBackground(), "green2");
        DrawableMap.changeColorStroke(binding.shareStrukP.getBackground(), "green");
        DrawableMap.changeColor(binding.CetakPasca.getBackground(), "green");
        DrawableMap.changeColorStroke(binding.CetakPasca.getBackground(), "green");
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

    public void getContentProfile() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(@NonNull Call<ResponProfil> call, @NonNull Response<ResponProfil> response) {
                assert response.body() != null;
                binding.KonterPS.setText(response.body().getData().getStore_name());
                binding.AlamatPS.setText(response.body().getData().getAddress());
            }

            @Override
            public void onFailure(@NonNull Call<ResponProfil> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public void popUpMenuSetSellPrice() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(CetakBank.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargaadmin, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            int total = Integer.parseInt(editText.getText().toString()) + Integer.parseInt(getIntent().getExtras().getString("hargadasar"));
            binding.admin.setText(harga);
            binding.total.setText( utils.ConvertRP(String.valueOf(total)));
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

    public void popUpEditFooterHeader(View view) {
        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakBank.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_footerheader, null);

        EditText editTextF = (EditText) dialogView.findViewById(R.id.EFooter);
        EditText editTextH = (EditText) dialogView.findViewById(R.id.EHeader);
        editTextF.setText(Preference.getFooter(getApplicationContext()));
        editTextH.setText(Preference.getHeader(getApplicationContext()));
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);

        button1.setOnClickListener(v -> {
         binding.footer.setText(editTextF.getText().toString());
         Preference.setFooter(getApplicationContext(), editTextF.getText().toString());
         binding.header.setText(editTextH.getText().toString());
         Preference.setHeader(getApplicationContext(), editTextH.getText().toString());

            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void popUpEditHeader(View view) {
        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakBank.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_header, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.EFooter);
        editText.setText(Preference.getHeader(getApplicationContext()));
        Preference.setHeader(getApplicationContext(), editText.getText().toString());
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);

        button1.setOnClickListener(v -> {
            binding.header.setText(editText.getText().toString());
            Preference.setHeader(getApplicationContext(), editText.getText().toString());
            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
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

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CetakBank.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }
//
                    binding.pilihPerangkat.setText(items[i]);
                }
            });

            android.app.AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }
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
                "[L]" + binding.header.getText().toString() + "\n" + "\n" +
                        "[C]<u><font size='tall' color ='bg-black'>" + binding.KonterPS.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + binding.AlamatPS.getText().toString() + "\n" +
                        "[C]<u type='double'>" + binding.WaktuPS.getText().toString() + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]" + "<b>Struk Transfer Bank<b>" + "\n" +
                        "[C]" + binding.Harga.getText().toString() + "\n" +
                        "[C]\n" +
                        "[C]\n" +
                        Penerima(binding.namaPS.getText().toString().substring(11)) + "\n" +
                        "[L]Bank [L]:" + binding.IdPS.getText().toString().substring(16) + "\n" +
                        Rekening(binding.nomorRekening.getText().toString().substring(17),"Rekening") + "\n" +
                        "[L]Admin [L]:" + binding.admin.getText().toString() + "\n" +

                        "[l]\n" +
                        "[L]<b>Total <b>" + "[L]:" + binding.total.getText().toString() + "\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]struk ini merupakan bukti\n" +
                        "[C]pembayaran yang SAH\n" +
                        "[C]harap disimpan\n" +
                        "[C]\n" +
                        "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                        "[L]\n" +
                        "[L]" + binding.footer.getText().toString() + "\n" +
                        "[L]\n"
        );
    }


    private String Penerima(String penerima){
        String value = "";

        if (penerima.length() <= 15) {
            value = "[L]<b>Nama</b>[L]:" + penerima;
        } else if (penerima.length() > 16 && penerima.length() < 30) {
            value = "[L]<b>Nama</b>[L]:" + penerima.substring(0,14)+"\n"+"[L][L] " + penerima.substring(14);
        } else {
            value = "[L]<b>Nama</b>[L]:" + penerima.substring(0,14) + "\n" + "[L][L] " + penerima.substring(14, 30) + "\n" + "[L][L] " + penerima.substring(30, 45);
        }

        return value;
    }

    private String Rekening(String rekening,String judul){
        String value="";

        if (rekening.length() < 15) {
            value = "[L]<b>"+judul+"</b>[L]:" + rekening;
        } else if (rekening.length() >= 15 && rekening.length() < 30) {
            value = "[L]<b>" + judul + "</b>[L]:" + rekening.substring(0, 14) + "\n" + "[L][L] " + rekening.substring(14);
        } else {
            value = "[L]<b>" + judul + "</b>[L]:" + rekening.substring(0, 14) + "\n" + "[L][L] " + rekening.substring(14, 30) + "\n" + "[L][L] " + rekening.substring(30);
        }

        return value;
    }
}