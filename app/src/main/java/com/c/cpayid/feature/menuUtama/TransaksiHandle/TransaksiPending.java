package com.c.cpayid.feature.menuUtama.TransaksiHandle;

import android.os.Bundle;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.c.cpayid.feature.R;

public class TransaksiPending extends AppCompatActivity {

    ImageView expand;
    LinearLayout linearExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_pending);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Transaksi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        expand = findViewById(R.id.expandtransaksi);
        linearExpand = findViewById(R.id.linearexpand);

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(linearExpand.getVisibility() == View.GONE){

                    TransitionManager.beginDelayedTransition(linearExpand, new AutoTransition());
                    linearExpand.setVisibility(View.VISIBLE);
                    expand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up);
                } else {

                    TransitionManager.beginDelayedTransition(linearExpand, new AutoTransition());
                    linearExpand.setVisibility(View.GONE);
                    expand.setImageResource(R.drawable.ic_baseline_expand_more_24);

                }

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