package com.c.cpayid.feature.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.BaseFragment;
import com.c.cpayid.feature.Fragment.Respon.MRuningText;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends BaseFragment {

    private ChatViewModel mViewModel;
    ConstraintLayout WaCenter, EmailCenter, teleGramCenter;
    String wa, telegram, email;
    TextView nomor,nomorTele,AlamatGm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_fragment, container, false);
        WaCenter = v.findViewById(R.id.WaCenter);
        teleGramCenter = v.findViewById(R.id.teleGramCenter);
        EmailCenter = v.findViewById(R.id.EmailCenter);
        nomor = v.findViewById(R.id.nomor);
        nomorTele = v.findViewById(R.id.nomorTele);
        AlamatGm = v.findViewById(R.id.AlamatGm);
        getContact(Preference.getServerID(getContext()));
        EmailCenter.setOnClickListener(view -> {

            if (!getEmail().isEmpty()) {
                String[] email = {getEmail()};
                composeEmail(email, "halo", null);
            } else {
                Toast.makeText(getContext(), "Alamat Email belum di setting", Toast.LENGTH_SHORT).show();
            }
        });

        WaCenter.setOnClickListener(view -> {
            if (!getWa().isEmpty()) {
                if (isWhatappInstalled()) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + "+" + getWa() +
                            "&text=" + "hallo saya.."));
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Nomor WA belum di setting", Toast.LENGTH_SHORT).show();
            }
        });

        teleGramCenter = v.findViewById(R.id.teleGramCenter);
        teleGramCenter.setOnClickListener(view -> {
            if (!getTelegram().isEmpty()) {
                if (isTelegramInstalled()) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + getTelegram()));
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), "Telegram is not installed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "ID Telegram belum di setting", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private boolean isWhatappInstalled() {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean whatsappInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }

        return whatsappInstalled;
    }

    private boolean isTelegramInstalled() {
        PackageManager packageManager = getActivity().getPackageManager();
        boolean teleInstall;

        try {
            packageManager.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES);
            teleInstall = true;
        } catch (PackageManager.NameNotFoundException e) {
            teleInstall = false;
        }

        return teleInstall;
    }

    void intentMessageTelegram(String msg) {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(getActivity(), appName);
        if (isAppInstalled) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
            startActivity(Intent.createChooser(myIntent, "Share with"));
        } else {
            Toast.makeText(getActivity(), "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void getContact(String id) {
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<MRuningText> call = api.getRunningText(token, id);
        call.enqueue(new Callback<MRuningText>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<MRuningText> call, @NonNull Response<MRuningText> response) {
                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    setWa(response.body().getData().get(0).getCs_whatsapp());
                    nomor.setText("+" + response.body().getData().get(0).getCs_whatsapp());
                    setTelegram(response.body().getData().get(0).getCs_telegram());
                    nomorTele.setText(response.body().getData().get(0).getCs_telegram());
                    setEmail(response.body().getData().get(0).getCs_email());
                    AlamatGm.setText(response.body().getData().get(0).getCs_email());
                } else {
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MRuningText> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void composeEmail(String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        startActivity(intent);

    }
}