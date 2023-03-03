package com.c.cpayid.feature.TopUpSaldoku.MetodeBayar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.LoadingPrimer;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.TopUpSaldoku.HistoryTopUp.ActivityHistoryTopUp;
import com.c.cpayid.feature.TopUpSaldoku.ReqSaldoku;
import com.c.cpayid.feature.TopUpSaldoku.mCancel;
import com.c.cpayid.feature.databinding.ActivityQrisBinding;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import io.github.muddz.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrisActivity extends BaseActivity {

    private ActivityQrisBinding binding;
    private String url;
    String primaryid = "";

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>QRIS <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        submitLimit(Double.parseDouble(Preference.getSaldoku(getApplicationContext()).replaceAll(",","")));

        binding.swipe.setOnRefreshListener(() -> {
            binding.swipe.setRefreshing(false);
            Picasso.get().load(getUrl()).into(binding.ImageQris);
        });

        binding.batal.setOnClickListener(view1 -> {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(QrisActivity.this);
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
                        if(response.body().getCode().equals("200")){
                            Intent intent = new Intent(QrisActivity.this, ActivityHistoryTopUp.class);
                            startActivity(intent);
                            Intent intent2 = new Intent("finish_activity");
                            sendBroadcast(intent2);
                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
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
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(binding.textView9.getBackground(), "green");
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

    private void submitLimit(double amount) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(QrisActivity.this);
        loadingPrimer.startDialogLoadingCancleAble();

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        String id = getIntent().getStringExtra("id");

        ReqSaldoku reqSaldoku = new ReqSaldoku(id, Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), amount);

        Call<ReqSaldoku> call = api.AddRequestSaldoku(token, reqSaldoku);
        call.enqueue(new Callback<ReqSaldoku>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ReqSaldoku> call, @NonNull Response<ReqSaldoku> response) {
                assert response.body() != null;
                String code = response.body().getCode();

                if (code.equals("200")) {
                    setPrimaryid(response.body().getData().getId());
                    setUrl(response.body().getData().getQris_image());
                    Picasso.get().load(response.body().getData().getQris_image()).into(binding.ImageQris);
                    binding.admin.setText(utils.ConvertRP(response.body().getData().getVa_admin()));
                    binding.Nominal.setText(utils.ConvertRP(response.body().getData().getAmount()));
                    binding.exp.setText("Berlaku hingga : " + response.body().getData().getVa_expired().substring(0, 11));

                    double total = Double.parseDouble(response.body().getData().getVa_admin())+ Double.parseDouble(response.body().getData().getAmount());

                    binding.total.setText(utils.ConvertRP(String.valueOf(total)));

                    Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_LONG).show();


                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

                loadingPrimer.dismissDialog();

            }

            @Override
            public void onFailure(@NonNull Call<ReqSaldoku> call, @NonNull Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                loadingPrimer.dismissDialog();
            }
        });

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(String primaryid) {
        this.primaryid = primaryid;
    }
}