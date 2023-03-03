package com.c.cpayid.feature.MarkUP;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Markup extends BaseActivity {

    EditText markupEditText, markupNilai;
    Button updateharga;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markup);

        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Markup <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        markupNilai = findViewById(R.id.markupNilai);
        updateharga = findViewById(R.id.markupButton);
        updateharga.setOnClickListener(view -> {
            if (!markupNilai.getText().toString().isEmpty()) {
                int nominal = Integer.parseInt(markupNilai.getText().toString());
                MarkUP(nominal);
            } else {
                Toast.makeText(getApplicationContext(),"Jumlah tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(updateharga.getBackground(), "green");
        DrawableMap.changeColorStroke(updateharga.getBackground(), "green");
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

    public void MarkUP(int nominal) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        sendMarkUP sendMarkUP = new sendMarkUP(nominal);

        Call<ResponMarkup> call = api.markup(token, sendMarkUP);
        call.enqueue(new Callback<ResponMarkup>() {
            @Override
            public void onResponse(@NonNull Call<ResponMarkup> call, @NonNull Response<ResponMarkup> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
//                    finish();
                    markupNilai.setText("");
                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponMarkup> call, @NonNull Throwable t) {

            }
        });
    }
}