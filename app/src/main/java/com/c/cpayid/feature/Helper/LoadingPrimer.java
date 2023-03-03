package com.c.cpayid.feature.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.engine.ApplicationVariable;

public class LoadingPrimer {

    Activity activity;
    AlertDialog dialog;

    public LoadingPrimer(Activity activity) {
        this.activity = activity;
    }

    public void startDialogLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.loadingprimer, null, false);

        String urlLottie = ApplicationVariable.lottieUtl.get(ApplicationVariable.applicationId);
        if (urlLottie != null) {
            LottieAnimationView lottieAnimationView = view.findViewById(R.id.progressUtama);
            lottieAnimationView.setAnimationFromUrl(urlLottie);
        }

        builder.setView(view);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    public void startDialogLoadingCancleAble() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.loadingprimer, null, false);

        String urlLottie = ApplicationVariable.lottieUtl.get(ApplicationVariable.applicationId);
        if (urlLottie != null) {
            LottieAnimationView lottieAnimationView = view.findViewById(R.id.progressUtama);
            lottieAnimationView.setAnimationFromUrl(urlLottie);
        }

        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    public void dismissDialog() {
        dialog.dismiss();

    }
}
