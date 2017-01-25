package com.android.alex.fppg.network;

import com.android.alex.fppg.model.Players;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 25-Jan-17.
 */

public class FppgService {

    public static final String BASE_URL = "https://cdn.rawgit.com";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttpClient
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(15000, TimeUnit.SECONDS)
                    .readTimeout(15000, TimeUnit.SECONDS)
                    .writeTimeout(15000, TimeUnit.SECONDS)
                    .build();

            // Retrofit
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
