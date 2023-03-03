package com.c.cpayid.feature.konter.MarkupKonter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkupKonter extends BaseActivity {

    EditText nominalMarkup;
    Button buttonMarkupKonter;
    TextView namakirim;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markup_konter);
        String color = Integer.toHexString(ColorMap.getColor(ApplicationVariable.applicationId, "green")).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Markup Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(DrawableMap.changeColorVector(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24), "green"));

        namakirim = findViewById(R.id.namakirim);
        namakirim.setText("Markup Konter : " + getIntent().getStringExtra("namakonter"));
        nominalMarkup = findViewById(R.id.nominalMarkup);
        buttonMarkupKonter = findViewById(R.id.buttonMarkupKonter);
        buttonMarkupKonter.setOnClickListener(v -> sendMarkUp(Integer.parseInt(nominalMarkup.getText().toString())));
    }

    @Override
    public void settingLayout() {
        super.settingLayout();
        DrawableMap.changeColor(buttonMarkupKonter.getBackground(), "green");
        DrawableMap.changeColorStroke(buttonMarkupKonter.getBackground(), "green");
    }

    private void sendMarkUp(int markup) {
        String id = getIntent().getStringExtra("id");
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        mMarkKonter MMarkKonter = new mMarkKonter(markup);
        Call<responMUK> call = api.markupKonter(token, MMarkKonter,id );
        call.enqueue(new Callback<responMUK>() {
            @Override
            public void onResponse(@NonNull Call<responMUK> call, @NonNull Response<responMUK> response) {
                assert response.body() != null;
                if (response.body().getCode().equals("200")) {
                    Toast.makeText(getApplicationContext(),"Berhasil Merubah MarkUp",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<responMUK> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

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
}