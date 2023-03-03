package com.c.cpayid.feature.pinNew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;
import com.c.cpayid.feature.BaseActivity;
import com.c.cpayid.feature.GantiPin;
import com.c.cpayid.feature.Helper.GpsTracker;
import com.c.cpayid.feature.Helper.RetroClient;
import com.c.cpayid.feature.Helper.utils;
import com.c.cpayid.feature.LoginActivity;
import com.c.cpayid.feature.Model.Mlogin;
import com.c.cpayid.feature.R;
import com.c.cpayid.feature.DrawerActivity;
import com.c.cpayid.feature.OtpVerify;
import com.c.cpayid.feature.engine.ApplicationVariable;
import com.c.cpayid.feature.engine.ColorMap;
import com.c.cpayid.feature.engine.DrawableMap;
import com.c.cpayid.feature.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessaging;
import io.github.muddz.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinNew extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MYTAG";
    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0;
    private ImageButton backButton;
    private ImageView dot1, dot2, dot3, dot4, dot5, dot6;
    private TextView LupaPinNew;
    private Handler handler;
    String telepon;
    GpsTracker gpsTracker;
    int salah = 0;

    // Actual password
    private String actualPassword;
    //Number of digits entered by the user
    private int noOfDigits;
    // Store the digits entered by the user
    private ArrayList<Integer> passwordTryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinnew);
        Objects.requireNonNull(getSupportActionBar()).hide();

        noOfDigits = 0;
        actualPassword = getResources().getString(R.string.password_actual);
        handler = new Handler();

        initialiseWidgets();
        getLocation();

        LupaPinNew = findViewById(R.id.LupaPinNew);

        LupaPinNew.setOnClickListener(view -> {
            Intent intent = new Intent(PinNew.this, GantiPin.class);
            startActivity(intent);
        });
    }

    @Override
    public void settingLayout() {
        super.settingLayout();

        DrawableMap.changeColorVector(backButton.getBackground(), "green");
        ((TextView) findViewById(R.id.name_textview)).setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        LupaPinNew.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));

        num1.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num2.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num3.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num4.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num5.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num6.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num7.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num8.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num9.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
        num0.setTextColor(ColorMap.getColor(ApplicationVariable.applicationId, "green"));
    }

    private void initialiseWidgets() {
        //Numpad widgets
        num1 = findViewById(R.id.btnNumpad1);
        num2 = findViewById(R.id.btnNumpad2);
        num3 = findViewById(R.id.btnNumpad3);
        num4 = findViewById(R.id.btnNumpad4);
        num5 = findViewById(R.id.btnNumpad5);
        num6 = findViewById(R.id.btnNumpad6);
        num7 = findViewById(R.id.btnNumpad7);
        num8 = findViewById(R.id.btnNumpad8);
        num9 = findViewById(R.id.btnNumpad9);
        num0 = findViewById(R.id.btnNumpad0);
        backButton = findViewById(R.id.btnNumpadBack);
        // Numpad widgets OnClickListeners
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        backButton.setOnClickListener(v -> {
            if (noOfDigits <= 6 && noOfDigits > 0) {
              noOfDigits = 0;

                clearDotsIndicator();
                passwordTryList.clear();

            }
        });

        //Dots widgets(imageViews)
        dot1 = findViewById(R.id.dot_one);
        dot2 = findViewById(R.id.dot_two);
        dot3 = findViewById(R.id.dot_three);
        dot4 = findViewById(R.id.dot_four);
        dot5 = findViewById(R.id.dot_five);
        dot6 = findViewById(R.id.dot_six);
        //Header
    }

    // Back button's listener is in initialiseWidgets(), not here
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        // Check if the number of digits already entered by the user is 0 to 3;
        // If so, register the user's click, else do nothing
        // Calls attemptUnlock() and passes noOfDigits after every listener trigger
        if (noOfDigits >= 0 && noOfDigits <= 5) {
            noOfDigits += 1;

            switch (v.getId()) {
                case R.id.btnNumpad1:
                    passwordTryList.add(1);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad2:
                    passwordTryList.add(2);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad3:
                    passwordTryList.add(3);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad4:
                    passwordTryList.add(4);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad5:
                    passwordTryList.add(5);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad6:
                    passwordTryList.add(6);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad7:
                    passwordTryList.add(7);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad8:
                    passwordTryList.add(8);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad9:
                    passwordTryList.add(9);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad0:
                    passwordTryList.add(0);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
            }
        }
    }

    // Clears ALL the dots indicators to initial state - hollow circle
    private void clearDotsIndicator() {
        dot1.setImageResource(R.drawable.hollow_circle);
        dot2.setImageResource(R.drawable.hollow_circle);
        dot3.setImageResource(R.drawable.hollow_circle);
        dot4.setImageResource(R.drawable.hollow_circle);
        dot5.setImageResource(R.drawable.hollow_circle);
        dot6.setImageResource(R.drawable.hollow_circle);
    }

    // Sets the dots indicator from hollow to filled based on number of digits entered by user
    private void setDotsIndicator(int num) {
        clearDotsIndicator();
        if (num == 1) {
            dot1.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 2) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 3) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 4) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 5) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
            dot5.setImageResource(R.drawable.filled_circle_default);
        } else if (num >= 6) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
            dot5.setImageResource(R.drawable.filled_circle_default);
            dot6.setImageResource(R.drawable.filled_circle_default);
        }
    }

    // Checks if the numOfDigits == 4, if so then compare the digits in the ArrayList to the actual password
    private void attemptUnlock(int num) {
        if (num == 6) {
            noOfDigits = 0;

            // Convert chars in ArrayList to a string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passwordTryList.size(); i++) {
                sb.append(passwordTryList.get(i));
            }
            String passwordTry = sb.toString();
            Login(utils.hmacSha(passwordTry));

            passwordTryList.clear();

        }
    }

    // Sets the dots to greeb and sets succeed animation (vertical vibration)
    // At end of animation, resets the dots to hollow
    private void showSucceedVibration() {
        Animation succeedAnimation = AnimationUtils.loadAnimation(this, R.anim.vertical_vibrate_animation);

        dot1.setImageResource(R.drawable.filled_circle_succeed);
        dot2.setImageResource(R.drawable.filled_circle_succeed);
        dot3.setImageResource(R.drawable.filled_circle_succeed);
        dot4.setImageResource(R.drawable.filled_circle_succeed);
        dot5.setImageResource(R.drawable.filled_circle_succeed);
        dot6.setImageResource(R.drawable.filled_circle_succeed);

        dot1.setAnimation(succeedAnimation);
        dot2.setAnimation(succeedAnimation);
        dot3.setAnimation(succeedAnimation);
        dot4.setAnimation(succeedAnimation);
        dot5.setAnimation(succeedAnimation);
        dot6.setAnimation(succeedAnimation);

        final ImageView[] dotsArray = new ImageView[6];
        dotsArray[0] = dot1;
        dotsArray[1] = dot2;
        dotsArray[2] = dot3;
        dotsArray[3] = dot4;
        dotsArray[4] = dot5;
        dotsArray[5] = dot6;

        for (int i = 0; i < 6; i++) {
            dotsArray[i].startAnimation(succeedAnimation);
        }

        succeedAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(() -> clearDotsIndicator(), 150);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    // Sets the dots to red and sets error animation (horizontal vibration)
    // At end of animation, resets the dots to hollow
    private void showErrorVibration() {
        Animation errorAnimation = AnimationUtils.loadAnimation(this, R.anim.horizontal_vibrate_animation);

        dot1.setImageResource(R.drawable.filled_circle_error);
        dot2.setImageResource(R.drawable.filled_circle_error);
        dot3.setImageResource(R.drawable.filled_circle_error);
        dot4.setImageResource(R.drawable.filled_circle_error);
        dot5.setImageResource(R.drawable.filled_circle_error);
        dot6.setImageResource(R.drawable.filled_circle_error);

        dot1.setAnimation(errorAnimation);
        dot2.setAnimation(errorAnimation);
        dot3.setAnimation(errorAnimation);
        dot4.setAnimation(errorAnimation);
        dot5.setAnimation(errorAnimation);
        dot6.setAnimation(errorAnimation);

        dot1.startAnimation(errorAnimation);
        dot2.startAnimation(errorAnimation);
        dot3.startAnimation(errorAnimation);
        dot4.startAnimation(errorAnimation);
        dot5.startAnimation(errorAnimation);
        dot6.startAnimation(errorAnimation);

        //At end of error animation, reset the dots
        errorAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(() -> clearDotsIndicator(), 150);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private String getUserAgent() {

        return new WebView(this).getSettings().getUserAgentString();
    }

    private String getIPaddress() {

        return utils.getIPAddress(true);
    }
    public void getLocation() {
        gpsTracker = new GpsTracker(PinNew.this);
        if (gpsTracker.canGetLocation()) {

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PinNew.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void Login(String pin) {
        double longlitude = gpsTracker.getLongitude();
        double latitude = gpsTracker.getLatitude();
        Preference.setLong(getApplicationContext(), String.valueOf(longlitude));
        Preference.setLang(getApplicationContext(),String.valueOf(latitude));
        String useragent = getUserAgent();
        String IP = getIPaddress();
        Intent tlp = getIntent();

        Preference.getSharedPreference(getApplicationContext());
        telepon = Preference.getKredentials(getApplicationContext());

        if (telepon.equals("")) {
            telepon = tlp.getStringExtra("number");
        }

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                String deviceToken = task.getResult();
                Log.v("tokenku",deviceToken);
                Mlogin mlogin = new Mlogin(ApplicationVariable.getServerId(), telepon, pin, deviceToken, IP, Value.getMacAddress(getApplicationContext()), useragent, longlitude, latitude);

                Api api = RetroClient.getApiServices();
                Call<Mlogin> call = api.Login(mlogin);
                call.enqueue(new Callback<Mlogin>() {
                    @Override
                    public void onResponse(@NonNull Call<Mlogin> call, @NonNull Response<Mlogin> response) {
                        assert response.body() != null;
                        String code = response.body().getCode();

                        if (code.equals("200")) {
                            showSucceedVibration();
                            String device = response.body().getData().getType_login();

                            if (device.equals("DD")) {
                                Intent intent = new Intent(PinNew.this, OtpVerify.class);
                                intent.putExtra("keterangan", "Kode telah dikirim ke WA, silahkan cek WA Anda");
                                intent.putExtra("userid",response.body().getData().getId());
                                intent.putExtra("otp",response.body().getData().getOtp_id());
                                intent.putExtra("pushid",deviceToken);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent home = new Intent(PinNew.this, DrawerActivity.class);
                                startActivity(home);
                                String token = response.body().getData().getToken();
                                Preference.getSharedPreference(getApplicationContext());
                                Preference.setToken(getApplicationContext(), token);
                                Preference.setKeyOtpId(getBaseContext(), response.body().getData().getId());
                                Preference.setKeyUserCode(getBaseContext(), response.body().getData().getCode());
                                finish();
                            }
                        } else if (code.equals("403")) {
                            StyleableToast.makeText(getApplicationContext(), response.body().getError() + " Silahkan hubungi Admin", Toast.LENGTH_LONG, R.style.mytoast).show();
                            Intent intent = new Intent(PinNew.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                            showErrorVibration();
                            salah += 1;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Mlogin> call, @NonNull Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
                        StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    }
                });
            }
        });
    }
}