package com.c.cpayid.feature.Helper;

import com.c.cpayid.feature.Api.Api;
import com.c.cpayid.feature.Api.Value;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit(){
        if(retrofit == null){


            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Value.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

            return retrofit;

    }

    public static Api getApiServices()
    {
        return  getRetrofit().create(Api.class);
    }
}
