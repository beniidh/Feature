package com.c.cpayid.feature.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.c.cpayid.feature.R;


/**
 * @author DICKY <akbar.attijani@gmail.com>
 */

public class ProgressDialog extends Dialog {
    public Context c;
    public Dialog d;
    public TextView isi;
    public android.widget.ProgressBar progress;

    String content = "Error";

    public ProgressDialog(Context applicationContext) {
        super(applicationContext);
        this.c = applicationContext;
    }

    public ProgressDialog(Context applicationContext, String content) {
        super(applicationContext);
        this.c = applicationContext;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_bar_layout);

        isi = findViewById(R.id.txt_isi);
        progress = findViewById(R.id.progressBar1);

        isi.setText(content);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
