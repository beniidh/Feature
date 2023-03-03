package com.c.cpayid.feature;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Model.Mlogin;
import com.c.cpayid.feature.Model.Mphone;
import com.c.cpayid.feature.component.ProgressDialog;
import com.c.cpayid.feature.databinding.ActivityLoginBinding;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.pinNew.PinNew;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import info.androidhive.fontawesome.FontDrawable;
import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private AppUpdateManager appUpdateManager;
    private AppUpdateInfo appUpdateInfo;

    private ProgressDialog progressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        SplitCompat.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).hide();

        progressDialog = new ProgressDialog(this, "Mohon tunggu...");
//        ApplicationVariable.applicationId = getIntent().getStringExtra("applicationId");
        ApplicationVariable.applicationId = "com.c.cflazz";

        List<String> permissons = new ArrayList<>();
        permissons.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissons.add(Manifest.permission.READ_CONTACTS);
        permissons.add(Manifest.permission.INTERNET);
        permissons.add(Manifest.permission.CAMERA);
        permissons.add(Manifest.permission.ACCESS_WIFI_STATE);
        permissons.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissons.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            permissons.add(Manifest.permission.BLUETOOTH_CONNECT);
            permissons.add(Manifest.permission.BLUETOOTH_SCAN);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && !hasPermissions(permissons)) {
            String[] permissionArray = new String[permissons.size()];
            for (int i = 0; i < permissons.size(); i++) {
                permissionArray[i] = permissons.get(i);
            }
            ActivityCompat.requestPermissions(this, permissionArray, 0);
        } else {
            checkForUpdate();
        }
    }

    void checkForUpdate() {
        progressDialog.show();

        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            progressDialog.hide();
            this.appUpdateInfo = appUpdateInfo;

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.registerListener(updatedListener);
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, this, AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                                    .setAllowAssetPackDeletion(true).build(), -997);
                } catch (Exception ignored) {}
            } else {
                launch();

            }
        }).addOnFailureListener(e -> launch());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == -997) {
            if (resultCode == RESULT_CANCELED && appUpdateInfo != null) {
                checkForUpdate();
                Toast.makeText(this, "Harus lakukan pembaruan dahulu!", Toast.LENGTH_LONG).show();
            }
        }
    }

    InstallStateUpdatedListener updatedListener = state -> {
        if (state.installStatus() == InstallStatus.INSTALLED) {
            removeUpdateListener();
        }
    };

    void removeUpdateListener() {
        appUpdateManager.unregisterListener(updatedListener);
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        if (getIntent().getStringExtra("applicationId") != null && !getIntent().getStringExtra("applicationId").equals(ApplicationVariable.applicationId)) {
            ApplicationVariable.applicationId = getIntent().getStringExtra("applicationId");
        }

        binding.logologin.setImageDrawable(DrawableMap.getApplicationIcon(this));
        binding.savecheck.setButtonTintList(ColorStateList.valueOf(ColorMap.getColor(ApplicationVariable.applicationId, "green")));
        binding.register.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        DrawableMap.changeColor(binding.loginButtn.getBackground(), "green");
    }

    private void launch() {
        String coder = Preference.getTrackRegister(getApplicationContext());
        String token = Preference.getToken(getApplicationContext());
        if (coder.equals("")) {
            if (!token.equals("")) {
                Api api = RetroClient.getApiServices();
                Call<Mlogin> call = api.getProfile("Bearer " + token);
                call.enqueue(new Callback<Mlogin>() {
                    @Override
                    public void onResponse(@NonNull Call<Mlogin> call, @NonNull Response<Mlogin> response) {
                        assert response.body() != null;
                        String code = response.body().getCode();

                        Intent i;
                        if ("200".equals(code)) {
                            i = new Intent(LoginActivity.this, DrawerActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            i = new Intent(LoginActivity.this, PinNew.class);
                            startActivity(i);
                            finish();
                            StyleableToast.makeText(getApplicationContext(), "Token sudah berakhir,Silahkan Masukan PIN", Toast.LENGTH_LONG, R.style.mytoast2).show();
                        }

                        progressDialog.hide();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Mlogin> call, @NonNull Throwable t) {
                        StyleableToast.makeText(getApplicationContext(), " Internet Belum dinyalakan", Toast.LENGTH_LONG, R.style.mytoast2).show();
                        progressDialog.hide();
                    }
                });

            } else {
                //get informasi lokasi login
                getLocation();
                progressDialog.hide();
                binding.privacy.setOnClickListener(view1 -> {
                    String url = "https://www.termsfeed.com/live/191ba483-4cf7-493c-94b7-e8dbeaeae818";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(),Toast.LENGTH_LONG).show();
                    }
                });

                // set drawable end to editText Login
                FontDrawable drawable = new FontDrawable(this,R.string.userabata,true,false);
                Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
                drawable.setTypeface(type2);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.setTextColor(getColor(R.color.black));
                }
                drawable.setTextSize(20);
                binding.numberphone.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

                // Event Onclick for register activity
                binding.register.setOnClickListener(v -> {
                    Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(register);
                });

                // Event Onclick for login activity
                binding.loginButtn.setOnClickListener(v -> validation(binding.numberphone.getText().toString()));

                binding.savecheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setkredentials(getApplicationContext(),binding.numberphone.getText().toString());
                    }
                });
            }
        } else {
            Intent i = new Intent(LoginActivity.this, PendingActivity.class);
            startActivity(i);
            finish();
            progressDialog.hide();
        }
    }

    public boolean hasPermissions(List<String> permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        List<String> permissionsList = new ArrayList<>();
        Collections.addAll(permissionsList, permissions);

        if (requestCode == 0) {
            if (hasPermissions(permissionsList)) {
                checkForUpdate();
            } else {
                Toast.makeText(this, "Permission not complete", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void validation(String number) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(LoginActivity.this);
        loadingPrimer.startDialogLoadingCancleAble();

        if (number.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            loadingPrimer.dismissDialog();
        } else {
            Api api = RetroClient.getApiServices();
            Mphone mphone = new Mphone(number);
            Call<Mphone> call = api.ChekPhone(mphone);
            call.enqueue(new Callback<Mphone>() {
                @Override
                public void onResponse(@NonNull Call<Mphone> call, @NonNull Response<Mphone> response) {
                    assert response.body() != null;
                    String code = response.body().getCode();
                    if (code.equals("200")){
                        Intent intent = new Intent(LoginActivity.this, PinNew.class);
                        intent.putExtra("number",number);
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setkredentials(getApplicationContext(),binding.numberphone.getText().toString());
                        startActivity(intent);
                        finish();
                        binding.numberphone.setText("");
                    }else {
                        StyleableToast.makeText(getApplicationContext(), "Nomor belum terdaftar", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    }
                    loadingPrimer.dismissDialog();

                }

                @Override
                public void onFailure(@NonNull Call<Mphone> call, @NonNull Throwable t) {
                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();loadingPrimer.dismissDialog();
                }
            });
        }

    }

    public void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(LoginActivity.this);
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        }
    }
}
